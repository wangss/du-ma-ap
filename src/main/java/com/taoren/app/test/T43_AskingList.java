package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T43_AskingList {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/asking/list";
                String serverUrl = "http://112.74.92.17:8080/trApp/asking/list";
//        String serverUrl = "http://112.74.198.2:8080/trApp/label/add";


        String token = "c074c98bbe964ff19b0339feedd994f1";
        String uid = "100000017";
        String phone = "15300000001";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

//        queryParams.put("token", "1f0ad46b16994df895aabc531de8fc65");
        queryParams.put("token", token);

        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();



//        appParams.put("askingDetail", "这是我的喊话");
//
        appParams.put("longitude", "114.028442");
        appParams.put("latitude", "22.62487");
////        appParams.put("area", "深圳修电脑天下");
//        appParams.put("media", "[{\"seq\":1,\"url\":\"aaa/bbb/16\"},{\"seq\":2,\"url\":\"fkdjf/gfd/17\"}]");


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
