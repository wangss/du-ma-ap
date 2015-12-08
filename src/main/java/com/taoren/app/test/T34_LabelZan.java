package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T34_LabelZan {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8080/trApp/label/zan";
//        String serverUrl = "http://112.74.92.17:8080/trApp/label/zan";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();


        queryParams.put("token", "4361d47715f34315bba1622d0adfb03e");
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();

        appParams.put("uid", "100000015");
        appParams.put("labelId", "300000034");
        appParams.put("action", "1");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = "10000001513500000002";

        //isSign
        boolean isSign = true;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
