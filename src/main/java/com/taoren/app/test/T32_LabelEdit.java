package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class T32_LabelEdit {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/label/edit";
        String serverUrl = "http://112.74.92.17:8080/trApp/label/edit";

        String token = "11308a6001cd4af9aec69be0a27b8ca6";
        String uid = "100000017";
        String phone = "15300000001";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        queryParams.put("token", token);
        queryParams.put("timestamp", System.currentTimeMillis()+"");
        queryParams.put("randomValue", new Random().nextLong()+"");

        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("labelId", "300000276");

//        appParams.put("labelName", "真的卖电脑");
//
        appParams.put("labelDetail", "*");

//        appParams.put("positionType","2");
//        appParams.put("longitude", "114.028442");
//        appParams.put("latitude", "22.62487");

//        appParams.put("media",
//                "[\n" +
//                        "  {\n" +
//                        "    \"seq\" : \"1\",\n" +
//                        "    \"url\" : \"/img/label/100000211/20150629/600000037\"\n" +
//                        "  },\n" +
//                        "  {\n" +
//                        "    \"seq\" : \"2\",\n" +
//                        "    \"url\" : \"/img/label/100000211/20150629/600000038\"\n" +
//                        "  }\n" +
//                        "]");
//



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
