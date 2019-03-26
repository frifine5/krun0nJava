package com.ca.entity;

public class FeeRecordEntity {

    private long id;
    private long sn;
    private int coefficient;
    private String rdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSn() {
        return sn;
    }

    public void setSn(long sn) {
        this.sn = sn;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }
}
