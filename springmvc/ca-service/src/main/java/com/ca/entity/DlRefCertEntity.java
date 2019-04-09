package com.ca.entity;

/**
 * 双证书关系实体
 * @author WangChengyu
 * 2019/4/9 11:50
 */
public class DlRefCertEntity {

    private long id;            // 申请单编号
    private long signSn;        // 签名证书序列号；
    private long encSn;         // 加密证书序列号；
    private String prtEncKey;   // 加密密钥的加密保护结构数据
    private String rdTime;      // 记录时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSignSn() {
        return signSn;
    }

    public void setSignSn(long signSn) {
        this.signSn = signSn;
    }

    public long getEncSn() {
        return encSn;
    }

    public void setEncSn(long encSn) {
        this.encSn = encSn;
    }

    public String getPrtEncKey() {
        return prtEncKey;
    }

    public void setPrtEncKey(String prtEncKey) {
        this.prtEncKey = prtEncKey;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }
}
