package com.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.entity.StringEntity;
import java.net.URISyntaxException;


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

    public static String httpGetSendAndReceive(List<NameValuePair> reqList, String url) {
        String exMsg = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 加参数
        HttpGet httppost;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if(null != reqList && reqList.size()>0){
                for (int i = 0; i < reqList.size(); i++) {
                    NameValuePair nv = reqList.get(i);
                    uriBuilder.addParameter( nv.getName(), nv.getValue());
                }
            }
            httppost = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            exMsg = "Exception：GET组装参数异常";
            log.error(exMsg, e);
            return exMsg;
        }
        try {
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
            exMsg = "Exception：客户端协议";
            log.error(exMsg, e);
        } catch (UnsupportedEncodingException e) {
            exMsg = "Exception：字符集编码不支持";
            log.error(exMsg, e);
        } catch (IOException e) {
            exMsg = "Exception：I/O异常";
            log.error(exMsg, e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return exMsg;
    }



    public static String httpSendAndReceive(String jsonRequest, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        httppost.setHeader(HTTP.CONTENT_TYPE, "application/json");

        String localAuthIp = "60.216.5.244";
//        httppost.setHeader("AuthIP", localAuthIp);



        StringEntity strEntity;
        try {
            strEntity = new StringEntity(jsonRequest, "UTF-8");
            httppost.setEntity(strEntity);
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



    public static String httpSendAndReceiveByMethod(String jsonRequest, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        httppost.addHeader(HTTP.CONN_DIRECTIVE, "close");

        StringEntity strEntity;
        try {
            strEntity = new StringEntity(jsonRequest, "UTF-8");
            httppost.setEntity(strEntity);
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
