package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class NewPhoneBatch {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/signUp/phone";
        String serverUrl = "http://112.74.92.17:8080/trApp/signUp/phone";


        for(long l=16300000001L; l<16300010001L; l++ ){
            String phone = l + "";
            Map<String, String> queryParams = new HashMap<String, String>();
            Map appParams = new HashMap<String, String>();
            appParams.put("phone", phone);
            Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
            Map headerParams = new HashMap<String, String>();
            String token = "";
            boolean isSign = false;
            DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, token, isSign);
        }



    }
}
