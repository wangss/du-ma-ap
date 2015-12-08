package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class ErrorTest {
    public static void main(String[] args) throws IOException{
        String serverUrl = "http://localhost:8080/trApp/signUp/user";
//        String serverUrl = "http://112.74.92.17:8080/trApp/signUp/user";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("phone","a");
        appParams.put("password", "b");
        appParams.put("verifyCode", "c");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String token = "12345678130";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, token, isSign);


    }
}
