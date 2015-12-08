package com.taoren.app.upload.controller;


import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.TaorenUtils;
import com.taoren.model.user.User;
import com.taoren.service.user.UploadTokenRemoteService;
import com.taoren.service.user.model.UploadTokenDto;
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



/**
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/upload/token")
public class UploadTokenController extends BaseController{
    Logger logger = LoggerFactory.getLogger(UploadTokenController.class);


    @Autowired
    private UploadTokenRemoteService uploadTokenService;

    /**
     * 头像上传token
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/head")
    public void headToken(@RequestParam("token") String token,
                         HttpServletRequest request,
                         HttpServletResponse response)throws InvalidRequestException, IOException {

        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){

            UploadTokenDto uploadTokenDto = uploadTokenService.headerUploadToken(u.getId());

            if(uploadTokenDto.getResultCode() == 1){

                json = TaorenUtils.o2j(uploadTokenDto.getTokenList().get(0));
            }else{
                json = TaorenUtils.o2j(uploadTokenDto);
            }
        }

        responseInfo(response, json);
    }


    /**
     * label图片上传token
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/label")
    public void labelMediaToken(@RequestParam("token") String token,
                        HttpServletRequest request,
                        HttpServletResponse response)throws InvalidRequestException,IOException {

        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){

            long uid = u.getId();
            int count = 1;

            String countStr = request.getParameter("count");
            if(countStr != null){
                try{
                    count = Integer.parseInt(countStr);
                }catch (Exception e){
                    count = 1;
                }
            }

            UploadTokenDto uploadTokenDto =  uploadTokenService.labelMediaUploadToken(uid, count);

            if(uploadTokenDto.getResultCode() == 1){

                json = TaorenUtils.o2j(uploadTokenDto.getTokenList());

            }else{
                json = TaorenUtils.o2j(uploadTokenDto);
            }
        }
        responseInfo(response, json);
    }

    /**
     * label图片上传token
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/asking")
    public void askingMediaToken(@RequestParam("token") String token,
                                HttpServletRequest request,
                                HttpServletResponse response)throws InvalidRequestException,IOException {

        String json = "";

        //验证请求是否有效
        User u = getUser(token);

        if(isValid(request, u)){

            long uid = u.getId();
            int count = 1;

            String countStr = request.getParameter("count");
            if(countStr != null){
                try{
                    count = Integer.parseInt(countStr);
                }catch (Exception e){
                    count = 1;
                }
            }

            UploadTokenDto uploadTokenDto =  uploadTokenService.askingMediaUploadToken(uid, count);

            if(uploadTokenDto.getResultCode() == 1){

                json = TaorenUtils.o2j(uploadTokenDto.getTokenList());

            }else{
                json = TaorenUtils.o2j(uploadTokenDto);
            }
        }
        responseInfo(response, json);
    }


}