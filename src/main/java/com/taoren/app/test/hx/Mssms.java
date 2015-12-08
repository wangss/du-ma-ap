package com.taoren.app.test.hx;


        import java.io.IOException;
        import java.io.InputStream;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;
        import java.util.Map.Entry;


public class Mssms {
    private static int connectTimeOut = 5000;
    private static int readTimeOut = 10000;
    private static String requestEncoding = "UTF-8";
    public static int getConnectTimeOut() {
        return connectTimeOut;
    }
    public static void setConnectTimeOut(int connectTimeOut) {
        Mssms.connectTimeOut = connectTimeOut;
    }
    public static int getReadTimeOut() {
        return readTimeOut;
    }
    public static void setReadTimeOut(int readTimeOut) {
        Mssms.readTimeOut = readTimeOut;
    }
    public static String getRequestEncoding() {
        return requestEncoding;
    }
    public static void setRequestEncoding(String requestEncoding) {
        Mssms.requestEncoding = requestEncoding;
    }

    public static String doGet(String requrl,Map<?,?> parameters,String recvEndcoding){
        HttpURLConnection url_con=null;
        String responseContent = null;
        String vchartset=recvEndcoding==""?Mssms.requestEncoding:recvEndcoding;
        try {
            StringBuffer params=new StringBuffer();
            for (Iterator<?> iter=parameters.entrySet().iterator();iter.hasNext();) {
                Entry<?, ?> element=(Entry<?, ?>) iter.next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(),vchartset));
                params.append("&");
            }
            if(params.length()>0){
                params=params.deleteCharAt(params.length()-1);
            }
            URL url=new URL(requrl);
            url_con=(HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            System.setProperty("连接超时：", String.valueOf(Mssms.connectTimeOut));
            System.setProperty("访问超时：", String.valueOf(Mssms.readTimeOut));
            url_con.setDoOutput(true);//
            byte[] b=params.toString().getBytes();
            url_con.getOutputStream().write(b, 0,b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            InputStream in=url_con.getInputStream();
            byte[] echo=new byte[10*1024];
            int len=in.read(echo);
            responseContent=(new String(echo,0,len).trim());
            int code = url_con.getResponseCode();
            if (code != 200) {
                responseContent = "ERROR" + code;
            }
        } catch (Exception e) {
            System.out.println("网络故障:"+ e.toString());
        }finally{
            if(url_con!=null){
                url_con.disconnect();
            }
        }
        return responseContent;

    }
    public static String doGet(String reqUrl, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        String vchartset=recvEncoding==""?Mssms.requestEncoding:recvEncoding;
        try {
            StringBuffer params = new StringBuffer();
            String queryUrl = reqUrl;
            int paramIndex = reqUrl.indexOf("?");

            if (paramIndex > 0) {
                queryUrl = reqUrl.substring(0, paramIndex);
                String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
                String[] paramArray = parameters.split("&");
                for (int i = 0; i < paramArray.length; i++) {
                    String string = paramArray[i];
                    int index = string.indexOf("=");
                    if (index > 0) {
                        String parameter = string.substring(0, index);
                        String value = string.substring(index + 1, string.length());
                        params.append(parameter);
                        params.append("=");
                        params.append(URLEncoder.encode(value, vchartset));
                        params.append("&");
                    }
                }

                params = params.deleteCharAt(params.length() - 1);
            }
            URL url = new URL(queryUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(Mssms.connectTimeOut));
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(Mssms.readTimeOut));
            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            InputStream in = url_con.getInputStream();
            byte[] echo = new byte[10 * 1024];
            int len = in.read(echo);
            responseContent = (new String(echo, 0, len)).trim();
            int code = url_con.getResponseCode();
            if (code != 200) {
                responseContent = "ERROR" + code;
            }
        } catch (Exception e) {
            System.out.println("网络故障:"+ e.toString());
        }finally{
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;

    }
    public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        String vchartset=recvEncoding==""?Mssms.requestEncoding:recvEncoding;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
                Entry<?, ?> element = (Entry<?, ?>) iter.next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(), vchartset));
                params.append("&");
            }

            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setConnectTimeout(Mssms.connectTimeOut);
            url_con.setReadTimeout(Mssms.readTimeOut);
            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            byte[] echo = new byte[10 * 1024];
            int len = in.read(echo);
            responseContent = (new String(echo, 0, len)).trim();
            int code = url_con.getResponseCode();
            if (code != 200) {
                responseContent = "ERROR" + code;
            }

        }
        catch (IOException e) {
            System.out.println("网络故障:"+ e.toString());
        }
        finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "XFTB702182");
        map.put("scode", "702888");
        map.put("mobile","13535221349");
        map.put("tempid","MB-2013102300");
        map.put("content","@1@=123456");
        String temp = Mssms.doPost("http://mssms.cn:8000/msm/sdk/http/sendsms.jsp",map, "GBK");
        System.out.println("值:" + temp);
    }
}
