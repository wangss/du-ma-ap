package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T26_UserSearch {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/user/search";
//        String serverUrl = "http://112.74.92.17:8080/trApp/user/search";
        String serverUrl = "http://112.74.198.2:8080/trApp/user/search";

//

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
//
        String token = "2025c47341b54ac4bf6c38907527269a";
        String uid = "100000086";
        String phone = "13535221349";

        //应用参数
        Map appParams = new HashMap<String, String>();


        appParams.put("trId", "rusty");

//        appParams.put("ageRange", "4");
//        appParams.put("lastActiveTimeRange", "5");


//        appParams.put("page", "1");
//        appParams.put("pageSize", "20");

        appParams.put("lon", "114.028555");//经度
        appParams.put("lat", "22.625990");//纬度


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
