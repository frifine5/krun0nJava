package com.common;

import java.util.List;
import java.util.UUID;

/**
 * @author WangChengyu
 * 2018/8/24 11:21
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;
    private List list;
    private int total;
    private int pageNo;
    private int pageSize;
    private String reqId;

    public Result() {
        this.reqId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.reqId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Result(int code, String message, List list) {
        this.code = code;
        this.message = message;
        this.list = list;
        this.reqId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Result(int code, String message, List list, int total) {
        this.code = code;
        this.message = message;
        this.list = list;
        this.total = total;
        this.reqId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Result(int code, String message, List list, int total, int pageNo, int pageSize) {
        this.code = code;
        this.message = message;
        this.list = list;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.reqId = UUID.randomUUID().toString().replaceAll("-", "");
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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
