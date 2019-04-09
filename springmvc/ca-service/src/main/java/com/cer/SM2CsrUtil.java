package com.cer;


import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.math.BigInteger;

public class SM2CsrUtil {


    public static DERSequence genSm2CsrBody(int ver, byte[] pk, String cn, String c, String o){
        // 0 : integer
        ASN1Integer version = new ASN1Integer(ver);
        // 1 : subject
        ASN1EncodableVector subjectVector = new ASN1EncodableVector();
        ASN1Encodable[] dcn = {new ASN1ObjectIdentifier("2.5.4.6"), new DERUTF8String(c)};//国家
        ASN1Encodable[] dc = {new ASN1ObjectIdentifier("2.5.4.3"), new DERUTF8String(cn)};//名称
        ASN1Encodable[] dero = {new ASN1ObjectIdentifier("2.5.4.10"), new DERUTF8String(o)};//组织机构
        ASN1Encodable[] dera = {new ASN1ObjectIdentifier("2.5.4.7"), new DERUTF8String("ChengDu")};//地区
        subjectVector.add(new DERSet(new DERSequence(dcn)));
        subjectVector.add(new DERSet(new DERSequence(dc)));
        subjectVector.add(new DERSet(new DERSequence(dero)));
        // add some gomain rule
        subjectVector.add(new DERSet(new DERSequence(dera)));

        DERSequence csrSubject = new DERSequence(subjectVector);
        // 2 : pk
        ASN1Encodable[] pkAlg = {new ASN1ObjectIdentifier("1.2.840.10045.2.1"), new ASN1ObjectIdentifier("1.2.156.10197.1.301")};
        DERSequence pkOid = new DERSequence(pkAlg);
        DERBitString pkString = new DERBitString(pk);
        ASN1Encodable[] pkOidAsn = {pkOid, pkString};
        DERSequence csrPk = new DERSequence(pkOidAsn);

        // csr 主体
        ASN1Encodable[] csrBody = {version, csrSubject, csrPk};
        return new DERSequence(csrBody);
    }

    /**
     * csr 组装
     */
    public static DERSequence makeSM2Csr(DERSequence tbsc, byte[] signValue)throws IOException {
        ASN1ObjectIdentifier oid = new ASN1ObjectIdentifier("1.2.156.10197.1.501");
        ASN1Encodable[] oidArr = {oid, DERNull.INSTANCE};
        DERSequence derOid = new DERSequence(oidArr);// 算法域
        // 签名值
        byte[] x = new byte[32];
        byte[] y = new byte[32];
        System.arraycopy(signValue, 0, x, 0, 32);
        System.arraycopy(signValue, 32, y, 0, 32);
        DERInteger r = new DERInteger(new BigInteger(1, x));
        DERInteger s = new DERInteger(new BigInteger(1, y));
        ASN1Encodable[] rsArr = {r, s};
        DERSequence derBitStringSV = new DERSequence(rsArr);
        DERBitString derSV = new DERBitString(derBitStringSV.getEncoded());// 签名值域
        ASN1Encodable[] asnCertArr = {tbsc, derOid, derSV};
        return new DERSequence(asnCertArr);
    }




}
