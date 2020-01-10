package com.login;


import com.common.ParamsUtil;
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

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/user/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object login(@RequestBody UserRequest userParam) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));

        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, String> data;
            String token = ParamsUtil.getUUIDStr();
            if (userParam.getType() == 0) {// 普通用户修改
                data = loginService.lgiPsUser(userParam, token);
            } else {// 管理员用户修改
                data = loginService.lgiAdmUser(userParam, token);
            }
            result.put("code", 0);
            result.put("msg", "登录成功");
            result.put("data", data);
        } catch (Exception e) {
            logger.error("用户登录失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/user/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object logout(@RequestBody UserRequest userParam) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(userParam));
        Map<String, Object> result = new HashMap<>();
        try {
            loginService.logoutUser(userParam);
            result.put("code", 0);
            result.put("msg", "登出成功");
        } catch (Exception e) {
            logger.error("用户登出失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }


}
