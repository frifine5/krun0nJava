package com.cyk.m.controller;

import com.common.ParamsUtil;
import com.common.Result;
import com.cyk.m.ev.Seal;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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


    @RequestMapping("/seal")
    public ModelAndView Add(HttpServletRequest request){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String unit = request.getParameter("unit");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        pageNoStr = ParamsUtil.checkNull(pageNoStr) ? "1":pageNoStr;
        pageSizeStr = ParamsUtil.checkNull(pageSizeStr) ? "10":pageSizeStr;
        int pageNo = Integer.parseInt(pageNoStr);
        int pageSize = Integer.parseInt(pageSizeStr);

        log.info(String.format("code=%s, name=%s, unit=%s", code, name, unit));
        log.info(String.format("pageNo=%s, pageSize=%s", pageNo, pageSize));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("c/seal");

        if("em".equals(name)){
            mv.addObject("total", 0);
            return mv;
        }
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
        for (int i = 0; i < 10; i++) {
            sealList.add(seal);
        }



        mv.addObject("code", 0);
        mv.addObject("message", "执行完毕");
        mv.addObject("list", sealList);
        mv.addObject("total", 51);
        mv.addObject("pageNo", pageNo);
        mv.addObject("pageSize", pageSize);

        return mv;
    }


}


