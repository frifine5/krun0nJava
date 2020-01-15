package com.user.controller;


import com.user.entity.PsSigImg;
import com.user.request.PersonSealRequest;
import com.user.service.PersonImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人签名图片/个人私章
 * @author WangChengyu
 * 2020/1/13 17:35
 */
@RestController
public class PersonSigController {

    private Logger logger = LoggerFactory.getLogger(PersonSigController.class);

    @Autowired
    PersonImgService personImgService;


    @RequestMapping(value = "/ps/genPsSeal", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object genPsSeal(@RequestBody PersonSealRequest pssRequest) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(pssRequest));
        Map<String, Object> result = new HashMap<>();
        try{
            String imgData = personImgService.getPersonSealImage(pssRequest);
            result.put("code", 0);
            result.put("msg", "制作名章成功");
            result.put("sealImgData", imgData);
        }catch (Exception e){
            logger.error("制作名章失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/ps/upPsSigs", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object upPsSigs(@RequestBody PersonSealRequest pssRequest) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(pssRequest));
        Map<String, Object> result = new HashMap<>();
        try{
            personImgService.pushPsSigImg(pssRequest);
            result.put("code", 0);
            result.put("msg", "上传手写签名成功");
        }catch (Exception e){
            logger.error("上传手写签名保存失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/ps/clearPsSig", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object clrSpecificPsSig(@RequestBody PersonSealRequest pssRequest) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(pssRequest));
        Map<String, Object> result = new HashMap<>();
        try{
            personImgService.clrCfPsSigImg(pssRequest);
            result.put("code", 0);
            result.put("msg", "清除指定手写签名成功");
        }catch (Exception e){
            logger.error("清除指定手写签名保存失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/ps/getPsSigs", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getPsSig(@RequestBody PersonSealRequest pssRequest) {
        logger.info("请求参数: {}", com.alibaba.fastjson.JSONObject.toJSON(pssRequest));
        Map<String, Object> result = new HashMap<>();
        try{
            PsSigImg data = personImgService.getPersonSigs(pssRequest.getAccount());
            if(data == null){
                logger.info("个人章查询到的结果为空");
                data = new PsSigImg();
                data.setAccount(pssRequest.getAccount());
            }
            result.put("code", 0);
            result.put("msg", "获取成功");
            result.put("data", data);
        }catch (Exception e){
            logger.error("个人章查询失败", e);
            result.put("code", -10);
            result.put("msg", e.getMessage());
        }
        return result;
    }


}
