package com.taoren.app.label.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;

import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;

import com.taoren.model.lb.Label;
import com.taoren.model.lb.LabelMedia;
import com.taoren.model.lb.LabelZan;
import com.taoren.model.user.User;
import com.taoren.service.lb.LabelRemoteService;
import com.taoren.service.lb.model.EsLabelDto;
import com.taoren.service.lb.model.LabelListRespDto;
import com.taoren.service.lb.model.LabelRespDto;

import com.taoren.service.sc.LabelSearchRemoteService;
import com.taoren.service.user.model.EsPositionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/label")
public class LabelController extends BaseController{
    Logger logger = LoggerFactory.getLogger(LabelController.class);


    @Autowired
    LabelRemoteService labelService;

    @Autowired
    LabelSearchRemoteService labelSearchService;


    /**
     * 用户添加自己的标签
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/add")
    public void labelAdd(
            @RequestParam("token") String token,
            @ModelAttribute Label label,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            label.setLabelName(label.getLabelName().trim());
            String media = request.getParameter("media");
            if(StringUtils.areNotEmpty(media)){
                try{
                    List<LabelMedia> mediaList = TaorenUtils.j2List(media, LabelMedia.class);
                    label.setMediaList(mediaList);
                }catch (Exception e){}
            }
            label.setUid(u.getId());

            LabelRespDto respDto = labelService.addLabel(label);

            //建立搜索索引（es）
            try{
                if(respDto.getLabelId() != null){
                    label.setId(respDto.getLabelId());
                    labelSearchService.indexLabel(label);
                }

                updateUserPosition(u.getId(), request);//同时更新用户位置信息

            }catch (Exception e){
                e.printStackTrace();
            }
            responseInfo(response, TaorenUtils.o2j(respDto));
        }

    }

    /**
     * 用户删除自己的标签
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/del")
    public void labelDel(@RequestParam("token") String token,
                         @RequestParam("labelId") Long labelId,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            Label label = new Label();
            label.setId(labelId);
            label.setUid(u.getId());

            LabelRespDto dto = labelService.delLabel(label);

            //删除搜索索引（es）
            try{
                if(dto.getResultCode() == 1){
                    labelSearchService.deleteLabel(label.getUid(), label.getId());
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            responseInfo(response, TaorenUtils.o2j(dto));
        }

    }



    /**
     * 用户修改自己的标签
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/edit")
    public void labelEdit(@RequestParam("token") String token,
                          @RequestParam("labelId") long labelId,
                          @ModelAttribute Label label,
                                        HttpServletRequest request,
                                        HttpServletResponse response)throws InvalidRequestException, IOException {



        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            label.setId(labelId);
            label.setUid(u.getId());

            String media = request.getParameter("media");
            if(StringUtils.areNotEmpty(media)){
                try{
                    label.setMediaList(TaorenUtils.j2o(media, List.class));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            LabelRespDto dto = labelService.editLabel(label);

            //更新索引（es）
            try{
                if(dto.getResultCode() == 1){
                    labelSearchService.updateLabel(label);
                }

                updateUserPosition(u.getId(), request);//同时更新用户位置信息

            }catch (Exception e){
                e.printStackTrace();
            }
            responseInfo(response, TaorenUtils.o2j(dto));
        }

    }

    /**
     * 标签详情
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/show")
    public void labelInfo(
            @RequestParam(value="token", required=false) String token,
            @RequestParam(value="uid", required=false) Long uid,
            @RequestParam(value="labelId", required=false) Long labelId,
                                    HttpServletResponse response)throws InvalidRequestException, IOException {

        String json = "[]";
        User u = getUser(token);

        if(token != null && u == null){
            throw new InvalidRequestException();
        }

        Label label = new Label();
        label.setUid(uid);
        label.setId(labelId);
        if(u != null){
            label.setZanUid(u.getId());
        }


        LabelListRespDto labelListRespDto = labelService.getLabel(label);

        if(labelListRespDto.getResultCode() == 1){

            List<Label> labelList = labelListRespDto.getList();
            for(Label l: labelList){
                EsPositionDto labelPosition = labelSearchService.getLabelPosition(l.getUid(), l.getId());
                if(labelPosition != null){
                    l.setZan(labelPosition.getZan());
                    l.setCommentCount(labelPosition.getCommentCount());
                }

            }
            json = TaorenUtils.o2j(labelList);
        }
        responseInfo(response, json);

    }

    /**
     * 标签详情
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/zan")
    public void labelZan(
            @RequestParam("token") String token,
            @RequestParam(value="uid") Long uid,
            @RequestParam(value="labelId") Long labelId,
            @RequestParam(value = "action") Integer action,
            HttpServletRequest request,
            HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){
            LabelZan zan = new LabelZan();
            zan.setUid(u.getId());//称赞人
            zan.setLabelId(labelId);//被称赞的labelId
            zan.setAction(action);
            LabelRespDto resp = new LabelRespDto();

            if(labelService.zanLabel(zan) == 1){
                resp.setResultCode(1);
                resp.setMsg("zan success");
                labelSearchService.zanLabel(uid, labelId, action);

            }else{
                resp.setResultCode(2);
                resp.setErrorCode(1);
                resp.setMsg("zan fail");
            }

            String json = TaorenUtils.o2j(resp);
            responseInfo(response, json);
        }

    }


}


