package com.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


/**
 * 使用rsa2048进行签名验签
 * @author WangChengyu
 * 2019/4/10 15:28
 */
public class RsaUtil {

    final static Logger log = LoggerFactory.getLogger(RsaUtil.class);


    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static final String PLAIN_TEXT = "test string";


    public static byte[] sign(PrivateKey privateKey, byte[] ctx) {
        MessageDigest messageDigest;
        byte[] signed = null;
        try {
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
            messageDigest.update(ctx);
            byte[] outputDigest_sign = messageDigest.digest();
            log.info("SHA-256加密后-----》" +bytesToHexString(outputDigest_sign));
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(outputDigest_sign);
            signed = Sign.sign();
            log.info("SHA256withRSA签名后-----》" + bytesToHexString(signed));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("不支持的算法RSA2048", e);
        }
        return signed;
    }
    /**
     * 签名
     *
     * @param privateKey
     *            私钥
     * @param plain_text
     *            明文
     * @return
     */
    public static byte[] sign(PrivateKey privateKey, String plain_text) {
        MessageDigest messageDigest;
        byte[] signed = null;
        try {
//            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
//            messageDigest.update(plain_text.getBytes("UTF-8"));
//            byte[] in = messageDigest.digest();
//            log.info("SHA-256加密后-----》" +bytesToHexString(in));
            byte[] in = plain_text.getBytes("UTF-8");
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(in);
            signed = Sign.sign();
            log.info("SHA256withRSA签名后-----》" + bytesToHexString(signed));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("不支持的算法RSA2048", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符集UTF-8", e);
        }
        return signed;
    }

    /**
     * 验签
     *
     * @param publicKey
     *            公钥
     * @param plain_text
     *            明文
     * @param signed
     *            签名
     */
    public static boolean verifySign(PublicKey publicKey, String plain_text, byte[] signed) {

        MessageDigest messageDigest;
        boolean SignedSuccess=false;
        try {
//            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
//            messageDigest.update(plain_text.getBytes("UTF-8"));
//            byte[] in = messageDigest.digest();
//            log.info("SHA-256加密后-----》" +bytesToHexString(in));
            byte[] in = plain_text.getBytes("UTF-8");
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(in);
            SignedSuccess = verifySign.verify(signed);
            log.info("验证成功？---" + SignedSuccess);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("不支持的算法RSA2048", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的字符集UTF-8", e);
        }
        return SignedSuccess;
    }

    /**
     * bytes[]换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }




}
