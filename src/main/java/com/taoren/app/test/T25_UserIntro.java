package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T25_UserIntro {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/user/intro";
        String serverUrl = "http://112.74.92.17:8080/trApp/user/intro";
//        String serverUrl = "http://112.74.198.2:8080/trApp/user/intro";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
////
//        queryParams.put("token", "fc52c93d3023485eb3af9a1a0a6e4fa3");
//        queryParams.put("timestamp", System.currentTimeMillis()+"");
//        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("ids", "100000003");

//        appParams.put("ageRange", "4");
//        appParams.put("lastActiveTimeRange", "5");


//        appParams.put("page", "1");
//        appParams.put("pageSize", "20");

        appParams.put("lon", "-114.028555");//经度
        appParams.put("lat", "22.625990");//纬度


        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = "10000012212345678102";

        //isSign
        boolean isSign = true;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
