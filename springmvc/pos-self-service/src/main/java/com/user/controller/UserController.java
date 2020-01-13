package com.user.controller;


import com.user.entity.AUser;
import com.user.entity.PUser;
import com.user.request.UserRequest;
import com.user.service.AdmUserService;
import com.user.service.PersonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 用户管理 登录
 * @author WangChengyu
 * 2020/1/10 12:00
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    PersonUserService personUserService;

    @Autowired
    AdmUserService admUserService;

    @RequestMapping(value = "/a/user/login", method = {RequestMethod.POST , RequestMethod.GET})
    @ResponseBody
    public Object login(@RequestBody UserRequest userParam){

        System.out.println("请求参数:");
        System.out.println(com.alibaba.fastjson.JSONObject.toJSON(userParam));

        PUser user = new PUser();
        user.setName("测试用户");
        user.setAccount(UUID.randomUUID().toString());
        user.setPwd("111222");
        user.setCode("110101199901011234");
        user.setCrtTime(new Date());


        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "ok");
        result.put("data", user);
        return result;
    }

    @RequestMapping(value = "/a/user/reg", method = {RequestMethod.POST , RequestMethod.GET})
    @ResponseBody
    public Object regUser(@RequestBody UserRequest userParam){
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));
        Map<String, Object> result = new HashMap<>();
        try{
            if(userParam.getType() == 0){// 用户注册
                personUserService.addPsUser(userParam);
            }else{// 管理员注册
                admUserService.addAdmUser(userParam);
            }
            result.put("code", 0);
            result.put("msg", "注册成功");
        }catch (Exception e){
            logger.error("用户注册失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/a/user/list", method = {RequestMethod.POST , RequestMethod.GET})
    @ResponseBody
    public Object listUsers(@RequestBody UserRequest userParam){
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Object> data = new HashMap<>();
            if(userParam.getType() == 0){// 用户列表
                List<PUser> psUsers = null;
                if(userParam.isPaging()){
                    psUsers = personUserService.getPUserListOnPage(userParam);
                    data.put("total", personUserService.getPsTotal());
                    data.put("pageNo", userParam.getPageNo());
                    data.put("pageSize", userParam.getPageSize());
                }else{
                    psUsers = personUserService.getPUserList(userParam);
                    data.put("total", null==psUsers? 0: psUsers.size());
                }
                data.put("list", psUsers);
            }else{// 管理员注册
                List<AUser> admUsers = null;
                if(userParam.isPaging()){
                    admUsers = admUserService.getAUserListOnPage(userParam);
                    data.put("total", admUserService.getAdmTotal());
                    data.put("pageNo", userParam.getPageNo());
                    data.put("pageSize", userParam.getPageSize());
                }else{
                    admUsers = admUserService.getAUserList(userParam);
                    data.put("total", null==admUsers? 0: admUsers.size());
                }
                data.put("list", admUsers);
            }
            result.put("code", 0);
            result.put("msg", "查询完毕");
            result.put("data", data);
        }catch (Exception e){
            logger.error("用户列表查询失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/a/user/mod", method = {RequestMethod.POST , RequestMethod.GET})
    @ResponseBody
    public Object modUser(@RequestBody UserRequest userParam){
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));
        Map<String, Object> result = new HashMap<>();
        try{
            if(userParam.getType() == 0){// 普通用户修改
                personUserService.modifyPsUser(userParam);
            }else{// 管理员用户修改
                admUserService.modifyAdmUser(userParam);
            }
            result.put("code", 0);
            result.put("msg", "修改成功");
        }catch (Exception e){
            logger.error("用户修改失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/a/user/del", method = {RequestMethod.POST , RequestMethod.GET})
    @ResponseBody
    public Object delUser(@RequestBody UserRequest userParam){
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));
        Map<String, Object> result = new HashMap<>();
        try{
            if(userParam.getType() == 0){// 普通用户修改
                personUserService.delPsUser(userParam);
            }else{// 管理员用户修改
                admUserService.delAdmUser(userParam);
            }
            result.put("code", 0);
            result.put("msg", "删除成功");
        }catch (Exception e){
            logger.error("用户删除失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }











}
