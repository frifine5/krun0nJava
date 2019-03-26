package com.ca.entity;

public class CertReqRdEntity {

    private long id;
    private String reqTime;
    private int status;
    private String unitName;
    private String unitUCode;
    private String unitAddr;
    private String validStart;
    private String validEnd;
    private int age;
    private String pk;
    private byte[] p10;

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
