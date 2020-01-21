package com.user;

import com.user.entity.PUser;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Date;

public class UserTest {


    @Test
    public void testPUserJson(){

        PUser user1 = new PUser();
        user1.setCode("110101199901011234");
        user1.setCrtTime(new Date());
        System.out.println(user1.getCodeTName());

        System.out.println(JSONObject.fromObject(user1));
        System.out.println("--------------------------------------------");
        System.out.println(com.alibaba.fastjson.JSONObject.toJSON(user1).toString());



    }

}
