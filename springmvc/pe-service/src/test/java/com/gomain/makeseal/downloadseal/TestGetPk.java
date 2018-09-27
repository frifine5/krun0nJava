package com.gomain.makeseal.downloadseal;

import com.common.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class TestGetPk {

    public static void main(String[] args) {
        List<NameValuePair> reqList = new ArrayList<>();
        // deviceNo=861414036987353&loginNo=liuyq1
        String loginNo = "liuyq1";
        String devNo = "861414036987353";
//        reqList.add(new BasicNameValuePair("loginNo", "default_user"));// 用户id
//        reqList.add(new BasicNameValuePair("deviceNo", "864361034895681"));// 设备号
        reqList.add(new BasicNameValuePair("loginNo", loginNo));// 用户id
        reqList.add(new BasicNameValuePair("deviceNo", devNo));// 设备号
        String url = "http://192.168.9.118:9901";
        //        url = "http://192.168.9.118:9090";
                url = "http://36.110.112.203:8112";
                url += "/asiainfo/getsm2Pk"; // 虎符服务端的地址和端口,以及获取公钥的接口/asiainfo/getsm2Pk
//        String urls = "http://192.168.9.118:9901/asiainfo/getsm2PkWithInnerWeb";
//        String rtn = HttpUtils.httpSendAndReceive(reqList, urls);
        String rtn = HttpUtils.httpSendAndReceive(reqList, url);
        System.out.println(rtn);

    }
}
