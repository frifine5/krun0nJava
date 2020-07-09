package com.example.shiro.springboot.user.vo;

import java.util.Set;

public class User {
    String id;
    String name;
    String pwd;
    Set<Role> roles;

    public User(String id, String name, String pwd, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.roles = roles;
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
