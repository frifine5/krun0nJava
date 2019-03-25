package com.common.utils;

import java.util.UUID;

/**
 * common result return to preview
 * @author WangChengyu
 * 2019/3/25 15:47
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;
    private String uuid;

    public Result() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUuid() {
        return (uuid == null || "".equals(uuid))?  UUID.randomUUID().toString().replaceAll("-", ""): uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
