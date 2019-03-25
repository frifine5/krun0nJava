package com.cer;

import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class SM2CertGenUtil {



    // general struct
    public static DERSequence generateCertTBSCert(int version, int serial, String algName, String subject, int validAge, byte[] pk){

        // 1) version 证书版本
        DERTaggedObject certVersion = new DERTaggedObject(true, 0, new DERInteger(version));
        // 2) serial 证书序列号
        DERInteger certSerial = new DERInteger(serial);
        // 3) algorithm oid
        // default sm2 算法OID
        ASN1Encodable[] smAlg = {GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE};
        DERSequence certAlg = new DERSequence(smAlg);
        // 4) issue 颁发者
        ASN1EncodableVector issueVector = new ASN1EncodableVector();
        ASN1Encodable[] issueName = {new DERObjectIdentifier(CertOidEnum.NAME.oid), new DERUTF8String("王师CA")};
        ASN1Encodable[] issueOrg = {new DERObjectIdentifier(CertOidEnum.ORGANIZATION.oid), new DERUTF8String("CA机构")};
        ASN1Encodable[] issueDN = {new DERObjectIdentifier(CertOidEnum.STREET.oid), new DERUTF8String("xx街道")};
        ASN1Encodable[] issueCity = {new DERObjectIdentifier(CertOidEnum.STATE.oid), new DERUTF8String("北京")};
        ASN1Encodable[] issueCountry = {new DERObjectIdentifier(CertOidEnum.COUNTRY.oid), new DERUTF8String("中华人民共和国")};
        issueVector.add(new DERSet(new DERSequence(issueName)));
        issueVector.add(new DERSet(new DERSequence(issueOrg)));
        issueVector.add(new DERSet(new DERSequence(issueDN)));
        issueVector.add(new DERSet(new DERSequence(issueCity)));
        issueVector.add(new DERSet(new DERSequence(issueCountry)));
        DERSequence certIssue = new DERSequence(issueVector);
        // 5) valid start add age to end 证书有效期（从--止）
        ASN1EncodableVector validVector = new ASN1EncodableVector();
        Date st = new Date();
        if(validAge <= 0 || validAge > 10){// default one year
            validAge = 1;
        }
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, thisYear + validAge);
        Date et = calendar.getTime();
        validVector.add(new ASN1UTCTime(st));
        validVector.add(new ASN1UTCTime(et));
        DERSequence certValid = new DERSequence(validVector);

        // 6) subject 证书的申请者/持有者/主体
        ASN1EncodableVector subjectVector = new ASN1EncodableVector();
        ASN1Encodable[] subjectName = {new DERObjectIdentifier(CertOidEnum.NAME.oid), new DERUTF8String(subject)};
        ASN1Encodable[] subjectDN = {new DERObjectIdentifier(CertOidEnum.STREET.oid), new DERUTF8String("xx街道")};
        ASN1Encodable[] subjectCity = {new DERObjectIdentifier(CertOidEnum.STATE.oid), new DERUTF8String("北京")};
        ASN1Encodable[] subjectCountry = {new DERObjectIdentifier(CertOidEnum.COUNTRY.oid), new DERUTF8String("中华人民共和国")};
        subjectVector.add(new DERSet(new DERSequence(subjectName)));
        subjectVector.add(new DERSet(new DERSequence(subjectDN)));
        subjectVector.add(new DERSet(new DERSequence(subjectCity)));
        subjectVector.add(new DERSet(new DERSequence(subjectCountry)));
        DERSequence certSubject = new DERSequence(subjectVector);

        // 7) publicKey 主体的公钥
        ASN1Encodable[] pkAlg = {new DERObjectIdentifier(CertOidEnum.SM2_ECPK.oid), new DERObjectIdentifier(CertOidEnum.SM2_ECPK_EX.oid)};
        DERSequence pkOid = new DERSequence(pkAlg);
        DERBitString pkString = new DERBitString(pk);
        ASN1Encodable[] pkOidAsn = {pkOid, pkString};
        DERSequence certPk = new DERSequence(pkOidAsn);

        // tbsc 证书主体
        ASN1Encodable[] tbsCertAsn = {certVersion, certSerial, certAlg, certIssue, certValid, certSubject, certPk};
        DERSequence certTBSCert = new DERSequence(tbsCertAsn);

        return certTBSCert;
    }


    /**
     * 证书主体， 签名值 ==》 证书结构体 | 指定为SM2算法oid
     * @return
     */
    public static DERSequence makeSM2Cert(DERSequence tbsc, byte[] signValue)throws Exception{
        DERObjectIdentifier oid = new DERObjectIdentifier(CertOidEnum.SM2.oid);
        ASN1Encodable[] oidArr = {oid, new DERNull()};
        DERSequence derOid = new DERSequence(oidArr);// 算法域

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
