package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T11_SignUpNewPhone {
    public static void main(String[] args) throws Exception{

        String serverUrl = "http://localhost:8180/trApp/signUp/phone";
//        String serverUrl = "http://112.74.92.17:8080/trApp/signUp/phone";

        String phone = "13300000001";
//        phone = "16200000272";
        String areaCode = "86";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("phone", phone);
        appParams.put("areaCode", areaCode);

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String token = "";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, token, isSign);

//     101   {"msg":"注册成功","resultCode":1,"time":"2015-06-16 16:23:18","token":"5a04a944d8fc466d927f0903098b7d77","uid":100000121}
//     102   {"msg":"注册成功","resultCode":1,"time":"2015-06-16 16:27:16","token":"fc52c93d3023485eb3af9a1a0a6e4fa3","uid":100000122}


    }
}
