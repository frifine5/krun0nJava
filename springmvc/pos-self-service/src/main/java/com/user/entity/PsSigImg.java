package com.user.entity;

/**
 * 个人手写签和私章图 对象
 */
public class PsSigImg {

    private int id;
    private String account;
    private String sig1;
    private String sig2;
    private String sig3;
    private String seal;

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

    public String getSig1() {
        return sig1;
    }

    public void setSig1(String sig1) {
        this.sig1 = sig1;
    }

    public String getSig2() {
        return sig2;
    }

    public void setSig2(String sig2) {
        this.sig2 = sig2;
    }

    public String getSig3() {
        return sig3;
    }

    public void setSig3(String sig3) {
        this.sig3 = sig3;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}
