package com.taoren.app.asking.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;
import com.taoren.model.ask.Asking;
import com.taoren.model.ask.AskingReply;
import com.taoren.model.lb.Label;
import com.taoren.model.lb.LabelComment;
import com.taoren.model.user.User;
import com.taoren.service.ask.AskingRemoteService;
import com.taoren.service.ask.AskingReplyRemoteService;
import com.taoren.service.ask.model.AskingListRespDto;
import com.taoren.service.ask.model.AskingReplyRespDto;
import com.taoren.service.lb.LabelCommentRemoteService;
import com.taoren.service.lb.LabelRemoteService;
import com.taoren.service.lb.model.LabelCommentRespDto;
import com.taoren.service.lb.model.LabelListRespDto;
import com.taoren.service.sc.AskingSearchRemoteService;
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
import java.util.Map;


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/asking/reply")
public class AskingReplyController extends BaseController{
    Logger logger = LoggerFactory.getLogger(AskingReplyController.class);


    @Autowired
    AskingReplyRemoteService askingReplyService;

    @Autowired
    AskingRemoteService askingService;

    @Autowired
    AskingSearchRemoteService askingSearchService;

    @Autowired
    MessageRemoteService messageService;

    /**
     *
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/add")
    public void askingReplytAdd(
            @RequestParam("token") String token,
            @RequestParam("uid") Long uid,
            @RequestParam("askingId") long askingId,
            @ModelAttribute AskingReply askingReply,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            askingReply.setUid(u.getId());

            AskingReplyRespDto respDto = askingReplyService.addAskingReply(askingReply);

            if(respDto.getResultCode() ==1){
                try {


                    User targetUser;
                    String msg;
                    String replyTo = request.getParameter("replyTo");
                    if(StringUtils.areNotEmpty(replyTo)){
                        targetUser = getUser(userService.getTokenByUid(Long.parseLong(replyTo)));
                        msg = "有人回复了你对别人的喊话喊话回复，哈哈，快去看看吧";
                    }else {
                        targetUser = getUser(userService.getTokenByUid(uid));
                        msg = "有人回复了你的喊话，快去看看吧";
                    }

                    if(targetUser != null && StringUtils.areNotEmpty(targetUser.getDeviceToken())) {
                        messageService.pushMessage(targetUser.getDeviceToken(),msg ,1);
                    }
//                    else {
//                        messageService.saveMessageWhenLogout(uid, msg);
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //Do nothing
                }

                askingSearchService.replyAsking(uid, askingId, 1);
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
     * 用户删除自己的标签
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/del")
    public void labelCommentDel(@RequestParam("token") String token,
                         @RequestParam("replyId") Long replyId,
                                @RequestParam("uid") Long uid,
                         @RequestParam("askingId") Long askingId,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            AskingReply askingReply = new AskingReply();
            askingReply.setId(replyId);

            if(u.getId() == uid){//喊话拥有都删除喊话回复
                Asking asking = new Asking();
                asking.setUid(u.getId());
                asking.setId(askingId);

                AskingListRespDto dto = askingService.getAsking(asking);

                if(dto != null && dto.getList() != null && dto.getList().size() > 0){
                    askingReply.setAskingId(askingId);
                }else {
                    askingReply.setAskingId(0L);
                }

            }else {
                askingReply.setUid(u.getId());
            }

            AskingReplyRespDto dto = askingReplyService.delAskingReply(askingReply);

            if(dto.getResultCode() ==1){
                askingSearchService.replyAsking(uid, askingId, -1);
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
    public void askingReplyList(
                         @RequestParam("askingId") Long askingId,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                         HttpServletResponse response) throws IOException {

        Map map = new HashMap();
        map.put("askingId", askingId);
        if(page != null){
            map.put("curr_page", page);
        }
        if(pageSize != null){
            map.put("page_size", pageSize);
        }
        responseInfo(response, TaorenUtils.o2j(askingReplyService.askingReplyList(map)));

    }


}


