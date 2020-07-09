package com.example.shiro.springboot.user.vo;

public class Permission {
    String id;
    String permName;

    public Permission() {
    }

    public Permission(String id, String permName) {
        this.id = id;
        this.permName = permName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }
}
