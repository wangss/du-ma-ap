package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class RelationIsRefused {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8180/trApp/relation/isRefused";
//        String serverUrl = "http://112.74.92.17:8080/trApp/user/edit";

        String token = "5c78a0b86f1c491380957a64090961a2";
        String uid = "100000070";
        String phone = "15100000021";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        queryParams.put("token", token);
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();

        appParams.put("targetUid", "100000012" );


        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = uid + phone;

        //isSign
        boolean isSign = true;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
