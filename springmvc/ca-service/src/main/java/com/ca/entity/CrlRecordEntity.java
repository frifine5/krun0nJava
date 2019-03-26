package com.ca.entity;

public class CrlRecordEntity {

    private long id;
    private byte[] data;
    private String rdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }
}
