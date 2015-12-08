package com.taoren.app.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;


import java.util.*;

/**
 * Created by king on 5/27/15.
 */
public class T1 {

    @JSONField
    private Date d;

    public static void main(String[] args) throws Exception {
//
//        List<Label> labelList = new ArrayList<Label>();
//
//        List<LabelPic> labelPicList1 = new ArrayList<LabelPic>();
//        List<LabelPic> labelPicList2 = new ArrayList<LabelPic>();
//        List<LabelPic> labelPicList3 = new ArrayList<LabelPic>();
//
//        LabelPic lp1 = new LabelPic();
//        lp1.setId(10000000000000001L);
//        lp1.setLabelId(10000000000002L);
//        lp1.setUrl("url");
//        lp1.setMsg("msg");
//
//        LabelPic lp2 = new LabelPic();
//        lp2.setId(10000000000000001L);
//        lp2.setLabelId(10000000000002L);
//        lp2.setUrl("url");
//        lp2.setMsg("msg");
//        labelPicList1.add(lp1);
//        labelPicList1.add(lp2);
//        labelPicList2.add(lp1);
//        labelPicList2.add(lp2);
//        labelPicList3.add(lp1);
//        labelPicList3.add(lp2);
//
//
//        Label l1 = new Label();
//        l1.setId(10000001L);
//        l1.setLabelName("labelName");
//        l1.setLabelDetail("labelDetail");
//        l1.setLongitude(0.111123);
//        l1.setLatitude(0.23939);
//        l1.setArea("area");
//        l1.setPositionType(1);
//        l1.setPicList(labelPicList1);
//
//        Label l2 = new Label();
//        l2.setId(10000001L);
//        l2.setLabelName("labelName");
//        l2.setLabelDetail("labelDetail");
//        l2.setLongitude(0.111123);
//        l2.setLatitude(0.23939);
//        l2.setArea("area");
//        l2.setPositionType(1);
//        l2.setPicList(labelPicList2);
//
//        Label l3 = new Label();
//        l3.setId(10000001L);
//        l3.setLabelName("labelName");
//        l3.setLabelDetail("labelDetail");
//        l3.setLongitude(0.111123);
//        l3.setLatitude(0.23939);
//        l3.setArea("area");
//        l3.setPositionType(1);
//        l3.setPicList(labelPicList3);
//
//        labelList.add(l1);
//        labelList.add(l2);
//        labelList.add(l3);
//
//        String json = JSON.toJSONString(labelList);
//
//        System.out.println(json);


//        NewUserRespDto p = new NewUserRespDto();
//        p.setResultCode(1);
//        p.setUid(100000000001L);
//        p.setToken("dfkdjfkldjglkdjgkdjgldjdkjd");
//        p.setTime(new Date());
//        p.setMsg("注册成功");
//
//        UserInfoRespDto p = new UserInfoRespDto();
//        p.setTime(new Date());
//        p.setResultCode(1);
//        p.setMsg("修改成功");
//
//        System.out.println(TaorenUtils.o2j(p));

//        System.out.println(JSON.toJSON(null));
//        System.currentTimeMillis();

//        String str = "abcdefghi";
//        System.out.println(str.substring(0, 2));

//        String trId = "aaa, bbb, ccc";
//        List<String> ids = null;
//
//        ids = Arrays.asList(trId.split(","));
//
//        List<String> idList = ids;
//        if(ids.size() > 20){
//            idList = ids.subList(0, 20);
//        }
//        List<LabelMedia> list = new ArrayList<LabelMedia>();
//        LabelMedia lm = new LabelMedia();
//        lm.setSeq(1);
//        lm.setUrl("aaa/bbb");
//        LabelMedia lm2 = new LabelMedia();
//        lm2.setSeq(2);
//        lm2.setUrl("fkdjf/gfd");
//
//        list.add(lm);
//        list.add(lm2);
//
//        String str = TaorenUtils.o2j(list);
//
//
//        System.out.println(str);
//
//        List<LabelMedia> l2 = TaorenUtils.j2o(str, List.class);
//        System.out.println(l2);

//
//        String name=java.net.URLEncoder.encode("中文", "UTF-8");
//        System.out.println(name);
//        name=java.net.URLEncoder.encode(name,"UTF-8");
//        System.out.println(name);
//        name=java.net.URLDecoder.decode(name, "UTF-8");
//        System.out.println(name);
//        name = java.net.URLDecoder.decode(name, "UTF-8");
//        System.out.println(name);
//
//        String str = "10000020812345678130nickname啦啦啦randomValue-6806122070023789853timestamp1435312290165token05d4e60c9718453bacf67d144a524fa2trIdtr000002";
//
//        str = URLEncoder.encode(str, "utf-8");
//        System.out.println(str);
//        System.out.println(MD5Utils.string2MD5(str));

//        String str = "中文啦";
//        System.out.println(str);
//        str = URLEncoder.encode(str, "utf-8");
//        System.out.println(str);
//        str = URLEncoder.encode(str, "utf-8");
//        System.out.println(str);

//        String str = "10000021115900000058labelDetailBbblabelNameGhhmedia[\n" +
//                "  {\n" +
//                "    \"seq\" : \"1\",\n" +
//                "    \"url\" : \"/img/label/100000211/20150629/600000045\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"seq\" : \"2\",\n" +
//                "    \"url\" : \"/img/label/100000211/20150629/600000046\"\n" +
//                "  }\n" +
//                "]positionType1randomValue7195296timestamp1435569989203token64fc1eb23c7247308c7f9ddfd382baa2";
//
//        String a = "/";
//
//        try{
//            str = java.net.URLEncoder.encode(str, "utf-8");
//            System.out.println(str);
//            System.out.println(java.net.URLDecoder.decode(str, "utf-8"));

//            str = java.net.URLEncoder.encode(str, "utf-8");
//            System.out.println(str);
//            System.out.println();
//        }catch (Exception e){}
//
//        System.out.println(str);
//        System.out.println(MD5Utils.string2MD5(str));
//
//        Date d = new Date();
//        System.out.println(d);
//
//        System.out.println(null + "");
//        System.out.println("" + null);
//        System.out.println(System.currentTimeMillis());

//        String password = "111111";
//        System.out.println( MD5Utils.string2MD5(password).substring(7, 23));
//        User u = null;
//        System.out.println(TaorenUtils.o2j(u));
//        String json = TaorenUtils.o2j(u);
//        User u2 = TaorenUtils.j2o(json, User.class);
//        System.out.println(u2);
//        test("wang");
//        Map map = new HashMap();
//        map.put("aaa", "aaa");
//        map.put("bbb", "bbb");
//        map.put("111", 111);
//        System.out.println(TaorenUtils.o2j(map));
//        Long.parseLong("aa");

        long l = 100;
        System.out.println(l == 100L);
    }


    public static void test(String str) {
//        str="str";
//        System.out.println(str);

    }
}


