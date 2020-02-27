package com.user.request;

/**
 * 预览使用的请求参数
 * @author WangChengyu
 * 2020/2/27 16:50
 */
public class PersonSealPreviewRequest {

    private String code;
    private String name;
    private String sig;
    private String seal;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}
