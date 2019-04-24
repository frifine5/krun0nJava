package com.gomain.makeseal.demo;

import com.common.utils.ParamsUtil;
import com.sm3.SM3Util;
import org.junit.Test;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TeGetSM3message {


    private static void sm3dg() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println();
        String split = "_";
        String appkey = "ZH1001";
        String nonce = uuid;
        String crt = ParamsUtil.formatTime19(new Date());


        StringBuffer sbf = new StringBuffer(nonce);
        sbf.append(split).append(crt).append(appkey);
        Base64.getEncoder().encodeToString(SM3Util.sm3Digest(sbf.toString().getBytes()));
    }


    @Test
    public void test1() {

        int a, b;

        a = 5;
        b = 9;
        System.out.printf("a=%d, b=%d", a, b);
        System.out.println();
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.printf("a=%s, b=%s", a, b);
        System.out.println();

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.printf("a=%s, b=%s", a, b);
        System.out.println();

    }


}
