package com.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author WangChengyu
 * 2019/1/3 17:23
 */
public class Sha1Util {

    /**
     * 使用java.security包完成sha1摘要
     * @param src
     * @return
     */
    public static byte[] sha1BaseMD(byte[] src) {
        if(null == src || src.length <=0){
            throw new RuntimeException("原文为空");
        }
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(src);
            return messageDigest.digest();
        }catch (NoSuchAlgorithmException alg){
            throw new RuntimeException("SHA1算法不支持", alg);
        }
    }

    public static String sha1CompontMd(byte[] src){
        /*
    <dependency>
        <groupId>commons-codec</groupId>
        <artifactId>commons-codec</artifactId>
        <version>1.11</version>
    </dependency>
         */
        return DigestUtils.sha1Hex(src);
    }






}
