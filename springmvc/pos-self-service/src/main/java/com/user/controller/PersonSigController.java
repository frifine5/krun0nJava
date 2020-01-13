package com.user.controller;


import com.user.request.PersonSealRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 个人签名图片/个人私章
 * @author WangChengyu
 * 2020/1/13 17:35
 */
@RestController
public class PersonSigController {

    private Logger logger = LoggerFactory.getLogger(PersonSigController.class);

    @RequestMapping(value = "/user/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object login(@RequestBody PersonSealRequest pssRequest) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(pssRequest));






        return null;
    }



}
