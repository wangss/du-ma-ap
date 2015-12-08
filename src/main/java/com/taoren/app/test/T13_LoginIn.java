package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T13_LoginIn {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/login/in";
        String serverUrl = "http://112.74.92.17:8080/trApp/login/in";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("areaCode", "86");
        appParams.put("phone", "15360495677");
//        appParams.put("trId", "Trtest");
        appParams.put("password", "123456");

//        appParams.put("deviceType", "1");
//        appParams.put("uuid", "uuidlala");
        appParams.put("longitude", "114.028554");
        appParams.put("latitude", "22.62598");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
//        String secretKey = "10000008415360495678";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, null, isSign);

    }
}
