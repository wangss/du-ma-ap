package com.taoren.app.user.controller;


import com.taoren.app.base.controller.BaseController;

import com.taoren.common.util.TaorenUtils;
import com.taoren.model.user.UserInfo;
import com.taoren.service.sc.UserSearchRemoteService;
import com.taoren.service.user.SignUpRemoteService;

import com.taoren.service.user.model.NewPhoneRespDto;
import com.taoren.service.user.model.NewUserRespDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/signUp")
public class SignUpController extends BaseController{
    Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private SignUpRemoteService signUpService;

    @Autowired
    private UserSearchRemoteService userSearchService;


    /**
     * 注册手机号验证
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/phone")
    public void newPhone(@RequestParam("phone") String phone,
                             @RequestParam("areaCode") int areaCode,
                               HttpServletResponse response)throws IOException {
        NewPhoneRespDto respDto = signUpService.verifyNewPhone(areaCode, phone);
        responseInfo(response, TaorenUtils.o2j(respDto));

    }


    /**
     * 注册新帐号
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/user")
    public void newUser(@RequestParam("phone") String phone,
                        @RequestParam("areaCode") String areaCode,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        @ModelAttribute UserInfo userInfo,
                        HttpServletResponse response)throws IOException {
        NewUserRespDto respDto  = signUpService.createNewAccount(userInfo);

        //建立搜索索引
        try{
            if(respDto.getResultCode() == 1 && respDto.getUid() != null){
                userInfo.setUid(respDto.getUid());
                userInfo.setPrivacyFind(1);
                userSearchService.indexUser(userInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        responseInfo(response, TaorenUtils.o2j(respDto));
    }


}