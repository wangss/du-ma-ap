package com.taoren.app.base.controller;

import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.app.util.TaoAppUtil;
import com.taoren.common.util.StringUtils;

import com.taoren.model.user.User;
import com.taoren.model.user.UserInfo;
import com.taoren.service.sc.UserSearchRemoteService;
import com.taoren.service.user.UserInfoRemoteService;
import com.taoren.service.user.UserRemoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * controller 基础类
 */
public class BaseController {

    @Autowired
    protected UserRemoteService userService;

    @Autowired
    protected UserInfoRemoteService userInfoService;

    @Autowired
    protected UserSearchRemoteService userSearchService;

    @ExceptionHandler({Exception.class})
    public void handlerException(Exception e, HttpServletResponse response) throws IOException{
        e.printStackTrace();
        responseInfo(response, getSystemErrorReturn());
    }

    @ExceptionHandler({InvalidRequestException.class})
    public void handlerException(InvalidRequestException e, HttpServletResponse response) throws IOException{
//        e.printStackTrace();
        responseInfo(response, getIllegalRequestReturn());
    }


    /**
     * 获取操作用户
     * @param token
     * @return
     */
    protected User getUser(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        return userService.getUserbyToken(token);
    }

    /**
     * 验证请求是否合法
     * @param request
     * @param u
     * @return
     * @throws InvalidRequestException
     */
    protected boolean isValid(HttpServletRequest request, User u) throws InvalidRequestException{

        long  timestamp = 1L;
        try{
            timestamp = Long.parseLong(request.getParameter("timestamp"));

        }catch (Exception e){
            throw new InvalidRequestException();
        }

        String sign = request.getParameter("sign");

        if(TaoAppUtil.isRequestValid(request.getParameterMap(), u, timestamp, sign)){
            return true;
        }else{
            throw new InvalidRequestException();
        }
    }


    /**
     * 更新用户信息(es)
     * @param userInfo
     * @param request
     */
    protected void updateUser(UserInfo userInfo, HttpServletRequest request){
        try{
            if(request.getParameter("longitude")!= null && request.getParameter("latitude")!= null){
                userInfo.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                userInfo.setLatitude(Double.parseDouble(request.getParameter("latitude")));
            }
            userSearchService.updateUser(userInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 更新用户地理位置信息(es)
     * @param uid
     * @param request
     */
    protected void updateUserPosition(long uid, HttpServletRequest request){
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(uid);

        //更新索引
        try{
            if(StringUtils.areNotEmpty(request.getParameter("longitude"), request.getParameter("latitude"))){
                userInfo.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                userInfo.setLatitude(Double.parseDouble(request.getParameter("latitude")));
            }
            userSearchService.updateUserPosition(userInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void responseInfo(HttpServletResponse response, String info) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(info);
    }


    private String getSystemErrorReturn(){
        return "{\"msg\":\"系统出错，请重试\",\"resultCode\":3}";
    }


    private String getIllegalRequestReturn(){
        return "{\"msg\":\"鉴权失败，或已退出，请重新登录\",\"resultCode\":4}";
    }

}