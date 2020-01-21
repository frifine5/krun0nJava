package com.eod.rsa;

import com.common.FileUtil;
import org.junit.Test;

import java.security.*;

public class RsaTest {


    @Test
    public void test1()throws Exception{

        String ksp = "/home/kstmp/tmp.jks";
        KeyStore keyStore = KeyStore.getInstance("JKS");
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        keyPairGen.initialize(2048, new SecureRandom());

        KeyPair keyPair = keyPairGen.generateKeyPair();

        PrivateKey priKey = keyPair.getPrivate();
        PublicKey pubKey = keyPair.getPublic();

        System.out.println(priKey);
        System.out.println(pubKey);

        FileUtil.writeInFiles("/home/kstmp/s.bin", priKey.getEncoded());
        FileUtil.writeInFiles("/home/kstmp/p.bin", pubKey.getEncoded());


        System.out.println();


    }

}
