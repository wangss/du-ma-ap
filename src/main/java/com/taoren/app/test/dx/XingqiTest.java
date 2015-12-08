package com.taoren.app.test.dx;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;
import com.taoren.common.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 6/30/15.
 */
public class XingqiTest {
    public static void main(String[] args) throws Exception{
        String serverUrl = "http://121.40.137.165/OpenPlatform/OpenApi";


        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("action", "sendOnce");
        queryParams.put("ac", "1001@500712000016");
        queryParams.put("authkey", "5C08647E7FD7E0431AA96669E024419F");
        queryParams.put("cgid", "52");
//        queryParams.put("csid", "sendOnce");
        queryParams.put("c", "taor测试验证码123456");
        queryParams.put("m", "13535221349");


        //应用参数
        Map appParams = new HashMap<String, String>();
//        appParams.put("action","a");
//        appParams.put("password", "b");
//        appParams.put("verifyCode", "c");

        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();

        //token
        String token = "12345678130";

        //isSign
        boolean isSign = false;

        DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, token, isSign);


    }
}
