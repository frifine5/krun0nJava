package com.cyk.util;

import com.sm3.Util;
import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Base64;

/**
 * 一些辅助功能的java实现方式
 *
 * @author WangChengyu
 * 2019/5/7 15:08
 */
public class SomeFunTest1 {


    @Test
    public void testGetMackAddress() {

        try {
            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("目标Ip= " + ia);

            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            System.out.println("mac数组长度：" + mac.length);
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                System.out.println("每8位:" + str);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            System.out.println("本机MAC地址:" + sb.toString().toUpperCase());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    @Test
    public void test(){

        String fileName = "a.png";
        String out = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(out);

        String nasPath = "/mnt/logs/nas/2020-04-07/";
        if(nasPath.contains("/mnt/logs/nas/")){
            nasPath = nasPath.substring(nasPath.indexOf("/mnt/logs/nas/") + 13);
        }
        System.out.println(nasPath);
    }

    @Test
    public void test1(){

        String s = "AAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAdYniR/hUQZkgAb6dR51D2bnmzd9VWI6g5y/OYX2+W+wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJYHLyAi0ND6eQ0eVigDQrfyiQe3Ec+wJE6+FVZZN4kq";

        System.out.println(Util.byteToHex(Base64.getDecoder().decode(s)));

        String h = "DDB5DAE482528387954E1A3BC7AF2CA3";
        System.out.println(h.length()/2);




    }

}
