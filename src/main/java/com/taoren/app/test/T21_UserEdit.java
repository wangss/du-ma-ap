package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T21_UserEdit {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8180/trApp/user/edit";
//        String serverUrl = "http://112.74.92.17:8080/trApp/user/edit";


        String token = "3ff77b22118b47dc8b81db322e7fd7c2";
        String uid = "100000014";
        String phone = "13500000001";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        queryParams.put("token", token);
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();

//        appParams.put("trId", "Tr_shuisheng" );

        appParams.put("nickname", "");

//        appParams.put("birthday", "1435903455257");
//        appParams.put("birthday", "2010-05-05");

//        appParams.put("qq", "89999943");


//        appParams.put("gender", "1");
//        appParams.put("qq", "110");
//        appParams.put("avatar", "/myavator.png");
//        appParams.put("longitude", "114.028555");//经度
//        appParams.put("latitude", "22.625990");//纬度
//
//        appParams.put("hobby", "唱歌、跳舞");
        appParams.put("signature", "");

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
