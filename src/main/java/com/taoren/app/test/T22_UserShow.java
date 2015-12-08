package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T22_UserShow {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/user/show";
        String serverUrl = "http://112.74.92.17:8080/trApp/user/show";
//        String serverUrl = "http://112.74.198.2:8080/trApp/user/show";



        String token = "74d3bc0cac5f44baa4aff255a71c94d2";
        String uid = "100000038";
        String phone = "15360495677";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        queryParams.put("token", token);
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //查询参数
//        Map<String, String> queryParams = new HashMap<String, String>();


        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("uid", "100000054");

//        appParams.put("labelId", "300000003");
        appParams.put("longitude", "114.028555");//经度
        appParams.put("latitude", "22.625990");//纬度


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
