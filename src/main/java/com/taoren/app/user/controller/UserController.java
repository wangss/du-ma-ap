package com.taoren.app.user.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.constant.CodeMsgConstants;
import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;
import com.taoren.model.user.User;
import com.taoren.model.user.UserFriend;
import com.taoren.model.user.UserInfo;
import com.taoren.service.user.RelationRemoteService;
import com.taoren.service.user.UserFriendRemoteService;
import com.taoren.service.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserFriendRemoteService friendService;


    /**
     * 查看用户资料
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/show")
    public void userShow(@RequestParam("uid") long uid,
                         HttpServletRequest request,
                         HttpServletResponse response)throws IOException {

        UserInfoRespDto respDto = userInfoService.getUserInfo(uid);
        UserInfo userInfo = respDto.getUserInfo();
        if(userInfo != null){
            //计算距离
            String lon= request.getParameter("longitude");
            String lat = request.getParameter("latitude");

            if(userInfo != null && userInfo.getPrivacyFind() != null && userInfo.getPrivacyFind() == 1 && StringUtils.areNotEmpty(lon, lat)){
                try {

                    EsPositionDto positionDto = userSearchService.getUserPosition(uid, Double.parseDouble(lon), Double.parseDouble(lat));

                    if(positionDto != null){

                        userInfo.setDistance(positionDto.getDistance());
                        userInfo.setActiveTime(positionDto.getActiveTime());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //判断是否为好友关系
            String token = request.getParameter("token");
            if(token != null){
                try{
                    User u = userService.getUserbyToken(token);
                    if(u != null){
                        RelationDto dto = friendService.isFriend(u.getId(), uid);
                        if(dto != null){
                            userInfo.setFriend(dto.getFriend());
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            userInfo.removeSomePrivacy();//这个一定要
        }

        responseInfo(response, TaorenUtils.o2j(userInfo));
    }




    /**
     * 用户编辑资料
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/edit")
    public void userEdit(@RequestParam("token") String token,
                         @RequestParam("sign") String sign,
                         @RequestParam("timestamp") long timestamp,
                         @ModelAttribute UserInfo userInfo,
                                    HttpServletRequest request,
                                    HttpServletResponse response)throws InvalidRequestException, IOException {

        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            boolean shouldreturn = false;
            UserInfoRespDto dto = new UserInfoRespDto();

            String trId = userInfo.getTrId();
            //如果有trId
            if(StringUtils.areNotEmpty(trId)){
                //trId已经存在，不可修改
                if(StringUtils.areNotEmpty(u.getTrId())){
                    dto.setResultCode(CodeMsgConstants.USERINFO_R_C_FAIL);
                    dto.setErrorCode(CodeMsgConstants.USERINFO_E_C_TRIDAlREADYHAVE);
                    dto.setMsg(CodeMsgConstants.USERINFO_E_MSG_TRIDAlREADYHAVE);
                    shouldreturn = true;
                }else {
                    EsUserDto userDto = userSearchService.findUserByTrId(trId);

                    if(userDto != null) {//trId已经被占用
                        dto.setResultCode(CodeMsgConstants.USERINFO_R_C_FAIL);
                        dto.setErrorCode(CodeMsgConstants.USERINFO_E_C_TRIDTAKEN);
                        dto.setMsg(CodeMsgConstants.USERINFO_E_MSG_TRIDTAKEN);
                        shouldreturn = true;
                    }
                }
            }

            if(!shouldreturn){
                userInfo.setUid(u.getId());
                userInfo.setId(u.getId());

                dto = userInfoService.editUserInfo(userInfo);

                updateUser(userInfo, request);
            }
            json = TaorenUtils.o2j(dto);
        }
        responseInfo(response, json);

    }



    /**
     * 修改密码
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/password")
    public void password(@RequestParam("token") String token,
                            @RequestParam("password") String password,
                            @RequestParam("passwordNew") String passwordNew,
                            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        User u = getUser(token);

        if(u == null){
            throw new InvalidRequestException();

        }else{
            u.setPassword(password);
            u.setPasswordNew(passwordNew);

            UserRespDto respDto = userService.editUserPassword(u);
            responseInfo(response, TaorenUtils.o2j(respDto));
        }


    }



    /**
     * 修改手机号
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/phone")
    public void changePhone(@RequestParam("token") String token,
                            @RequestParam("phone") String phone,
                            @RequestParam(value = "areaCode", required = false) int areaCode,
                            @RequestParam("verifyCode") String verifyCode,
                            HttpServletResponse response)
                                                            throws InvalidRequestException, IOException {


        User u = getUser(token);

        if(u == null){
            throw new InvalidRequestException();

        }else{

            u.setPhone(phone);
            u.setVerifyCode(verifyCode);
            u.setAreaCode(areaCode);
            UserRespDto respDto = userService.editUserPhone(u);
            responseInfo(response, TaorenUtils.o2j(respDto));
        }
    }


    /**
     * 用户查找
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/search")
    public void searchUserByTrId(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String trId = request.getParameter("trId");

        EsUserDto esUserDto = userSearchService.findUserByTrId(trId);

        try{
            User u = getUser(request.getParameter("token"));
            if(u != null && u.getId() != null){
                updateUserPosition(u.getId(), request);
            }
        }catch (Exception e){
            //do nothing
        }

        responseInfo(response, TaorenUtils.o2j(esUserDto));
    }


    /**
     * 用户简介
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/intro")
    public void nearByUserFind(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String json = "[]";
        String trId = request.getParameter("ids");

        if(StringUtils.areNotEmpty(trId)){

            List<String> ids = Arrays.asList(trId.split(","));
            if(ids.size() > 20){
                ids = ids.subList(0, 20);
            }

            json = TaorenUtils.o2j(userSearchService.findUsersByIds(ids));

            User u = getUser(request.getParameter("token"));
            if(u != null && u.getId() != null){
                updateUserPosition(u.getId(), request);
            }
        }
        responseInfo(response, json);
    }


}