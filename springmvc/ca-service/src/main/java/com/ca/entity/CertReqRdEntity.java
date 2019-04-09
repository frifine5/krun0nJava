package com.ca.entity;

/**
 * 证书申请表：
 * @author WangChengyu
 * 2019/4/9 11:28
 */
public class CertReqRdEntity {

    private long id;            // 证书序列号，来自系统发号
    private String reqTime;     // 申请时间
    private int status;         // 申请状态
    private String unitName;    // 申请单位/组织名称
    private String unitUCode;   // 申请单位统一社会信用代码
    private String unitDisCode; // 申请单位地区编码（到县区一级的行政区划代码）
    private String unitAddr;    // 申请单位详细地址
    private String validStart;  // 有效期起 时间
    private String validEnd;    // 有效期止 时间
    private int age;            // 有效年期
    private String pk;          // 申请的签名公钥
    private byte[] p10;         // 申请的签名公钥的p10结构

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitUCode() {
        return unitUCode;
    }

    public void setUnitUCode(String unitUCode) {
        this.unitUCode = unitUCode;
    }

    public String getUnitDisCode() {
        return unitDisCode;
    }

    public void setUnitDisCode(String unitDisCode) {
        this.unitDisCode = unitDisCode;
    }

    public String getUnitAddr() {
        return unitAddr;
    }

    public void setUnitAddr(String unitAddr) {
        this.unitAddr = unitAddr;
    }

    public String getValidStart() {
        return validStart;
    }

    public void setValidStart(String validStart) {
        this.validStart = validStart;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public byte[] getP10() {
        return p10;
    }

    public void setP10(byte[] p10) {
        this.p10 = p10;
    }
}
