package com.esr.entity;


import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * ruler of GMT0031 ESEAL
 * @author WangChengyu
 * 2019/12/9 16:35
 */
public class GMT0031Util {

    private Logger logger = LoggerFactory.getLogger(GMT0031Util.class);

    // combine e-seal
    public byte[] combineESeal(byte[] body, byte[] mkCert, byte[] signedData, boolean verifyBody ){
        // 校验body的数据格式
        if(verifyBody){

        }
        try{
            ASN1EncodableVector esealVect = new ASN1EncodableVector();
            ASN1Sequence sealInfo = (ASN1Sequence)ASN1Sequence.fromByteArray(body);
            esealVect.add(sealInfo);

            DEROctetString mkCerter = new DEROctetString(mkCert);
            ASN1ObjectIdentifier signAlgID = GMObjectIdentifiers.sm2sign_with_sm3;
            DERBitString signData = new DERBitString(signedData);

            ASN1Encodable[] signInfoArr = {mkCerter, signAlgID, signData};
            DERSequence signInfo = new DERSequence(signInfoArr);
            esealVect.add(signInfo);

            DERSequence eSeal = new DERSequence(esealVect);

            return eSeal.getEncoded();
        }catch (IOException e){
            logger.error("ESeal ASN.1 编码失败", e);
        }
        return null;
    }


    /**
     * 电子印章信息组成GMT0031待签数据
     * @param eSeal 电子印章数据
     * @return 待签数据byte[]
     */
    public byte[] prepareCombine(GmtEseal eSeal){
        // 验证参数是否存在空值和越界值
        GmtEseal.verifyESealNec(eSeal);

        // 参数组ASN1格式
        DERIA5String id = new DERIA5String(eSeal.getID());
        ASN1Integer version = new ASN1Integer(eSeal.getVersion());
        DERIA5String vid = new DERIA5String(eSeal.getVid());
        ASN1Encodable[] headerArr = {id, version, vid};
        DERSequence header = new DERSequence(headerArr);

        DERIA5String esId = new DERIA5String(eSeal.getEsID());

        ASN1Integer type = new ASN1Integer(eSeal.getType());
        DERUTF8String name = new DERUTF8String(eSeal.getName());
        List<String> certList = eSeal.getCertList();
        ASN1EncodableVector certVector = new ASN1EncodableVector();
        for (String certBase64: certList) {
            byte[] cert;
            try{
                cert = Base64.getDecoder().decode(certBase64);
            }catch (Exception e){
                logger.error("证书Base64解码失败");
                continue;
            }
            DEROctetString octCert = new DEROctetString(cert);
            certVector.add(octCert);
        }
        DERSequence asnCertList = new DERSequence(certVector);
        DERUTCTime createTime = new DERUTCTime(eSeal.getCreateDate());
        DERUTCTime validStart = new DERUTCTime(eSeal.getValidStart());
        DERUTCTime validEnd = new DERUTCTime(eSeal.getValidEnd());
        ASN1Encodable[] propertyArr = {type, name, asnCertList, createTime, validStart, validEnd};
        DERSequence property = new DERSequence(propertyArr);

        DERIA5String imgType = new DERIA5String(eSeal.getImageType());
        DEROctetString imgData = new DEROctetString(Base64.getDecoder().decode(eSeal.getImageData()));
        ASN1Integer imgWidth = new ASN1Integer(eSeal.getImageWidth());
        ASN1Integer imgHeight = new ASN1Integer(eSeal.getImageHeight());
        ASN1Encodable[] pictureArr = {imgType, imgData, imgWidth, imgHeight};
        DERSequence picture = new DERSequence(pictureArr);

        ASN1EncodableVector esealInfoVector = new ASN1EncodableVector();
        esealInfoVector.add(header);
        esealInfoVector.add(esId);
        esealInfoVector.add(property);
        esealInfoVector.add(picture);

        if(null != eSeal.getExDates() ){
            ASN1EncodableVector exDataVect = new ASN1EncodableVector();
            for (String exData: eSeal.getExDates() ) {
                byte[] data;
                try{
                    data = Base64.getDecoder().decode(exData);
                    ASN1Sequence ext = (ASN1Sequence)ASN1Sequence.fromByteArray(data);
                    exDataVect.add(ext);
                }catch (Exception e){
                    logger.error("证书Base64解码失败");
                }
            }
            if(exDataVect.size()>0){
                DERSequence extDatas = new DERSequence(exDataVect);
                DERTaggedObject extTagObj = new DERTaggedObject(0, extDatas);
                esealInfoVector.add(extTagObj);
            }
        }
        DERSequence esealInfo = new DERSequence(esealInfoVector);
        try{
            return esealInfo.getEncoded();
        }catch (IOException e){
            logger.error("esealInfo ASN.1 编码失败", e);
            return null;
        }
    }



    // combine e-sign-seal







}
