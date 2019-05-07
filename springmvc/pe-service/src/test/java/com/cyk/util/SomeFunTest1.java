package com.cyk.util;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;

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
}
