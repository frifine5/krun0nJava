package com.user.request;

/**
 * 个人印
 * @author WangChengyu
 * 2020/1/13 17:38
 */
public class PersonSealRequest {

    private String account;
    private String sig;
    private int sigNo;
    private String seal;
    private String imgType;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public int getSigNo() {
        return sigNo;
    }

    public void setSigNo(int sigNo) {
        this.sigNo = sigNo;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
}
