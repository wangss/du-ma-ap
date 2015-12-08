package com.taoren.app.util.http;


import com.taoren.app.constant.Constants;
import com.taoren.common.model.FileItem;
import com.taoren.common.util.TaorenUtils;
import com.taoren.common.util.WebUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * http client
 */
public class DefaultHttpClient {

	
	private static int connectTimeout = 15 * 1000;//10秒
	private static int readTimeout = 15 * 1000;//15秒


    public static String doPost(String serverUrl,
                                  Map<String, String> queryParams,
                                  Map<String, String> appParams,
                                  Map<String, FileItem> fileParams,
                                  Map<String, String> headerMap,
                                                         String secretKey, boolean isSign) throws IOException{

        //签名
        if(isSign){
            Map signMap = new HashMap<String, String>();

            signMap.putAll(queryParams);
            signMap.putAll(appParams);

            String sign = TaorenUtils.sign(signMap, secretKey);

            queryParams.put("sign", sign);

        }


        //上传文件
        boolean ifUploadFile = false;

		StringBuffer reqUrl = new StringBuffer(serverUrl);

		try {
			String query = WebUtils.buildQuery(queryParams, Constants.CHARSET_UTF8);

			if(reqUrl.indexOf("?") != -1){
				reqUrl.append("&");
			} else {
				reqUrl.append("?");
			}
			reqUrl.append(query);

		} catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println(reqUrl.toString());

		String rsp = null;
		try {

			// 是否需要上传文件

			if (ifUploadFile) {
				fileParams = TaorenUtils.cleanupMap(fileParams);

				rsp = doPost(reqUrl.toString(), appParams, fileParams, Constants.CHARSET_UTF8, connectTimeout, readTimeout, headerMap);

			} else {
				rsp = doPost(reqUrl.toString(), appParams, Constants.CHARSET_UTF8, connectTimeout, readTimeout, headerMap);
			}

            System.out.println("----------");
            System.out.println(rsp);
			return rsp;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


    public static void main(String[] args) throws Exception{


    }

	protected static String doPost(String url,
			Map<String, String> params, 
			String charset, 
			int connectTimeout, 
			int readTimeout,
			Map<String, String> headerMap) throws Exception {
		return WebUtils.doPost(url, params, charset, connectTimeout, readTimeout, headerMap);
	}
	
	protected static String doPost(String url,
			Map<String, String> params, 
			Map<String, FileItem> fileParams,
			String charset,
			int connectTimeout, 
			int readTimeout, 
			Map<String, String> headerMap) throws IOException {
		return WebUtils.doPost(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
	}
}
