package com.example.shiro.springboot.user.service;

import com.example.shiro.springboot.user.LoginService;
import com.example.shiro.springboot.user.vo.Permission;
import com.example.shiro.springboot.user.vo.Role;
import com.example.shiro.springboot.user.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ShiroLoginServiceImpl implements LoginService {

    public User getUserByName(String name) {
        return getMapByName(name);
    }


    // for test 
    private User getMapByName(String userName) {
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Permission perm1 = new Permission("1", "query");
        Permission perm2 = new Permission("2", "add");
        Set<Permission> permSets1 = new HashSet<>();
        permSets1.add(perm1);
        permSets1.add(perm2);
        Role role1 = new Role("1", "admin", permSets1);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role1);

        User user1 = new User("1", "wsl", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user1.getName(), user1);

        Permission perm3 = new Permission("3", "query");
        Set<Permission> permSets2 = new HashSet<>();
        permSets2.add(perm3);
        Role role2 = new Role("2", "user", permSets2);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role2);

        User user2 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user2.getName(), user2);


        return map.get(userName);
    }


}
