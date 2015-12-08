package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T92_NearbyLabel {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8180/trApp/nearby/label";
//        String serverUrl = "http://112.74.92.17:8080/trApp/nearby/label";
//        String serverUrl = "http://112.74.198.2:8080/trApp/nearby/label";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
//

        String token = "2025c47341b54ac4bf6c38907527269a";
        String uid = "100000086";
        String phone = "13535221349";



        //应用参数
        Map appParams = new HashMap<String, String>();


//        appParams.put("keyword", "现在");
//        appParams.put("gender", "1");

//        appParams.put("ageRange", "4");
//        appParams.put("lastActiveTimeRange", "5");

//
//        appParams.put("page", "1");
//        appParams.put("pageSize", "100");

//        appParams.put("range", "10000");

        appParams.put("lat", "22.620374");
        appParams.put("lon", "114.038359");


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
