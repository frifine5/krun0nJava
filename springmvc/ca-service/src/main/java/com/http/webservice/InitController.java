package com.http.webservice;


import com.common.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class InitController {

    @RequestMapping(value = "/app/isInitial", method = {RequestMethod.GET,RequestMethod.POST})
    public Object checkIsInitFinish(HttpServletRequest request){
        boolean isInital = true;
        // check database；

        // check keyFiles;

        // check snowStack number generation
        String s = "I got you in time: "+new Date();
        String msg = request.getParameter("msg");
        System.out.println("I got msg from app: "+msg);
        return new Result<>(0, "初始化完成", isInital);
    }

    @RequestMapping(value = "/app/doInitial", method = {RequestMethod.GET,RequestMethod.POST})
    public Object doInitial(HttpServletRequest request){
        boolean isInital = true;
        // check keyFiles;

        // init database；

        // check snowStack number generation


        return new Result();
    }








}
