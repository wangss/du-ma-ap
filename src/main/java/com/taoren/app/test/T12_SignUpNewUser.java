package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T12_SignUpNewUser {
    public static void main(String[] args) throws IOException{
        String serverUrl = "http://localhost:8180/trApp/signUp/user";
//        String serverUrl = "http://112.74.92.17:8080/trApp/signUp/user";


//        String serverUrl = "http://112.74.198.2:8080/trApp/signUp/user";

        String phone = "13300000001";
        String areaCode = "86";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("phone",phone);
        appParams.put("areaCode", areaCode);
        appParams.put("password", "123456");
        appParams.put("verifyCode", phone.substring(0, 3) + phone.substring(phone.length() -3, phone.length()));
        appParams.put("nickname", "王水生");
        appParams.put("birthday", "1988-05-15");
//        appParams.put("birthday", "1980-01");
//        appParams.put("gender", "2");
//        appParams.put("avator", "/my.cnf");
        appParams.put("deviceType", "1");
        appParams.put("uuid", "fkdjfdkUUID");

        appParams.put("latitude", "22.625129");
        appParams.put("longitude", "114.028259");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
//        String token = "13500000009";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, null, isSign);


    }
}
