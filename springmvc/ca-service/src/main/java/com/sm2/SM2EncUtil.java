package com.sm2;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

public class SM2EncUtil {
    //生成随机秘钥对
    public static void generateKeyPair() {
        SM2 sm2 = SM2.getInstance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();

        System.out.println("公钥: " + StrUtil.byteToHex(publicKey.getEncoded(false)));
        System.out.println("私钥: " + StrUtil.byteToHex(privateKey.toByteArray()));
    }

    //数据加密
    public static String encrypt(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return null;
        }

        if (data == null || data.length == 0) {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.getInstance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);

//      System.out.println("C1 " + Util.byteToHex(c1.getEncoded()));
//      System.out.println("C2 " + Util.byteToHex(source));
//      System.out.println("C3 " + Util.byteToHex(c3));
        //C1 C2 C3拼装成加密字串
        return StrUtil.byteToHex(c1.getEncoded(false)) + StrUtil.byteToHex(source) + StrUtil.byteToHex(c3);

    }

    //数据解密
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0) {
            return null;
        }
        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = StrUtil.byteToHex(encryptedData);
        /***分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
        byte[] c1Bytes = StrUtil.hexToByte(data.substring(0, 130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = StrUtil.hexToByte(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = StrUtil.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

        SM2 sm2 = SM2.getInstance();
        BigInteger userD = new BigInteger(1, privateKey);

        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        //返回解密结果
        return c2;
    }


    public static void main(String[] args) throws Exception {
        //生成密钥对
//        generateKeyPair();
        System.out.println("-------------- split --------------");

        String plainText = "xiaoqi test some context | 这里是中文";
        byte[] sourceData = plainText.getBytes();

        //下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

//        // test 用生成公私钥加解密测试： 公私钥不匹配只会导致解密的结果是错误，但只要格式是正确的，解密仍可进行
//        pubk = "045683F56D8D7FFA385D96D7F5150DE6811369A0958254FC8DBCF98741DE2609FDA1D09084F7DEBA816531E609B614814A80D0746DD590049D692B15836527E4AE";
//        prik = "1C2238FA4B99D89BC9C5930EA0A2AFFAEB5D47B63FDC53036E529A62A9FE52B6";
        pubk = "04DA576036B8657CC8F3DF35F6112D8ED721CECEEF25A3B43EA03FF260110AD96C93B28E18ED9DAA41658B4A720D3A9F691E4DD1C5CA6C78BC9EAB6FD447F712EF";
        prik = "00D7BCD1984B1B53788E9A9322F52D69FB72F0984617890D9C4FFC9F1623DB17F2";


        GMTSM2 sm2 = GMTSM2.getInstance();
        String[] strPair = sm2.genPairOnString();
        pubk = strPair[0];
        prik = strPair[1];

        System.out.println("加密前: \t"+plainText);
        System.out.println("加密: ");
        String cipherText = SM2EncUtil.encrypt(StrUtil.hexToByte(pubk), sourceData);
        System.out.println(cipherText);
        System.out.println("解密: ");
        String decText = new String(SM2EncUtil.decrypt(StrUtil.hexToByte(prik), StrUtil.hexToByte(cipherText)));
        System.out.println(decText);

    }
}
