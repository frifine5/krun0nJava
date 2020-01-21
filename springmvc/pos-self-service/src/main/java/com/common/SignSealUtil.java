package com.common;

import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.util.Date;

public class SignSealUtil {

    /*
    SES_Signature::=SEQUENCE{
        toSign	TBS_Sign，	--待电子签章数据
        cert	OCTET STRING，	--签章者数字证书
        signatureAlgID OBJECT IDENTIFIER，	--签名算法标识
        signature	BIT STRING，	--电子签章中签名值
        timeStamp [0] BIT STRING  OPTIONAL  --对签名值的时间戳
    }

    TBS_Sign::=SEQUENCE{
        version	INTEGER，				--电子签章的版本, 本标准设定数值为3，代表当前版本为4；
        eseal	 SESeal，						--电子印章
        timeInfo	GeneralizedTime，			--签章时间
        dataHash	BIT STRING，				--原文杂凑值
        propertyInfo	IA5String，				--原文数据的属性
        extDatas [0] ExtensionDatas OPTIONAL	--自定义数据
    }
     */


    /**
     * 组装签章结构体，带签名数据已byte[] ,签名已完成
     */
    public static byte[] makeSeal(byte[] signValue, byte[] signerCert, byte[] toSign) throws IOException {
        byte[] sv = new byte[64];
        DERBitString signature;     // 签名值结构
        if(signValue.length == 64){
            signature = new DERBitString(signValue);
        }else if(signValue.length == 128) {
            System.arraycopy(signValue, 32, sv, 0, 32);
            System.arraycopy(signValue, 32+64, sv, 32, 32);
            signature = new DERBitString(sv);
        }else{
            throw new RuntimeException("签名值长度错误");
        }

        ASN1ObjectIdentifier signOid = new ASN1ObjectIdentifier("1.2.156.10197.1.501"); // 签名算法标识
        DEROctetString cert = new DEROctetString(signerCert);   // 签章者数字证书
        ASN1Sequence toSignSeq = (ASN1Sequence)ASN1Sequence.fromByteArray(toSign);  // 待签名结构

        ASN1Encodable[] sesArr = {toSignSeq, cert, signOid, signature};
        DERSequence sesSeque = new DERSequence(sesArr);

        return sesSeque.getEncoded();
    }


    /**
     * 组装toSign 结构体
     * @param esealByt 电子印章结构体
     * @param signTime 签名时间
     * @param hash 原为摘要值
     * @param prop 原文数据的属性
     * @return 电子签章带签名数据结构体
     * @throws IOException ASN1包的io数据异常，一般为数据结构的检查
     */
    public static byte[] combineToSignStruct(byte[] esealByt, Date signTime, byte[] hash, String prop) throws IOException{

        //  待电子签章数据  oSign	TBS_Sign
        ASN1Integer version = new ASN1Integer(3);       // 电子签章的版本, 本标准设定数值为3，代表当前版本为4；
        ASN1Sequence eseal = (ASN1Sequence)ASN1Sequence.fromByteArray(esealByt);    // 电子印章结构体数据
        ASN1GeneralizedTime timeInfo = new ASN1GeneralizedTime(signTime);   // 签名时间
        DERBitString dataHash = new DERBitString(hash); // 原文的摘要值
        DERIA5String propertyInfo = new DERIA5String(prop); // 原文数据的属性

        ASN1EncodableVector oSignArrVect = new ASN1EncodableVector();
        oSignArrVect.add(version);
        oSignArrVect.add(eseal);
        oSignArrVect.add(timeInfo);
        oSignArrVect.add(dataHash);
        oSignArrVect.add(propertyInfo);

        DERSequence toSign = new DERSequence(oSignArrVect);
        return toSign.getEncoded();
    }


}
