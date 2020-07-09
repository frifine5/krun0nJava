package com.example.shiro.springboot.user.vo;

import java.util.Set;

public class Role {
    private String id;
    private String roleName;

    private Set<Permission> permSets;

    public Role(String id, String roleName, Set<Permission> sets) {
        this.id = id;
        this.roleName = roleName;
        this.permSets = sets;
    }

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permission> getPermSets() {
        return permSets;
    }

    public void setPermSets(Set<Permission> permSets) {
        this.permSets = permSets;
    }
}
