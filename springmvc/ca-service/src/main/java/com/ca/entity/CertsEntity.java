package com.ca.entity;

public class CertsEntity {

    private long id;
    private int type;
    private String dn;
    private byte[] prtkey;
    private long fatherId;
    private byte[] cert;
    private String rdTime;

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

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public byte[] getPrtkey() {
        return prtkey;
    }

    public void setPrtkey(byte[] prtkey) {
        this.prtkey = prtkey;
    }

    public long getFatherId() {
        return fatherId;
    }

    public void setFatherId(long fatherId) {
        this.fatherId = fatherId;
    }

    public byte[] getCert() {
        return cert;
    }

    public void setCert(byte[] cert) {
        this.cert = cert;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }
}
