package com.http.webservice;


import com.ca.service.InitService;
import com.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;



@RestController
public class InitController {

    Logger log = LoggerFactory.getLogger(InitController.class);

    @Autowired
    InitService initService;


    @RequestMapping(value = "/app/doInitial", method = {RequestMethod.GET,RequestMethod.POST})
    public Object doInitial(HttpServletRequest request){
        boolean initStepRt;
        try{
            // check database；
            initStepRt = initService.initDbSchema();
            if(!initStepRt){
                return new Result<>(-101, "数据库初始化失败", null);
            }
            // check keyFiles;
            initStepRt = initService.isKeyStore();
            if(!initStepRt){
                return new Result<>(-102, "初始化未完成", "系统密钥未初始化");
            }else{
                // key 校验性检查 -- 初始化完成判定
                initStepRt = initService.checkSmkMatch();
                if(!initStepRt){
                    return new Result<>(-103, "初始化未完成", "系统密钥校验失败，请求联系密管中心KMC");
                }
                return new Result<>(0, "初始化完成", null);
            }

        }catch (Exception e){
            log.error("初始化未完成", e);
            return new Result<>(-109, "初始化未完成", e.getMessage());
        }
    }









}
