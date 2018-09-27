package com.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HttpUtils {
    final static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String httpSendAndReceive(List<NameValuePair> reqList, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(reqList, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                String rtn = "";
                if (entity != null) {
                    String jsonBody = EntityUtils.toString(entity, "UTF-8");
                    if(!jsonBody.trim().startsWith("{")){
                        rtn = jsonBody;
                    }else {
                        rtn = JSONObject.fromObject(jsonBody).toString();
                    }
                    log.info("Response content: " + rtn);
                }
                return rtn;
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            log.error("httpclient 请求异常：客户端协议", e);
        } catch (UnsupportedEncodingException e) {
            log.error("httpclient 请求异常：字符集编码", e);
        } catch (IOException e) {
            log.error("httpclient 请求异常：I/O异常", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


}
