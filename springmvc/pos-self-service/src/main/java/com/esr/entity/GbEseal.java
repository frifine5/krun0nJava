package com.esr.entity;

import java.util.Date;
import java.util.List;

/**
 * Guoban电子印章
 * @author WangChengyu
 * 2019/12/10 11:12
 */
public class GbEseal {
/* // 总览结构
    电子印章结构体	印章信息:eSealInfo
        印章头:header
            标识:ID	IA5String
			版本号:version	INTEGER
			厂商ID:Vid	IA5String
		印章标识:esID	IA5String
		印章属性:property	印章类型:type	INTEGER
			印章名称:name	UTF8String
			签章人证书列表类型:certListType	INTEGER
			"签章人证书列表:certList
:: =choice{certs, certDigestList}"	签章人证书:certs	 =SEQUENCE OF Cert
				签章人证书杂凑:certDigestList	" =SEQUENCE OF CertDigestObj::=
SEQUENCE {type, value}"

			印章制作日期:createDate	GeneralizedTime
			印章有效起始日期:validStart	GeneralizedTime
			印章有效终止日期:validEnd	GeneralizedTime
		印章图像数据:picture	图像类型:type	IA5String
			图像数据:data	OCTET STRING
			图像显示宽度:width	INTEGER
			图像显示高度:height	INTEGER
		自定义数据:extDatas ::=SEQUENCE{extnID, critical, extnValue}	自定义扩展字段标识:extnID	OBJECT IDENTIFIER
			自定义扩展字段是否关键:critical	BOOLEAN
			自定义扩展字段数据值:extnValue	OCTET STRING
	制章者证书:cert	OCTET STRING
	签名算法标识:signAlgID	OBJECT IDENTIFIER
	签名值:signedValue	BIT STRING

 */

//    印章信息:esealInfo =========================================================
//      印章头:header
//          标识:ID	IA5String
//          版本号:version	INTEGER
//          厂商ID:Vid	IA5String
        String ID;
        int version;
        String vid;
//      印章标识:esID	IA5String
        String esID;
//      印章属性:property
//          印章类型:type	INTEGER
//          印章名称:name	UTF8String
//          签章人证书列表类型:certListType	INTEGER
//          "签章人证书列表:certList
//                  :: =choice{certs, certDigestList}"	签章人证书:certs
//                          =SEQUENCE OF Cert
//                              签章人证书杂凑:certDigestList	" =SEQUENCE OF CertDigestObj::=
//                                    SEQUENCE {type, value}"
//          印章制作日期:createDate	    UTCTime
//          印章有效起始日期:validStart	UTCTime
//          印章有效终止日期:validEnd	UTCTime
        int type;
        String name;
        int certListType;
        List<String> certList;
        Date createDate;
        Date validStart;
        Date validEnd;
//      印章图像数据:picture
//          图像类型:type	IA5String
//          图像数据:data	OCTET STRING
//          图像显示宽度:width	INTEGER
//          图像显示高度:height	INTEGER
        String imageType;
        String imageData;
        int imageWidth;
        int imageHeight;
//	    "自定义数据:extDatas
//            ::= SEQUENCE{entnID, critical, extnValue}"	自定义扩展字段标识:extnID	OBJECT IDENTIFIER
//          自定义扩展字段是否关键:critical	BOOLEAN
//          自定义扩展字段数据值:extnValue	OCTET STRING
        List<String> exDates;
//    制章者证书:cert	OCTET STRING
//    签名算法标识:signAlgID	OBJECT IDENTIFIER
//    签名值:signData	BIT STRING
        String mkCert;
        String mkSignAlgID;
        String signValue;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getEsID() {
        return esID;
    }

    public void setEsID(String esID) {
        this.esID = esID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCertListType() {
        return certListType;
    }

    public void setCertListType(int certListType) {
        this.certListType = certListType;
    }

    public List<String> getCertList() {
        return certList;
    }

    public void setCertList(List<String> certList) {
        this.certList = certList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getValidStart() {
        return validStart;
    }

    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public List<String> getExDates() {
        return exDates;
    }

    public void setExDates(List<String> exDates) {
        this.exDates = exDates;
    }

    public String getMkCert() {
        return mkCert;
    }

    public void setMkCert(String mkCert) {
        this.mkCert = mkCert;
    }

    public String getMkSignAlgID() {
        return mkSignAlgID;
    }

    public void setMkSignAlgID(String mkSignAlgID) {
        this.mkSignAlgID = mkSignAlgID;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public String show(){
        String properties =
                "GuoBan   ::eseal struct:=======================================================================start\n"
                + String.format("[0][0]%16s: %s\n%22s: %d\n%22s: %s\n",
                "ID", ID, "version", version, "Vid", vid)
                + String.format("   [1]%16s: %s\n", "esID", esID)
                + String.format("   [2]%16s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n",
                "type", type, "name", name, "certListType", certListType,"certList",  certList,
                "createDate", createDate, "validStart", validStart, "validEnd", validEnd)
                + String.format("   [3]%16s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n",
                "type", imageType, "data", imageData, "width", imageWidth, "height", imageHeight)
                + String.format("   [4]%16s: %s\n", "exDates", exDates)
                + String.format("\n[1]%19s: %s\n", "cert", mkCert)
                + String.format("[2]%19s: %s\n", "signAlgID", mkSignAlgID)
                + String.format("[3]%19s: %s\n", "signValue", signValue)
                + "GuoBan   ::eseal struct:=======================================================================end\n\n"
                ;
        return properties;
    }

    public String showInner(){
        String properties = String.format("\t\t[0][0]%24s: %s\n\t\t%30s: %d\n\t\t%30s: %s\n",
                        "ID", ID, "version", version, "Vid", vid)
                        + String.format("\t\t   [1]%24s: %s\n", "esID", esID)
                        + String.format("\t\t   [2]%24s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n" +
                        "\t\t%30s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n",
                        "type", type, "name", name, "certListType", certListType, "certList",  certList,
                        "createDate", createDate, "validStart", validStart, "validEnd", validEnd)
                        + String.format("\t\t   [3]%24s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n",
                        "type", imageType, "data", imageData, "width", imageWidth, "height", imageHeight)
                        + String.format("\t\t   [4]%24s: %s\n", "exDates", exDates)
                        + String.format("\n\t\t[1]%27s: %s\n", "cert", mkCert)
                        + String.format("\t\t[2]%27s: %s\n", "signAlgID", mkSignAlgID)
                        + String.format("\t\t[3]%27s: %s\n", "signValue", signValue)
                ;
        return properties;
    }

}
