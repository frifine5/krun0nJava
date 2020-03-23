package com.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.management.RuntimeMBeanException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

/**
 * 处理rsa的keypair文件pfx
 *
 * @author WangChengyu
 * 2019/4/10 15:32
 */
public class PfxUtil {

    final static Logger log = LoggerFactory.getLogger(PfxUtil.class);

    //转换成十六进制字符串
    public static String Byte2String(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            //if (n<b.length-1)  hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static byte[] StringToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue();  //将最高位保存在最低位
            temp = temp >> 8;   //向右移8位
        }
        return b;
    }

    public static Key[] GetKeyformPfx(String strPfxPath, String strPassword) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(strPfxPath);
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((strPassword == null) || strPassword.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = strPassword.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
//            log.info("keystore type=" + ks.getType());
            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu
            // In MS IE 6.
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements())// we are readin just one certificate.
            {
                keyAlias = (String) enumas.nextElement();
                log.info("alias=[" + keyAlias + "]");
            }
            // Now once we know the alias, we could get the keys.
//            log.info("is key entry=" + ks.isKeyEntry(keyAlias));
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
//            log.info("cert class = " + cert.getClass().getName());
//            log.info("cert = " + cert);
            log.info("rsa public key = " + pubkey);
            log.info("rsa private key = " + prikey);
            return new Key[]{prikey, pubkey};
        } catch (Exception e) {
            throw new RuntimeException("从pfx中提取密钥失败", e);
        }
    }


    /**
     * 生成rsa的PFX文件（java-软）：自签证书
     */
    public static byte[] genRsaPfx(String keyAlias, char[] password, String issuer, int day) throws Exception {
        String cn = "与休";
        String ou = "组织某部门";
        String o = "组织";
        String l = "某市";
        String st = "某省";
        String c = "CN";
        String[] issArr = issuer.split(",");
        if (issArr.length < 5) {
            throw new RuntimeException("主体信息不足");
        } else if (issArr.length == 5) {
            cn = issArr[0];
            o = issArr[1];
            l = issArr[2];
            st = issArr[3];
        } else {//第六项之后的都忽略
            cn = issArr[0];
            ou = issArr[1];
            o = issArr[2];
            l = issArr[3];
            st = issArr[4];
        }


        byte[] ksByts = null;
        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(null, null);
        CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
        X500Name x500Name = new X500Name(cn, ou, o, l, st, c);
        keypair.generate(2048);

        PrivateKey privateKey = keypair.getPrivateKey();
        X509Certificate[] chain = new X509Certificate[1];
        chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long) day * 24 * 60 * 60);

        KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");
        outputKeyStore.load(null, password);
        outputKeyStore.setKeyEntry(keyAlias, privateKey, password, chain);

        // store to byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outputKeyStore.store(baos, password);

        ksByts = baos.toByteArray();
        baos.close();
        System.out.println("create pfx Success !");

        return ksByts;

    }


}
