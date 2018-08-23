package com.cyk.controller;

import com.cyk.ev.Seal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SealController {

    @RequestMapping("/seal/querySealList")
    public Object getFirstPage(HttpServletRequest request) {

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String unit = request.getParameter("unit");
        System.out.printf("code=%s, name=%s, unit=%s\n", code, name, unit);

        ModelAndView mav = new ModelAndView();

        // seal demo
        Seal seal = new Seal();
        seal.setCode("10215048");
        seal.setName("GM测试章");
        seal.setUnit("xx单位");
        seal.setLp("法定代表人");
        seal.setOptr("经办人");
        seal.setSt(new Date().toString());
        seal.setPic("图片?");
        List<Seal> sealList = new ArrayList<>();
        sealList.add(seal);
        seal.setCode("10215042");
        seal.setName("o测试章");
        sealList.add(seal);

        Map<String,Object> data = new HashMap<>();
        data.put("sealList", sealList);

        mav.addAllObjects(data);

        Map<String,Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("message", "success");

        return map;
    }


}


