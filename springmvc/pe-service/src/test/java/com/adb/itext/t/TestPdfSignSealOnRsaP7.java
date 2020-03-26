package com.adb.itext.t;


import com.common.utils.FileUtil;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Date;

/**
 *
 * 手撕p7b -_- +!
 * @author WangChengyu
 * 2020/3/26 15:29
 */
public class TestPdfSignSealOnRsaP7 {


    @Test
    public void testCombineESeal2P7b()throws Exception{

            // 如没有这个文件 可以临时生成：  keytool -genkey -v -alias root -keyalg RSA -storetype PKCS12 -keystore d:/home/rsa202003.jks
        FileInputStream keyStoreStream = new FileInputStream("D:\\home\\rsa202003.jks");
        Security.addProvider(new BouncyCastleProvider());
        // 读取keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = "123456".toCharArray();
        keyStore.load(keyStoreStream, password);
        String alias = keyStore.aliases().nextElement();
        // 获得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
        // 获得证书链
        Certificate[] chain = keyStore.getCertificateChain(alias);
        if(chain.length<1){
            System.out.println("没有证书");
            return;
        }
        Certificate cert = chain[chain.length-1];

        ASN1Sequence certSeq = (ASN1Sequence)ASN1Sequence.fromByteArray(cert.getEncoded());


        ASN1Sequence certAt0 = (ASN1Sequence)certSeq.getObjectAt(0);
        ASN1Integer certSerial = (ASN1Integer) certAt0.getObjectAt(1);
        ASN1Sequence certIss = (ASN1Sequence)certAt0.getObjectAt(3);

//        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\cert1.cer", cert.getEncoded());

        // 手动组p7
        String esealStr = "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/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wqX9DwAACAASURBVHic7Z13fBTFF8DvLYSErpQkBKQmEQg11IRAqIJA6CU06UVAikKCUowRpEgH4egCkSIggkQBRUGlCVhAQlNBFFECKGJXfp/fXnLJvdl+t7Ptst8/IHc7O/tm3t7ulFccDhsbGxsbGxsbGxuzweTLH1AgMCglJSiwQED+fIzR8tjQJl+hwkWKFiv+0MMlSpYqHRwSCgShIcGlS5Us8XDZF4oVLVK4UD6jpbXxEabcI+UrVKxUuUo4eEV4lcqVKlYo/0g5+6dvFSIfLV+1UrXq3ulZQPM1KlUt/2i60a2xkSBfzVq169SNVqtqTGi9OrVr1bQf9KajfoOGjd6MoalqTFTjRk0a1De6jTbZxLVr2qxUhFa69hBfrVnTR+OMbm0eJ655i5attNe1h1at21y1lW4MzGMvtg3RU9k5hLQt2s4ew+sL83it9h2NUHYOHdvXetxWuk4kdOrcxUhl59Clc6cEo/vC/+la/mEvl1K0JHx6+a5G94g/E9Ste7wqBcX26Nmrd2Kfvv36T5jQv1/fPom9e/XsEauqyvj/dStndL/4J08MTPRFNT169R00eMjQYZ2aDx8x0ilUsXPkiOHNOw0bOurJ0WPGlvHhEtFPDRynd2/4O13H9/dO27FdJkxs83Sn4fm9vVL+ZyYNbTFxQhfvLhddeaj9bKdG3OSHvfjhJSV/XezZ56aqvejU54pMm5KcpPy6raZPtmfoNHi0+FKlfd5r0POdUqjOlpiUTkMG9VJ6/RkvPErz4nmR1BdnKuvrWS+1mDRbKylmT5rz0ixlcsx8MVUrKfIAc+eFKejjMi2LvTxfe2ECXy7WUsmbJbPRXO2F8UfqF3lKvncjFhTb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";
        byte[] esealByt = Base64.getDecoder().decode(esealStr);

        ASN1ObjectIdentifier contentType0 = PKCSObjectIdentifiers.signedData;

        ASN1Integer version = new ASN1Integer(1);

        // new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1")   // sha256
        // new ASN1ObjectIdentifier("1.3.14.3.2.26")            // sha1
        ASN1Encodable[] set1 = { new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1") , DERNull.INSTANCE};
        DERSet digAlgs1 = new DERSet(new DERSequence(set1));

        ASN1Encodable[] content1 = {PKCSObjectIdentifiers.data};
        DERSequence contentInfo1 = new DERSequence(content1);

        ASN1TaggedObject certsTag = new DERTaggedObject(false, 0, new DERSequence(certSeq));

        // >>>>> 签名者信息集
        ASN1Integer sigVersion = new ASN1Integer(1);

        ASN1Encodable[] seq01 = {certIss, certSerial};
        DERSequence issAndSerial = new DERSequence(seq01);

        DERSequence sigAlgSeq = new DERSequence(set1);


        // 要认证属性

        byte[] md0 = "1234567890abcdef".getBytes(); // 替代的摘要

        MessageDigest m = MessageDigest.getInstance("SHA256");
        m.update(md0);
        byte[] md = m.digest();


        ASN1Encodable[] attSet1 = {PKCSObjectIdentifiers.pkcs_9_at_contentType, new DERSet(PKCSObjectIdentifiers.data)};
        DERSequence attSeq1 = new DERSequence(attSet1);
        ASN1Encodable[] attSet2 = {PKCSObjectIdentifiers.pkcs_9_at_signingTime, new DERSet(new DERUTCTime(new Date()))};
        DERSequence attSeq2 = new DERSequence(attSet2);
        ASN1Encodable[] attSet3 = {PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, new DERSet(new DEROctetString(esealByt))};
        DERSequence attSeq3 = new DERSequence(attSet3);
        ASN1Encodable[] attSet_last = {PKCSObjectIdentifiers.pkcs_9_at_messageDigest, new DERSet(new DEROctetString(md))};
        DERSequence attSeqlast = new DERSequence(attSet_last);

        ASN1EncodableVector attVect = new ASN1EncodableVector();
        attVect.add(attSeq1);
        attVect.add(attSeq2);
        attVect.add(attSeq3);
        attVect.add(attSeqlast);

        DERSet attSets = new DERSet(attVect); // for 签名
        DERTaggedObject attsTag = new DERTaggedObject(false, 0, new DERSequence(attVect));

        ASN1Encodable[] dasigAlg = {PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE};
        DERSequence dasigAlgSeq = new DERSequence(dasigAlg);


        // 计算摘要+签名
        String rsaAlgSign = "SHA256withRSA";
        Signature sig = Signature.getInstance(rsaAlgSign);
        sig.initSign(privateKey);
        sig.update(attSets.getEncoded());

        byte[] signedBytes = sig.sign();
        DEROctetString signed = new DEROctetString(signedBytes);

        // combine signerInfo

        ASN1Encodable[] signArr = {sigVersion, issAndSerial, sigAlgSeq, attsTag, dasigAlgSeq, signed};
        DERSequence signedInfo = new DERSequence(signArr);

        DERSet signedInfos = new DERSet(signedInfo);


        // combine context[0] of level l filed
        ASN1Encodable[] context0 = {version, digAlgs1, contentInfo1, certsTag, signedInfos};

        DERTaggedObject context0Tag = new DERTaggedObject(true, 0, new DERSequence(context0));

        // combine root
        ASN1Encodable[] rootEncode = {contentType0, context0Tag};

        DERSequence contentInfoSeqRoot = new DERSequence(rootEncode);
        System.out.println(contentInfoSeqRoot);


        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\testRsqP7pack-sha256.p7b", contentInfoSeqRoot.getEncoded());
        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\testRsqP7pack-sha256-B.p7b",
                Base64.getEncoder().encodeToString(contentInfoSeqRoot.getEncoded()));


    }



}
