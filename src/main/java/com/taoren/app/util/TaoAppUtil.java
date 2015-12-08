package com.taoren.app.util;

import com.taoren.common.util.MD5Utils;
import com.taoren.common.util.StringUtils;
import com.taoren.model.user.User;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by king on 6/8/15.
 */
public abstract class TaoAppUtil {
    private TaoAppUtil() {}

    /**
     * 难请求有效性
     * @return 签名
     */
    public static boolean isRequestValid(Map<String, String[]> params, User u, long timestamp, String sign) {
//        System.out.println("Sign = " + sign);

        if(u == null || u.getPhone() == null || params.get("randomValue") == null){
            return false;
        }

        if(StringUtils.isEmpty(sign)){
            return false;
        }

        if((timestamp + 60 * 2 * 1000) < System.currentTimeMillis()){
            return false;
        }


        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder(u.getId() + u.getPhone());
        for (String key : keys) {
            if(key.equals("sign")){
                continue;
            }
            String value =  params.get(key) == null? null: params.get(key)[0];
            if (StringUtils.areNotEmpty(key, value)) {
                query.append(key).append(value);
//                System.out.println("key = " + key + "   value = " + value);
            }
        }

        String str = query.toString();

        try{
            str = java.net.URLEncoder.encode(query.toString(), "utf-8");
            System.out.println(str);
            System.out.println(MD5Utils.string2MD5(str));
        }catch (Exception e){}

        // 第三步：使用MD5加密
        if(sign.equals(MD5Utils.string2MD5(str))){
            return true;
        }else{
            return false;
        }

    }


    public static void main(String[] args) throws Exception{
        String str = java.net.URLEncoder.encode("*", "utf-8");
        System.out.printf(str);
    }
}
