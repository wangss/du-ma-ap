package com.taoren.app.user.controller;


import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.TaorenUtils;

import com.taoren.model.user.User;
import com.taoren.model.user.UserInfo;
import com.taoren.service.user.LoginRemoteService;
import com.taoren.service.user.MessageRemoteService;
import com.taoren.service.user.model.LoginRespDto;

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


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private LoginRemoteService loginService;

    @Autowired
    private MessageRemoteService messageService;

    /**
     * 用户登录
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/in")
    public void login(
            @RequestParam(value="phone", required=false) String phone,
            @RequestParam(value="areaCode", required=false) Integer areaCode,
            @RequestParam(value="trId", required=false) String trId,
            @RequestParam("password") String password,
            @RequestParam(value="deviceToken", required=false) String deviceToken,
                                    HttpServletRequest request,
                                    HttpServletResponse response)throws IOException {

        String json = "";

        User user = new User();

        user.setPhone(phone);
        user.setAreaCode(areaCode);
        user.setTrId(trId);
        user.setPassword(password);
        user.setDeviceToken(deviceToken);


        LoginRespDto respDto = loginService.login(user);

        if(respDto.getResultCode() == 1){//表示登录成功

            UserInfo userInfo = respDto.getUserInfo();
            json = TaorenUtils.o2j(userInfo);

//            try{
//                if(deviceToken != null){
//                    messageService.pushMessageWhenLogin(deviceToken, userInfo.getId());
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//                //do nothing
//            }
            updateUser(userInfo, request);//更新用户信息(es)
        }else{
            json = TaorenUtils.o2j(respDto);
        }

        responseInfo(response, json);

    }


    /**
     * 用户退出
     * @param response
     * @throws IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/out")
    public void logout(@RequestParam("token") String token,
                       @ModelAttribute User user,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws InvalidRequestException,IOException {

        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request,u)){

            user.setId(u.getId());
            user.setPhone(u.getPhone());
            user.setAreaCode(u.getAreaCode());
            user.setToken(token);

            json = TaorenUtils.o2j(loginService.logout(user));

        }

        responseInfo(response, json);

    }



    /**
     * 忘记密码
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/password/forget")
    public void newUser(
            @RequestParam("areaCode") int areaCode,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpServletResponse response)throws IOException {

        User user = new User();
        user.setAreaCode(areaCode);
        user.setPhone(phone);
        user.setPassword(password);
        user.setVerifyCode(verifyCode);
        String json = TaorenUtils.o2j(loginService.passwordForget(user));

        logger.debug(json);

        responseInfo(response, json);
    }
}