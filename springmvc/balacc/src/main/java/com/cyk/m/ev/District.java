package com.cyk.m.ev;

/**
 * 区划代码
 * @author WangChengyu
 * 2018/8/28 15:46
 */
public class District {
    private String dCode;
    private String dName;

    public District() {
    }

    public District(String dCode, String dName) {
        this.dCode = dCode;
        this.dName = dName;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }
}
