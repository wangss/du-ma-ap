package com.taoren.app.test;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class T28_UserReIndex {
    public static void main(String[] args) throws Exception{
//        String serverUrl = "http://localhost:8180/trApp/user/reindex/kldjfdfkqerinvkdfjdkfjdkjkjk";
        String serverUrl = "http://112.74.92.17:8080/trApp/user/reindex/kldjfdfkqerinvkdfjdkfjdkjkjk";
//        String serverUrl = "http://112.74.198.2:8080/trApp/user/reindex/kldjfdfkqerinvkdfjdkfjdkjkjk";


//        String serverUrl = "http://localhost:8180/trApp/user/delete/kldjfdfkqerinvkdfjdkfjdkjkjk";
//        String serverUrl = "http://112.74.198.2:8080/trApp/user/delete/kldjfdfkqerinvkdfjdkfjdkjkjk";



        String ids = "100000169";
        List<String> idList = Arrays.asList(ids.split("\n"));
        Map<String, String> queryParams = new HashMap<String, String>();
        //文件
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();

        //header
        Map headerParams = new HashMap<String, String>();



//        for(String id: idList){
       for(int i=100000001; i<100010001; i++){
                    String id = i+"";
            System.out.println(id);
            //应用参数
            Map appParams = new HashMap<String, String>();

            appParams.put("uid", id.trim());

            DefaultHttpClient.doPost(serverUrl, queryParams, appParams, fileParams, headerParams, null, false);
        }
        //查询参数


    }
}
