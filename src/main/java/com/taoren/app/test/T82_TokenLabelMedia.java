package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T82_TokenLabelMedia {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8080/trApp/upload/token/label";
//        String serverUrl = "http://112.74.92.17:8080/trApp/login/out";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("token", "f153ae4a0a6d4771be9ed23b4296b3bd");
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("deviceType", "1");
        appParams.put("uuid", "dfjduii");
        appParams.put("longitude", "-30.00002");
        appParams.put("latitude", "1.000001");
        appParams.put("count", "3");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = "10000019612345678128";

        //isSign
        boolean isSign = true;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
