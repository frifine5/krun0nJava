package com.esr.entity;

import java.util.Date;
import java.util.List;

/**
 * GM/T 0031 电子印章
 * @author WangChengyu
 * 2019/12/9 16:36
 */
public class GmtEseal {
/* // 总览结构
印章信息:esealInfo	印章头:header	标识:ID	IA5String
		版本号:version	INTEGER
		厂商ID:Vid	IA5String
	印章标识:esID	IA5String
	印章属性:property	印章类型:type	INTEGER
		印章名称:name	UTF8String
		签章人证书列表:certList::=SEQUENCE OF Cert	签章人证书:cert
		印章制作日期:createDate	UTCTime
		印章有效起始日期:validStart	UTCTime
		印章有效终止日期:validEnd	UTCTime
	印章图像数据:picture	图像类型:type	IA5String
		图像数据:data	OCTET STRING
		图像显示宽度:width	INTEGER
		图像显示高度:height	INTEGER
	"自定义数据:extDatas
::= SEQUENCE{entnID, critical, extnValue}"	自定义扩展字段标识:extnID	OBJECT IDENTIFIER
		自定义扩展字段是否关键:critical	BOOLEAN
		自定义扩展字段数据值:extnValue	OCTET STRING
签名值:signInfo	制章者证书:cert	OCTET STRING
	签名算法标识:signAlgID	OBJECT IDENTIFIER
	签名值:signData	BIT STRING
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
//          签章人证书列表:certList::=SEQUENCE OF Cert	签章人证书:cert
//          印章制作日期:createDate	    UTCTime
//          印章有效起始日期:validStart	UTCTime
//          印章有效终止日期:validEnd	UTCTime
        int type;
        String name;
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
//    签名值:signInfo =========================================================
//      制章者证书:cert	OCTET STRING
//      签名算法标识:signAlgID	OBJECT IDENTIFIER
//      签名值:signData	BIT STRING
        String mkCert;
        String mkSignAlgID;
        String signData;


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

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }


    public String show(){
        String properties =
                "GM/T 0031::eseal struct:=======================================================================start\n"
                + String.format("[0][0]%16s: %s\n%22s: %d\n%22s: %s\n",
                "ID", ID, "version", version, "Vid", vid)
                + String.format("   [1]%16s: %s\n", "esID", esID)
                + String.format("   [2]%16s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n",
                "type", type, "name", name, "certList",  certList,
                "createDate", createDate, "validStart", validStart, "validEnd", validEnd)
                + String.format("   [3]%16s: %s\n%22s: %s\n%22s: %s\n%22s: %s\n",
                "type", imageType, "data", imageData, "width", imageWidth, "height", imageHeight)
                + String.format("   [4]%16s: %s\n", "exDates", exDates)
                + String.format("\n[1][0]%16s: %s\n", "cert", mkCert)
                + String.format("   [1]%16s: %s\n", "signAlgID", mkSignAlgID)
                + String.format("   [2]%16s: %s\n", "signData", signData)
                + "GM/T 0031::eseal struct:=======================================================================end\n\n"
                ;
        return properties;
    }

    public String showInner(){
        String properties = String.format("\t\t[0][0]%24s: %s\n\t\t%30s: %d\n\t\t%30s: %s\n",
                        "ID", ID, "version", version, "Vid", vid)
                        + String.format("\t\t   [1]%24s: %s\n", "esID", esID)
                        + String.format("\t\t   [2]%24s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n" +
                        "\t\t%30s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n",
                        "type", type, "name", name, "certList",  certList,
                        "createDate", createDate, "validStart", validStart, "validEnd", validEnd)
                        + String.format("\t\t   [3]%24s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n\t\t%30s: %s\n",
                        "type", imageType, "data", imageData, "width", imageWidth, "height", imageHeight)
                        + String.format("\t\t   [4]%24s: %s\n", "exDates", exDates)
                        + String.format("\n\t\t[1][0]%24s: %s\n", "cert", mkCert)
                        + String.format("\t\t   [1]%24s: %s\n", "signAlgID", mkSignAlgID)
                        + String.format("\t\t   [2]%24s: %s\n", "signData", signData)
                ;
        return properties;
    }

    /**
     * 电子印章结构的必要参数校验
     */
    public static boolean verifyESealNec(GmtEseal eseal){
        if(null == eseal) throw new NullPointerException("印章参数为空");
        // 查空字符串
        String arr1[] = {eseal.getID(), eseal.getEsID(), eseal.getImageType(), eseal.getImageData(),
            eseal.getName(), eseal.getVid()};
        for(String s : arr1){
            if(null == s || "".equalsIgnoreCase(s))
                throw new NullPointerException("印章字符类型参数为空");
        }
        // 查空集合签章人证书
        List certList = eseal.getCertList();
        if(certList  == null || certList.size() < 1)
            throw new NullPointerException("签章人证书列表为空");
        // 查负数
        Integer[] arr3 = {eseal.getVersion(), eseal.getType(), eseal.getImageWidth(), eseal.getImageHeight()};
        for(Integer i: arr3){
            if(null == i || i<0)
                throw new IllegalArgumentException("印章参数数字类型参数不合法");
        }
        // 查日期
        Date[] arr4 = {eseal.getCreateDate(), eseal.getValidStart(), eseal.getValidEnd()};
        for(Date d: arr4){
            if(null == d || d.getTime() <= 0 )
                throw new IllegalArgumentException("印章参数时间类型参数不合法");
        }
        if(eseal.getValidStart().compareTo(eseal.getValidEnd()) > 0)
            throw new IllegalArgumentException("印章参数有效期参数不合法");



        return true;
    }


}
