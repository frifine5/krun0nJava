package com.cyk.m.controller;

import com.common.Result;
import com.cyk.m.ev.Seal;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class SealController {
    Logger log = Logger.getLogger(SealController.class);

    @RequestMapping("/seal/querySealList")
    public @ResponseBody Object getFirstPage(HttpServletRequest request ) {

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String unit = request.getParameter("unit");
        log.info(String.format("code=%s, name=%s, unit=%s\n", code, name, unit));

        // seal demo
        Seal seal = new Seal();
        seal.setCode("10215048");
        seal.setName("GM测试章");
        seal.setType("0");
        seal.setUnit("xx单位");
        seal.setLp("法定代表人");
        seal.setOptr("经办人");
        seal.setSt(new Date());
        seal.setPic("图片?");
        List<Seal> sealList = new ArrayList<>();
        sealList.add(seal);
        sealList.add(seal);
        sealList.add(seal);

        Result result = new Result(0,"执行完成", sealList, 3, 1, 10);

        return result;

    }


}


