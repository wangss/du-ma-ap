package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T41_AskingAdd {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/asking/add";
//                String serverUrl = "http://112.74.92.17:8080/trApp/asking/add";
        String serverUrl = "http://112.74.198.2:8080/trApp/asking/add";


        String token = "781d3a3452cb4acead945f34480d7785";
        String uid = "100000086";
        String phone = "13535221349";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

//        queryParams.put("token", "1f0ad46b16994df895aabc531de8fc65");
        queryParams.put("token", token);

        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();



        appParams.put("askingDetail", "这是第一个喊话...");

        appParams.put("longitude", "114.028442");
        appParams.put("latitude", "22.62487");
//        appParams.put("area", "深圳修电脑天下");
//        appParams.put("media", "[{\"seq\":1,\"url\":\"aaa/bbb/18\"},{\"seq\":2,\"url\":\"fkdjf/gfd/19\"}]");


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
