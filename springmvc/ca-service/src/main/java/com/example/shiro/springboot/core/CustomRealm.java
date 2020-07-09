package com.example.shiro.springboot.core;

import com.example.shiro.springboot.user.service.ShiroLoginServiceImpl;
import com.example.shiro.springboot.user.vo.Permission;
import com.example.shiro.springboot.user.vo.Role;
import com.example.shiro.springboot.user.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("customRealm")
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private ShiroLoginServiceImpl loginService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // get login-name
        String name = (String) principalCollection.getPrimaryPrincipal();

        // get user by login-name
        User user = loginService.getUserByName(name);
        if(null == user) return null;

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permission perm : role.getPermSets()) {
                simpleAuthorizationInfo.addStringPermission(perm.getPermName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = loginService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(name, user.getPwd(), this.getName());
        }
    }
}
