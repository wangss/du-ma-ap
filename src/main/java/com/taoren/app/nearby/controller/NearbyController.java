package com.taoren.app.nearby.controller;

import com.taoren.app.base.controller.BaseController;
import com.taoren.app.common.exception.InvalidRequestException;
import com.taoren.common.util.StringUtils;
import com.taoren.common.util.TaorenUtils;

import com.taoren.model.user.User;
import com.taoren.service.ask.AskingRemoteService;
import com.taoren.service.ask.model.AskingMediaListRespDto;
import com.taoren.service.ask.model.EsAskingDto;
import com.taoren.service.sc.LabelSearchRemoteService;
import com.taoren.service.sc.NearByAskingSearchRemoteService;
import com.taoren.service.sc.NearBySearchRemoteService;
import com.taoren.service.sc.model.SearchParams;
import com.taoren.service.user.model.EsUserDto;
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
import java.util.Arrays;
import java.util.List;


/**
 *
 * wangshuisheng
 */
@Scope("prototype")
@Controller
@RequestMapping("/nearby")
public class NearbyController extends BaseController{

    Logger logger = LoggerFactory.getLogger(NearbyController.class);

    @Autowired
    NearBySearchRemoteService nearBySearchService;

    @Autowired
    NearByAskingSearchRemoteService nearByAskingSearchService;

    @Autowired
    AskingRemoteService askingService;

    @Autowired
    LabelSearchRemoteService labelSearchService;


    /**
     * 附近用户
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/user")
    public void nearbyUser(
            @ModelAttribute SearchParams searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        List<EsUserDto> userList = nearBySearchService.nearByUser(searchParams);
        String token = request.getParameter("token");
        User u = getUser(token);

        if(token != null && u == null){
            throw new InvalidRequestException();
        }

        if(u != null && u.getId() != null){
            updateUserPosition(u.getId(), request);
        }

        responseInfo(response, TaorenUtils.o2j(userList));
    }

    /**
     * 附近标签
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/label")
    public void nearbyLabel(
            @ModelAttribute SearchParams searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException,IOException {

        List<EsUserDto> userList = nearBySearchService.nearByLabel(searchParams);

        String token = request.getParameter("token");
        User u = getUser(token);

        if(token != null && u == null){
            throw new InvalidRequestException();
        }

        if(u != null && u.getId() != null){
            updateUserPosition(u.getId(), request);
        }

        responseInfo(response, TaorenUtils.o2j(userList));

    }


    /**
     * 附近标签
     * @param response
     * @throws java.io.IOException
     * @Author wangshuisheng
     */
    @RequestMapping(value = "/asking")
    public void nearbyAsking(
            @ModelAttribute SearchParams searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvalidRequestException, IOException {

        List<EsAskingDto> askingDtoList = nearByAskingSearchService.nearByAsking(searchParams);

        for(EsAskingDto askingDto: askingDtoList){

            AskingMediaListRespDto dto = askingService.getAskingMedia(askingDto.getAskingId());
            if(dto != null && dto.getResultCode() == 1){
                askingDto.setMediaList(dto.getList());
            }
        }

        String token = request.getParameter("token");

        User u = getUser(token);
        if(token != null && u == null){
            throw new InvalidRequestException();
        }

        if(u != null && u.getId() != null){
            updateUserPosition(u.getId(), request);
        }
        responseInfo(response, TaorenUtils.o2j(askingDtoList));

    }
}