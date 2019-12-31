package com.esr.entity;


import java.util.Date;
import java.util.List;

/**
 * Guoban 电子签章结构
 * @author WangChengyu
 * 2019/12/10 11:37
 */
public class GbESignSeal {
    /*
    待电子签章数据:toSign
        版本:version	INTEGER
	    电子印章:eseal	看上面|
	    签章时间:timeInfo	GeneralizedTime
	    原文杂凑值:dataHash	BIT STRING
	    原文数据的属性:propertyInfo	IA5String
	    自定义数据:extDatas ::=SEQUENCE{extnID, critical, extnValue}	自定义扩展字段标识:extnID	OBJECT IDENTIFIER
		    自定义扩展字段是否关键:critical	BOOLEAN
		    自定义扩展字段数据值:extnValue	OCTET STRING
	签章者数字证书:cert	OCTET STRING
	签名算法标识:signatureAlgorithm	OBJECT IDENTIFIER
    签名值:signature	BIT STRING
    时间戳:timeStamp   BIT STRING OPTIONAL
     */

//    待电子签章数据:toSign ===============================================
//        版本:version	INTEGER
//        电子印章:eseal	看上面|
//        签章时间:timeInfo	GeneralizedTime
//        原文杂凑值:dataHash	BIT STRING
//        原文数据的属性:propertyInfo	IA5String
//        自定义数据:extDatas ::=SEQUENCE{extnID, critical, extnValue}
//            自定义扩展字段标识:extnID	OBJECT IDENTIFIER
//            自定义扩展字段是否关键:critical	BOOLEAN
//            自定义扩展字段数据值:extnValue	OCTET STRING

    int version;
    GbEseal eseal;
    Date timeInfo;
    String dataHash;
    String propertyInfo;
    List<String> extDatas;

//    签章者数字证书:cert	OCTET STRING
//    签名算法标识:signatureAlgorithm	OBJECT IDENTIFIER
//    签名值:signature	BIT STRING
//    时间戳:timeStamp   BIT STRING OPTIONAL
    String cert;
    String signatureAlgorithm;
    String signature;
    String timeStamp;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public GbEseal getEseal() {
        return eseal;
    }

    public void setEseal(GbEseal eseal) {
        this.eseal = eseal;
    }

    public Date getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(Date timeInfo) {
        this.timeInfo = timeInfo;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public String getPropertyInfo() {
        return propertyInfo;
    }

    public void setPropertyInfo(String propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    public List<String> getExtDatas() {
        return extDatas;
    }

    public void setExtDatas(List<String> extDatas) {
        this.extDatas = extDatas;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String show(){
        String properties =
                "GuoBan   ::esign struct:=======================================================================start\n"
                + String.format("[0][0]%24s: %s\n","version", version)
                + String.format("   [1]%24s: \n%s\n","eseal", eseal.showInner())
                + String.format("   [2]%24s: %s\n","timeInfo", timeInfo)
                + String.format("   [3]%24s: %s\n","dataHash", dataHash)
                + String.format("   [4]%24s: %s\n","propertyInfo", propertyInfo)
                + String.format("   [5]%24s: %s\n","extDatas", extDatas)
                + String.format("[1]%27s: %s\n","cert", cert)
                + String.format("[2]%27s: %s\n","signatureAlgorithm", signatureAlgorithm)
                + String.format("[3]%27s: %s\n","signature", signature)
                + String.format("[4]%27s: %s\n","timeStamp", timeStamp)
                + "GuoBan   ::esign struct:=======================================================================end\n\n"
                ;
        return properties;
    }
}
