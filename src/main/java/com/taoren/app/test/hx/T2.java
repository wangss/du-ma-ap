package com.taoren.app.test.hx;

/**
 * Created by king on 6/29/15.
 */
public class T2 {
    public static void main(String[] args) throws Exception{
//        String str = "10000021115900000058labelDetailBbblabelNameGhh
        String str = "10000021115900000058labelDetailFffflabelNameTrymedia[\n" +
                "  {\n" +
                "    \"seq\" : \"1\",\n" +
                "    \"url\" : \"/img/label/100000211/20150629/600000053\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"seq\" : \"2\",\n" +
                "    \"url\" : \"/img/label/100000211/20150629/600000054\"\n" +
                "  }\n" +
                "]positionType1randomValue79030024timestamp1435572165872token64fc1eb23c7247308c7f9ddfd382baa2";

        System.out.println(str);
        str = java.net.URLEncoder.encode(str, "utf-8");
        System.out.println(str);
//        str = java.net.URLDecoder.decode(str, "utf-8");
//        System.out.println(str);
    }
}
