package com.taoren.app.relation.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;

import com.taoren.common.util.TaorenUtils;
import com.taoren.model.user.User;
import com.taoren.service.user.RelationRemoteService;

import com.taoren.service.user.UserFriendRemoteService;
import com.taoren.service.user.UserReportRemoteService;
import com.taoren.service.user.model.RelationDto;
import com.taoren.service.user.model.UserRefuseRespDto;
import com.taoren.service.user.model.UserReportRespDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Scope("prototype")
@Controller
@RequestMapping("/relation")
public class RelationController extends BaseController {
    Logger logger = LoggerFactory.getLogger(RelationController.class);


    @Autowired
    private RelationRemoteService relationService;

    @Autowired
    private UserReportRemoteService reportService;

    @Autowired
    private UserFriendRemoteService friendService;


    /**
     * 添加删除朋友
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/friend")
    public void userInfo(@RequestParam("token") String token,
                         @RequestParam(value="friend") long friend,
                         @RequestParam(value = "action") int action,
                                    HttpServletRequest request,
                                    HttpServletResponse response)throws InvalidRequestException, IOException {
        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){

            RelationDto dto = null;
            if (action > 0) {
                dto = friendService.addUserFriend(u.getId(), friend);
            }
            if (action < 0) {
                dto = friendService.delUserFriend(u.getId(), friend);
            }

            json = TaorenUtils.o2j(dto);
        }
        responseInfo(response, json);
    }

    /**
     * 添加删除朋友
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/friendIds")
    public void userInfo(@RequestParam("token") String token,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);
        if(isValid(request, u)){
            List<Long> ids = friendService.getUserFriendIds(u.getId());
            responseInfo(response, TaorenUtils.o2j(ids));
        }
    }

    /**
     * 举报
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/report")
    public void userShow(@RequestParam("token") String token,
                         @RequestParam("defendant") long defendant,
                         @RequestParam("reason") int reason,
                         @RequestParam("reasonMsg") String reasonMsg,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {
        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){
            UserReportRespDto dto = reportService.addUserReport(u.getId(), defendant, reason, reasonMsg);
            responseInfo(response, TaorenUtils.o2j(dto));
        }
    }


    /**
     * 拒绝接收对方消息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/refuse")
    public void userEdit(@RequestParam("token") String token,
                         @RequestParam(value="refuseUid") long refuseUid,
                         @RequestParam(value = "action") int action,
                                    HttpServletRequest request,
                                    HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){

            UserRefuseRespDto dto = null;
            if (action > 0) {
                dto = relationService.addUserRefuse(u.getId(), refuseUid);
            }
            if (action < 0) {
                dto = relationService.delUserRefuse(u.getId(), refuseUid);
            }
            responseInfo(response, TaorenUtils.o2j(dto));
        }
    }

    /**
     * 拒绝接收对方消息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/isRefused")
    public void userEdit(@RequestParam("token") String token,
                         @RequestParam("targetUid") long targetUid,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){
            RelationDto dto  = relationService.isRefused(u.getId(), targetUid);
            responseInfo(response, TaorenUtils.o2j(dto));
        }

    }

    /**
     * 拒绝接收对方消息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/info")
    public void info(@RequestParam("token") String token,
                         @RequestParam("targetUid") long targetUid,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){
            RelationDto dto = relationService.getRelation(u.getId(), targetUid);
            responseInfo(response, TaorenUtils.o2j(dto));
        }

    }

}