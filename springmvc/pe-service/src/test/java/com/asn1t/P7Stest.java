package com.asn1t;

import com.common.utils.FileUtil;
import com.rsa.PfxUtil;
import com.rsa.RsaUtil;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.junit.Test;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class P7Stest {



    @Test
    public void testType()throws Exception{


        /*
        * 1.2.840.113549.1.7
        * 1.2.840.113549.1.7
        * 1.2.840.113549.1.7
        * */

        ASN1ObjectIdentifier oid1 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.1");
        ASN1ObjectIdentifier oid2 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.2");
        ASN1ObjectIdentifier oid3 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.3");
        ASN1ObjectIdentifier oid4 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.4");
        ASN1ObjectIdentifier oid5 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.5");
        ASN1ObjectIdentifier oid6 = new ASN1ObjectIdentifier("1.2.840.113549.1.7.6");

        ASN1Encodable[] asn = {oid1, oid2, oid3, oid4, oid5, oid6};

        ASN1Sequence seq1 = new DERSequence(asn);

        byte[] encode = seq1.getEncoded();
        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\type.bin", Base64.getEncoder().encodeToString(encode));


    }

    @Test
    public void test1(){
        String pfxFile = "C:\\Users\\49762\\Desktop\\yuxiu-002.pfx";
        Key[] keyPair = PfxUtil.GetKeyformPfx(pfxFile, "12345678");
        System.out.println(keyPair);


        String txt = "一段测试信息：明文1234567890ABCDEF";
        System.out.println("do sign and verifySign");
        byte[] rsaSv1 = RsaUtil.sign((PrivateKey) keyPair[0], txt);
        System.out.println("sign over :\n"+RsaUtil.bytesToHexString(rsaSv1));

        boolean isVerify = RsaUtil.verifySign((PublicKey) keyPair[1], txt, rsaSv1);
        System.out.println("验签结果：\t"+isVerify);

    }

    @Test
    public void test0()throws Exception{
        String cn = "与休";
        String ou = "组织某部门";
        String o = "组织";
        String l = "某市";
        String st = "某省";
        String c = "CN";
        int day = 365*3;   // 3年
        String pwd = "12345678";
        String alias = "yuxiu";
        String iaName = "单位,组织,某市,某省,CN";
        String pfxFile = "C:\\Users\\49762\\Desktop\\yuxiu-002.pfx";
        byte[] pfxByte = PfxUtil.genRsaPfx(alias, pwd.toCharArray(), iaName, day);
        FileUtil.writeInFiles(pfxFile, pfxByte);


    }


}
