package com.to.sm3;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;


import java.io.UnsupportedEncodingException;
import java.security.Security;


public class Sm3Utils {

    private static final String ENCODING="utf-8";
    static{// 动态增加JCE
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 裸摘
     */
    public static String hashNokey(String ctx){
        String result = "";
        try{
            // 获得原文的byte数据
            byte[] in = ctx.getBytes(ENCODING);
            // 调用
            byte[] out = hash(in);
            // 转换为16进制字符串
            result = Hex.toHexString(out);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] hash(byte[] srcData){
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    public static byte[] hmac(byte[] key, byte[] srcData){
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }














    public static void main(String[] args) throws Exception{
        String ctx = "1234567890abcdef";
        System.out.println("without key");
        System.out.println(hashNokey(ctx));
        // sm3 right and pass on without key
        String hexPk = "abcdef1234567890";
        byte[] md2 = hmac(Hex.decode(hexPk), ctx.getBytes(ENCODING));
        String md2Str = Hex.toHexString(md2);
        System.out.println("with key");
        System.out.println(md2Str);

    }









}
