package com.cyk.m.controller;

import com.cyk.common.Result;
import com.cyk.m.ev.District;
import com.cyk.m.service.DistrictService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class DistrictController {
    Logger log = Logger.getLogger(DistrictController.class);

    @Autowired
    DistrictService districtService;

    @RequestMapping("/district")
    public Result Add(HttpServletRequest request){
        String dcode = request.getParameter("dcode");
        String type = request.getParameter("type");

        log.info(String.format("dcode=%s, type=%s", dcode, type));
        List<District> list = districtService.getListByType(dcode, type);
        Result result;
        if(null == list || list.size()<=0 ){
            result = new Result(0, "执行完毕", null);
        }else{
            result = new Result(0, "执行完毕", list);
        }
        return result;
    }


}


