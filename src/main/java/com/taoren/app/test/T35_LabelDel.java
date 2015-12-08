package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T35_LabelDel {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8180/trApp/label/del";
//        String serverUrl = "http://112.74.92.17:8080/trApp/label/del";
//        String serverUrl = "http://112.74.198.2:8080/trApp/label/del";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();


        String token = "a081eb8cd19a4a7994454b373967fff2";
        String uid = "100000525";
        String phone = "13184388190";

        queryParams.put("token", token);
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();

        appParams.put("labelId", "300000308");

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
