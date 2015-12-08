package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T24_UserPhoneChange {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8080/trApp/user/phone";
        String serverUrl = "http://112.74.92.17:8080/trApp/user/phone";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        queryParams.put("token", "45b73f7e8d064b7786342be9dfa55eca");
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("phone", "15360495679");

        appParams.put("verifyCode", "153679");



        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = "10000001315360495678";

        //isSign
        boolean isSign = true;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
