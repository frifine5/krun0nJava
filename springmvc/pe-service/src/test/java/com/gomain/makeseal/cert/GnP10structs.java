package com.gomain.makeseal.cert;

import com.cer.SM2CaCert;
import com.cer.SM2CsrUtil;
import com.common.utils.FileUtil;
import com.common.utils.ParamsUtil;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;

public class GnP10structs {





//    @Test
    public void genPBody() throws  Exception{

        String fdp = "D:\\work documents\\统一电子印章\\税局\\pks\\";
        String p10bodydir = "D:\\work documents\\统一电子印章\\税局\\p10body\\";
        String p10SvDir = "D:\\work documents\\统一电子印章\\税局\\sv\\";


        File fd = new File(fdp);
        if(!fd.isDirectory()){
            System.out.println("不是目录");
            return;
        }

        // 获得目录下
        File[] files = fd.listFiles();
        for (File fx : files ) {
            String fname = fx.getName();
            System.out.println("文件名：" + fname);
            byte[] pkStr = FileUtil.fromDATfile(fdp + fname);
            System.out.println(new String(pkStr));
            byte[] pk = Base64.getDecoder().decode(new String(pkStr));
            if(fname.contains(".txt")){
                fname = fname.substring(0, fname.indexOf(".txt") );
            }

            System.out.println(fname);
            byte[] body = genP10bodybyte(pk, fname);
            String csrBodyBase64 = Base64.getEncoder().encodeToString(body);
            FileUtil.writeInFiles(p10bodydir + fname, csrBodyBase64);
            FileUtil.writeInFiles(p10SvDir + fname + "-signData", "");

        }


    }





    byte[] genP10bodybyte(byte[] pk, String cn) throws IOException {


        String st = "北京市";
        String l = "北京市";
        String o = "国家税务总局";
        String ou = "征管和科技发展处";

        // 0 : integer
        ASN1Integer version = new ASN1Integer(0);
        // 1 : sub
        DERSequence csrSubject;
        try{
            ASN1EncodableVector issueVector = new ASN1EncodableVector();
            ASN1Encodable[] issueName = {RFC4519Style.cn, new DERUTF8String(cn)};//2.5.4.3
            ASN1Encodable[] issueOU = { RFC4519Style.ou, new DERUTF8String(ou) };// 2.5.4.10
            ASN1Encodable[] issueOrg = { RFC4519Style.o, new DERUTF8String(o) };// 2.5.4.10
            ASN1Encodable[] issueLoc = { RFC4519Style.l, new DERUTF8String(l) };// 2.5.4.9
            ASN1Encodable[] issueState = { RFC4519Style.st, new DERUTF8String(st) };// 2.5.4.8
            ASN1Encodable[] issueCountry = { RFC4519Style.c, new DERPrintableString("CN") };// 2.5.4.6

            // 国家， 省/州，  地区， 组织， 组织单元， 单位通用名（主题名）
            issueVector.add(new DERSet(new DERSequence(issueCountry)));
            issueVector.add(new DERSet(new DERSequence(issueState)));
            issueVector.add(new DERSet(new DERSequence(issueLoc)));
            issueVector.add(new DERSet(new DERSequence(issueOrg)));
             issueVector.add(new DERSet(new DERSequence(issueOU)));
            issueVector.add(new DERSet(new DERSequence(issueName)));
            csrSubject = new DERSequence(issueVector);

        }catch (Exception e){
            throw new RuntimeException("使用者结构生成失败", e);
        }
        if(null == csrSubject){
            throw new RuntimeException("使用者结构生成失败");
        }
        // 2 : pk
        ASN1Encodable[] pkAlg
                = {new ASN1ObjectIdentifier("1.2.840.10045.2.1"), new ASN1ObjectIdentifier("1.2.156.10197.1.301")};


        DERSequence pkOid = new DERSequence(pkAlg);
        DERBitString pkString = new DERBitString(pk);
        ASN1Encodable[] pkOidAsn = {pkOid, pkString};
        DERSequence csrPk = new DERSequence(pkOidAsn);

        ASN1Encodable[] csrBody = {version, csrSubject, csrPk };
        DERSequence asnCsrBody = new DERSequence(csrBody);

        byte[] body = asnCsrBody.getEncoded();

        return body;
    }




    byte[] compBody2Csr(byte[] body, byte[] sv) throws IOException{

        ASN1ObjectIdentifier oid = new ASN1ObjectIdentifier("1.2.156.10197.1.501");
        ASN1Encodable[] oidArr = {oid};// , DERNull.INSTANCE};
        DERSequence derOid = new DERSequence(oidArr);// 算法域
        // 签名值
        byte[] x = new byte[32];
        byte[] y = new byte[32];
        System.arraycopy(sv, 0, x, 0, 32);
        System.arraycopy(sv, 32, y, 0, 32);
        DERInteger r = new DERInteger(new BigInteger(1, x));
        DERInteger s = new DERInteger(new BigInteger(1, y));
        ASN1Encodable[] rsArr = {r, s};
        DERSequence derBitStringSV = new DERSequence(rsArr);
        DERBitString derSV = new DERBitString(derBitStringSV.getEncoded());// 签名值域
        ASN1Encodable[] asnCertArr = {DERSequence.fromByteArray(body), derOid, derSV};
        DERSequence derCsr = new DERSequence(asnCertArr);
        byte[] csrByts = derCsr.getEncoded();

        System.out.println("------ p10 base64 string --------");
        String p10Str = Base64.getEncoder().encodeToString(csrByts);
        System.out.println(p10Str);

        return csrByts;
    }


    @Test
    public void testComCsrs() throws Exception{

        String p10bodydir = "D:\\work documents\\统一电子印章\\税局\\p10body\\";
        String p10SvDir = "D:\\work documents\\统一电子印章\\税局\\sv\\";

        String p10Csr = "D:\\work documents\\统一电子印章\\税局\\p10\\";


        File fd = new File(p10bodydir);
        if(!fd.isDirectory()){
            System.out.println("不是目录");
            return;
        }

        // 获得目录下
        File[] files = fd.listFiles();
        for (File fx : files ) {
            String name = fx.getName();

            byte[] body = Base64.getDecoder().decode(new String(FileUtil.fromDATfile(p10bodydir + name)));
            byte[] sv = Base64.getDecoder().decode(new String(FileUtil.fromDATfile(p10SvDir + name + "-signData")));

            byte[] csrByts = compBody2Csr(body, sv);

            String csrBase64 = Base64.getEncoder().encodeToString(csrByts);

            FileUtil.writeInFiles(p10Csr + name + ".csr", csrBase64);

        }




    }



    @Test
    public void testVfUks() throws Exception{

        String svStr = "45ua0DXi6YcQB9EMKwNyz25ks0pNFyUByHVJWG0q1knUbRYClJPRLSUqYg+66fR2YLm83vh7+opFVr29hJspbA==";

        String bodyStr = "MIIBGgIBADCBuTELMAkGA1UEBhMCQ04xEjAQBgNVBAgMCeWMl+S6rOW4gjESMBAGA1UEBwwJ5YyX5Lqs5biCMRswGQYDVQQKDBLlm73lrrbnqI7liqHmgLvlsYAxITAfBgNVBAsMGOW+geeuoeWSjOenkeaKgOWPkeWxleWkhDFCMEAGA1UEAww55Zu95a6256iO5Yqh5oC75bGA55S15a2Q5Y2w56ug5Yi25L2c57O757uf5Yi256ug5pON5L2c5ZGYMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE2FrBkVrSltv4hhKOtxL3YPtw/Fq3YQwQ+6939k+D1QVVMOKaE5Sn8YXG/5Dj25D5auOXsiPWLN1086z/e531PA==";
        byte[] body = Base64.getDecoder().decode(bodyStr);
        byte[] sv = Base64.getDecoder().decode(svStr);

        byte[] csrByt = compBody2Csr(body, sv);

        byte[] pk = SM2CaCert.getSM2PublicKeyFromCSR(csrByt);
        System.out.println("pk");
        System.out.println(Base64.getEncoder().encodeToString(pk));

        GMTSM2 sm2 = GMTSM2.getInstance();

        byte[] md = SM3Util.sm3Digest(body, pk);

        boolean verify = sm2.sm2Verify(md, sv, pk);

        System.out.println(verify);


    }




    @Test
    public void testVfs() throws Exception{

        String p10Csr = "D:\\work documents\\统一电子印章\\税局\\p10\\";


        File fd = new File(p10Csr);
        if(!fd.isDirectory()){
            System.out.println("不是目录");
            return;
        }

        // 获得目录下
        File[] files = fd.listFiles();
        for (File fx : files ) {

            String name = fx.getName();
            byte[] src = Base64.getDecoder().decode(new String(FileUtil.fromDATfile(p10Csr + name)));

            byte[] pk = SM2CaCert.getSM2PublicKeyFromCSR(src);
            byte[] sv = SM2CaCert.getSM2signatureValue(src);
            byte[] body = SM2CaCert.getSM2TBSCertificateDate(src);

            GMTSM2 sm2 = GMTSM2.getInstance();

            byte[] md = SM3Util.sm3Digest(body, pk);
            boolean verify = sm2.sm2Verify(md, sv, pk);
            System.out.println(name + " - 验证结果： \t" +verify);

        }

    }




    @Test
    public void testGnChg() throws Exception{

        String p10Csr = "D:\\work documents\\统一电子印章\\税局\\p10\\";
        String p10dest = p10Csr + "chg\\";
        File fd = new File(p10Csr);
        if(!fd.isDirectory()){
            System.out.println("不是目录");
            return;
        }

        // 获得目录下
        File[] files = fd.listFiles();
        for (File fx : files ) {
            if(fx.isDirectory()) continue;
            String name = fx.getName();
            String txt = new String(FileUtil.fromDATfile(p10Csr + name));
            if(name.contains(".csr")){
                name = name.substring(0, name.indexOf(".csr"));
            }
            inageFile(txt, p10dest + name + ".csr");
        }
    }

    void inageFile(String txt, String fn) throws Exception{
        int len = txt.length();
        String s = "-----BEGIN CERTIFICATE REQUEST-----";
        String e = "-----END CERTIFICATE REQUEST-----";
        int c = len %64 == 0 ? len/64 : len/64 +1;
        FileUtil.appendCtx2File2(fn, s);
        for (int i = 0; i < c; i++) {
            String line;
            if(i == c -1){
                line = txt.substring( 64 * i);
            }else{
                line = txt.substring( 64 * i, 64 *(i+1));
            }
            FileUtil.appendCtx2File2(fn, line);

        }
        FileUtil.appendCtx2File2(fn, e);

    }



}
