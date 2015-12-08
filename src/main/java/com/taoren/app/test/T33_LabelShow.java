package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T33_LabelShow {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8080/trApp/label/show";
        String serverUrl = "http://112.74.92.17:8080/trApp/label/show";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        String token = "ae34055340374b72a79198dd05fc74f5";
        String uid = "100000017";
        String phone = "15300000001";
//        queryParams.put()

        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("uid", "100000017");

//        appParams.put("labelId", "300000003");


        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String secretKey = "15360495678";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, secretKey, isSign);

    }
}
