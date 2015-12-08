package com.taoren.app.test.userBatch;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.model.FileItem;
import com.taoren.common.util.TaorenUtils;
import com.taoren.service.user.model.NewUserRespDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by king on 5/28/15.
 */
public class NewUser implements Runnable{

    String userServerUrl = "http://112.74.198.2:8080/trApp/signUp/user";
    String phoneServerUrl = "http://112.74.198.2:8080/trApp/signUp/phone";

    String areaCode="86";
    String phone = "";
    String password = "123456";
    String nickname = "";
    String birthday = "123456";
    double latitude = 0.0;
    double longitude = 0.0;

    public NewUser(String phone, String nickname, String birthday, double latitude, double longitude) {
        this.phone = phone;
        this.nickname = nickname;
        this.birthday = birthday;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void run() {
        System.out.println("phoneBegin:" + phone + new Date());
        try {
            newPhone();
            newUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("phoneEnd:" + phone + new Date());

    }


    private void newPhone() throws IOException{

        Map<String, String> queryParams = new HashMap<String, String>();
        Map appParams = new HashMap<String, String>();
        appParams.put("phone", phone);
        appParams.put("areaCode", areaCode);
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
        Map headerParams = new HashMap<String, String>();
        String token = "";
        boolean isSign = false;

        DefaultHttpClient.doPost(phoneServerUrl, queryParams, appParams, fileParams, headerParams, token, isSign);
    }

    private void newUser() throws IOException{

        Map<String, String> queryParams = new HashMap<String, String>();
        //应用参数
        Map appParams = new HashMap<String, String>();
        appParams.put("phone", phone);
        appParams.put("areaCode", areaCode);
        appParams.put("password", password);
        appParams.put("verifyCode", phone.substring(0, 3) + phone.substring(phone.length() - 3, phone.length()));
        appParams.put("nickname", nickname);
        appParams.put("birthday", birthday);

        appParams.put("latitude", latitude + "");
        appParams.put("longitude", longitude + "");

        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
        Map headerParams = new HashMap<String, String>();
        boolean isSign = false;

       String json = DefaultHttpClient.doPost(userServerUrl, queryParams, appParams, fileParams, headerParams, null, isSign);
//        if(json != null){
//            addLabels(json);
//        }
    }

    public void addLabels(String json){
        NewUserRespDto dto = TaorenUtils.j2o(json, NewUserRespDto.class);
        if(dto != null && dto.getToken() != null){
            Long uid = dto.getUid();
            String token = dto.getToken();
            String phone = this.getPhone();
            for(int i=0; i<=0; i++){
                try {
                    addLabel(uid, token ,phone);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void addLabel(Long uid, String token, String phone) throws IOException{
        String serverUrl = "http://112.74.198.2:8080/trApp/label/add";

        //查询参数
        Map<String, String> queryParams = new HashMap<String, String>();

//        queryParams.put("token", "1f0ad46b16994df895aabc531de8fc65");
        queryParams.put("token", token);

        queryParams.put("timestamp", System.currentTimeMillis() + "");
        queryParams.put("randomValue", new Random().nextLong() + "");

        //应用参数
        Map appParams = new HashMap<String, String>();

        String labelName = getRandomJianHan(5);

        appParams.put("labelName", labelName);

        appParams.put("labelDetail", labelName + "中...");
//        appParams.put("positionType", "1");
//
//        appParams.put("longitude", "114.028442");
//        appParams.put("latitude", "22.62487");
//        appParams.put("area", "深圳修电脑天下");

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

    public static String getRandomJianHan(int len)
    {
        String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try
            {
                str = new String(b, "GBk"); //转成中文
            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(getRandomJianHan(5));
    }
    public String getUserServerUrl() {
        return userServerUrl;
    }

    public void setUserServerUrl(String userServerUrl) {
        this.userServerUrl = userServerUrl;
    }

    public String getPhoneServerUrl() {
        return phoneServerUrl;
    }

    public void setPhoneServerUrl(String phoneServerUrl) {
        this.phoneServerUrl = phoneServerUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
