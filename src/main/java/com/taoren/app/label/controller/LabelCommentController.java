package com.taoren.app.label.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;
import com.taoren.model.lb.Label;
import com.taoren.model.lb.LabelComment;
import com.taoren.model.lb.LabelMedia;
import com.taoren.model.lb.LabelZan;
import com.taoren.model.user.User;
import com.taoren.service.lb.LabelCommentRemoteService;
import com.taoren.service.lb.LabelRemoteService;
import com.taoren.service.lb.model.LabelCommentRespDto;
import com.taoren.service.lb.model.LabelListRespDto;
import com.taoren.service.lb.model.LabelRespDto;
import com.taoren.service.sc.AskingSearchRemoteService;
import com.taoren.service.sc.LabelSearchRemoteService;
import com.taoren.service.user.MessageRemoteService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/label/comment")
public class LabelCommentController extends BaseController{
    Logger logger = LoggerFactory.getLogger(LabelCommentController.class);


    @Autowired
    LabelCommentRemoteService labelCommentService;

    @Autowired
    LabelRemoteService labelService;

    @Autowired
    LabelSearchRemoteService labelSearchService;

    @Autowired
    MessageRemoteService messageService;


    /**
     * 添加
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/add")
    public void labelCommentAdd(
            @RequestParam("token") String token,
            @RequestParam("uid") Long uid,
            @RequestParam("labelId") Long labelId,
            @ModelAttribute LabelComment labelComment,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            labelComment.setUid(u.getId());
            labelComment.setLabelId(labelId);

            LabelCommentRespDto respDto = labelCommentService.addLabelComment(labelComment);

            if(respDto.getResultCode() == 1){

                try{
                    long uid2 = u.getId();
                    if(uid == uid2) {//自己评论自己就算了
                    }else {

                        User targetUser;
                        String msg;
                        String replyTo = request.getParameter("replyTo");
                        if(StringUtils.areNotEmpty(replyTo)){
                            targetUser = getUser(userService.getTokenByUid(Long.parseLong(replyTo)));
                            msg = "有人回复了你的评论，快去看看吧";
                        }else {
                            targetUser = getUser(userService.getTokenByUid(uid));
                            msg = "有人评论了你的标签，快去看看吧";
                        }

                        if(targetUser != null && StringUtils.areNotEmpty(targetUser.getDeviceToken())) {
                            messageService.pushMessage(targetUser.getDeviceToken(), msg, 1);
                        }

//                    else {
//                        messageService.saveMessageWhenLogout(uid, msg);
//                    }
                    }

                }catch (Exception e){
                    //Do nothing
                }

                labelSearchService.commentLabel(uid, labelId, 1);
            }

            try{
                updateUserPosition(u.getId(), request);//同时更新用户位置信息

            }catch (Exception e){
                e.printStackTrace();
            }
            responseInfo(response, TaorenUtils.o2j(respDto));
        }

    }

    /**
     * 删除
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/del")
    public void labelCommentDel(@RequestParam("token") String token,
                         @RequestParam("commentId") Long commentId,
                                @RequestParam("uid") Long uid,
                         @RequestParam("labelId") Long labelId,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            LabelComment labelComment = new LabelComment();
            labelComment.setId(commentId);


            if(u.getId() == uid){
                Label label = new Label();
                label.setUid(u.getId());
                label.setId(labelId);
                LabelListRespDto dto = labelService.getLabel(label);
                if(dto != null && dto.getList() != null && dto.getList().size() > 0){
                    labelComment.setLabelId(labelId);
                }else {
                    labelComment.setLabelId(0L);
                }
            }else {
                labelComment.setUid(u.getId());
            }

            LabelCommentRespDto dto = labelCommentService.delLabelComment(labelComment);

            if(dto.getResultCode() == 1){
                labelSearchService.commentLabel(uid, labelId, -1);
            }
            responseInfo(response, TaorenUtils.o2j(dto));
        }
    }


    /**
     * 用户删除自己的标签
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/list")
    public void labelCommentList(
                         @RequestParam("labelId") Long labelId,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                         HttpServletResponse response) throws IOException {

        Map map = new HashMap();
        map.put("labelId", labelId);
        if(page != null){
            map.put("curr_page", page);
        }
        if(pageSize != null){
            map.put("page_size", pageSize);
        }

        responseInfo(response, TaorenUtils.o2j(labelCommentService.labelCommentList(map)));

    }




    /**
     * 添加
     * @param
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/pushMessage")
    public void pushMessage(
            @RequestParam("deviceToken") String deviceToken,
            @RequestParam("msg") String msg
                )throws InvalidRequestException, IOException {

        messageService.pushMessage(deviceToken,msg ,1);
    }

    /**
     * 添加
     * @param
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/saveMessageWhenLogout")
    public void saveMessageWhenLogout(
            @RequestParam("uid") long uid,
            @RequestParam("msg") String msg
    )throws InvalidRequestException, IOException {

        messageService.saveMessageWhenLogout(uid, msg);
    }

    /**
     * 添加
     * @param
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/pushMessageWhenLogin")
    public void pushMessageWhenLogin(
            @RequestParam("uid") long uid,
            @RequestParam("deviceToken") String deviceToken
    )throws InvalidRequestException, IOException {

        messageService.pushMessageWhenLogin(deviceToken, uid);

    }
}


