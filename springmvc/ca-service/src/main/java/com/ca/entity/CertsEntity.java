package com.ca.entity;

/**
 * 证书（单证书）实体
 * @author WangChengyu
 * 2019/4/9 11:43
 */
public class CertsEntity {

    private long id;            // 证书序列号
    private int type;           // 证书类型： 0-签名证书， 1-加密证书
    private long fatherId;      // 上级证书序列号
    private long status;        // 证书状态： 0-使用， 1-提前吊销
    private String cert;        // 证书数据
    private String rdTime;      // 记录时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFatherId() {
        return fatherId;
    }

    public void setFatherId(long fatherId) {
        this.fatherId = fatherId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }
}
