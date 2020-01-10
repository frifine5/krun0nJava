package com.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * administrator user entity
 * @author WangChengyu
 * 2020/1/9 11:23
 */
public class AUser {

    private int id;
    private int roleId;
    private String roleName;
    private String account;
    private String name;
    private String pwd;
    private int statusCode;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crtTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleId == 0 ? "未分配":
                roleId == 1 ? "管理员":
                        roleId == 2 ? "审计员": "统计员";
    }

    public void setRoleName(String roleName) {
        this.roleName = roleId == 0 ? "未分配":
                roleId == 1 ? "管理员":
                        roleId == 2 ? "审计员": "统计员";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return statusCode == 0 ? "未启用":
                statusCode == 1 ? "有效":
                        statusCode == 2 ? "冻结": "注销";
    }

    public void setStatus(String status) {
        this.status = statusCode == 0 ? "未启用":
                statusCode == 1 ? "有效":
                        statusCode == 2 ? "冻结": "注销";
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}
