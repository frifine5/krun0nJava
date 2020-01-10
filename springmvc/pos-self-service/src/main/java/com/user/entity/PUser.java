package com.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * person user entity
 * @author WangChengyu
 * 2020/1/9 11:23
 */
public class PUser {

    private int id;
    private String account;
    private String pwd;
    private String name;
    private String code;
    private int codeType;
    private String codeTName;
    private int sexCode;
    private String sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    public String getCodeTName() {
        return codeType == 0 ? "身份证" :
                codeType == 1 ? "护照" :
                        codeType == 2 ? "军官证" :
                                codeType == 3 ? "驾驶证" :
                                        codeType == 11 ? "港澳通行证" :
                                                codeType == 12 ? "台胞证" :
                                                        codeType == 9 ? "其它" : "其它";
    }

    public void setCodeTName(String codeTName) {
        this.codeTName = codeType == 0 ? "身份证" :
                codeType == 1 ? "护照" :
                        codeType == 2 ? "军官证" :
                                codeType == 3 ? "驾驶证" :
                                        codeType == 11 ? "港澳通行证" :
                                                codeType == 12 ? "台胞证" :
                                                        codeType == 9 ? "其它" : "其它";
    }

    public int getSexCode() {
        return sexCode;
    }

    public void setSexCode(int sexCode) {
        this.sexCode = sexCode;
    }

    public String getSex() {
        return sexCode == 0 ? "男" : sexCode == 1 ? "女" : "中";
    }

    public void setSex(String sex) {
        this.sex = sexCode == 0 ? "男" : sexCode == 1 ? "女" : "中";
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}
