package com.tes;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;


public class TestBcSm2 {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        BCECPublicKey bcecPublicKey;

        /*
prime256v1
secp256r1
nistp256
secp256k1


        * */

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");

        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("Prime256v1");
        keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥
        PublicKey puk = keyPair.getPublic();
        // 获取私钥
        PrivateKey prk = keyPair.getPrivate();


        byte[] bsk = prk.getEncoded();
        byte[] bpk = puk.getEncoded();
        System.out.printf("%s\n%s\n", Base64.getEncoder().encodeToString(bsk), Base64.getEncoder().encodeToString(bpk));

        write(bsk, "C:\\Users\\49762\\Desktop\\sk.bin");
        write(bpk, "C:\\Users\\49762\\Desktop\\pk.bin");


        Signature sign = Signature.getInstance("SM3withSM2", "BC");
        sign.initSign(prk);
        String t = "123456123456123456123456123456xx";
        sign.update(t.getBytes());
        byte[] sv1 = sign.sign();

        System.out.println("sv1.len = " + sv1.length);

        sign.initVerify(puk);
        boolean right1 = sign.verify(sv1);
        System.out.println("验证v1: " + right1);


    }


    static void write(byte[] b, String f) throws Exception {
        File file = null;
        if (!(file = new File(f)).isFile()) {
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        out.write(b, 0, b.length);
        out.flush();
        out.close();
    }


    @Test
    public void testBCsm2T1() {

        String bpk = "BNEreIoS9Ht43MB+IA96UAfzkCTAQoDYPFchOt8vgkyximJUMRIwDybP8auna1/vVAm2cWWAJkI6yqL3E8tKXA8=";
        String bsk = "EjrRJkMilTngZTXZwp62S4+hrqLrs+87PoJkS6OuKgU=";


        BigInteger bigPrk = new BigInteger(Base64.getDecoder().decode(bsk));

        BCECPrivateKey bcecPrivateKey = GmUtil.getPrivatekeyFromD(bigPrk);

        byte[] gsk1 = bcecPrivateKey.getD().toByteArray();

        String s1 = Base64.getEncoder().encodeToString(gsk1);
        System.out.println(s1.equalsIgnoreCase(bsk));

        byte[] msg = "message digest".getBytes();
        byte[] userId = "1234567812345678".getBytes();
        byte[] sig = GmUtil.signSm3WithSm2(msg, userId, bcecPrivateKey);
        System.out.println(Hex.toHexString(sig));
        String bsv = Base64.getEncoder().encodeToString(sig);


        System.out.println(
                " String bsv = \""+bsv+"\";\n" +
                        " String bpk = \""+bpk+"\";\n" +
                        "GMTSM2 sm2 = GMTSM2.getInstance();\n" +
                        "        byte[] pk = Base64.getDecoder().decode(bpk);\n" +
                        "        byte[] sv = Base64.getDecoder().decode(bsv);\n" +
                        "        byte[] msg = \"message digest\".getBytes();\n" +
                        "        byte[] md = SM3Util.sm3Digest(msg, pk);\n" +
                        "\n" +
                        "        boolean v2 = sm2.sm2Verify(md, sv, pk);\n" +
                        "        System.out.println(\"verify:\\t\" + v2);"
        );


    }


}
