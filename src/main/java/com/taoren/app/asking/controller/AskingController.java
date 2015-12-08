package com.taoren.app.asking.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;
import com.taoren.model.ask.Asking;
import com.taoren.model.ask.AskingMedia;
import com.taoren.model.lb.Label;
import com.taoren.model.lb.LabelMedia;
import com.taoren.model.lb.LabelZan;
import com.taoren.model.user.User;
import com.taoren.service.ask.AskingRemoteService;
import com.taoren.service.ask.model.AskingListRespDto;
import com.taoren.service.ask.model.AskingRespDto;
import com.taoren.service.lb.LabelRemoteService;
import com.taoren.service.lb.model.LabelListRespDto;
import com.taoren.service.lb.model.LabelRespDto;
import com.taoren.service.sc.AskingSearchRemoteService;
import com.taoren.service.sc.LabelSearchRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/asking")
public class AskingController extends BaseController{
    Logger logger = LoggerFactory.getLogger(AskingController.class);


    @Autowired
    AskingRemoteService askingService;

    @Autowired
    AskingSearchRemoteService askingSearchService;


    /**
     *
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/add")
    public void askingAdd(
            @RequestParam("token") String token,
            @ModelAttribute Asking asking,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            asking.setAskingDetail(asking.getAskingDetail().trim());
            String media = request.getParameter("media");
            if(StringUtils.areNotEmpty(media)){
                try{
                    List<AskingMedia> mediaList = TaorenUtils.j2List(media, AskingMedia.class);

                    asking.setMediaList(mediaList);
                }catch (Exception e){}
            }
            asking.setUid(u.getId());

            AskingRespDto respDto = askingService.addAsking(asking);

            //建立搜索索引（es）
            try{
                if(respDto.getAskingId() != null){
                    asking.setId(respDto.getAskingId());
                    askingSearchService.indexAsking(asking);
                }

                updateUserPosition(u.getId(), request);//同时更新用户位置信息

            }catch (Exception e){
                e.printStackTrace();
            }
            responseInfo(response, TaorenUtils.o2j(respDto));
        }

    }

    /**
     * 用户
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/del")
    public void askingDel(@RequestParam("token") String token,
                         @RequestParam("askingId") Long askingId,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            Asking asking = new Asking();
            asking.setId(askingId);
            asking.setUid(u.getId());

            AskingRespDto dto = askingService.delAsking(asking);

            //删除搜索索引（es）
            try{
                if(dto.getResultCode() == 1){
                    askingSearchService.deleteAsking(asking.getUid(), asking.getId());
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            responseInfo(response, TaorenUtils.o2j(dto));
        }

    }


    /**
     *
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/list")
    public void askList(
            @RequestParam("token") String token,
            @RequestParam(value="askingId", required=false) Long askingId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request,
                                    HttpServletResponse response)throws InvalidRequestException, IOException {


        User u = getUser(token);

        if(isValid(request,u)){
            Map map = new HashMap();
            map.put("uid", u.getId());
            if(askingId != null){
                map.put("askingId", askingId);
            }
            if(page != null){
                map.put("curr_page", page);
            }
            if(pageSize != null){
                map.put("page_size", pageSize);
            }

            AskingListRespDto respDto = askingService.askingList(map);

            responseInfo(response, TaorenUtils.o2j(respDto));
        }
    }


}


