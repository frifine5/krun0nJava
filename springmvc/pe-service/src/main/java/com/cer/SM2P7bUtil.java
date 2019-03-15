package com.cer;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;

import java.util.Base64;

/**
 * p7b证书的解析工具
 * @author WangChengyu
 * 2019/3/15 16:05
 */
public class SM2P7bUtil {


    /**
     * 解析p7b格式的证书到证书链，仅获得证书链，不验证证书链是否正确
     * @param baseP7b .p7b类型证书的Base64字符
     * @return 证书链的Base64字符串数组
     */
    public static String[] getCertChainsFromP7b(String baseP7b){
        try{
            byte[] p7b = Base64.getDecoder().decode(baseP7b);
            ASN1Sequence baseSeq = (ASN1Sequence)ASN1Sequence.fromByteArray(p7b);
            ASN1Encodable at1 = ASN1Sequence.getInstance(baseSeq).getObjectAt(1);
            ASN1TaggedObject at1obj = ASN1TaggedObject.getInstance(at1);
            ASN1Encodable at10 = at1obj.getObjectParser(0, true);
            ASN1Encodable at103 = ASN1Sequence.getInstance(at10).getObjectAt(3);
            ASN1TaggedObject at103obj = ASN1TaggedObject.getInstance(at103);

            // 证书链结构
            ASN1Encodable at1030 = at103obj.getObjectParser(0, true);

            ASN1Sequence seqArr = ASN1Sequence.getInstance(at1030);
            int len = seqArr.size();
            String[] certChains = new String[len];
            for (int i = 0; i < seqArr.size(); i++) {
                ASN1Encodable at1030i = seqArr.getObjectAt(i);
                byte[] bci = ((ASN1Sequence)at1030i).getEncoded();
                certChains[i] = Base64.getEncoder().encodeToString(bci);
            }
            return certChains;
        }catch (Exception e){
            throw new RuntimeException("解析p7b过程失败", e);
        }
    }



}
