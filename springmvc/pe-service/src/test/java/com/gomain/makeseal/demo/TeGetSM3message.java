package com.gomain.makeseal.demo;

import com.common.utils.ParamsUtil;
import com.sm3.SM3Util;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TeGetSM3message {



    private static void sm3dg(){
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





}
