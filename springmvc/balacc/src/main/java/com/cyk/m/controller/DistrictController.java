package com.cyk.m.controller;

import com.common.Result;
import com.cyk.m.ev.District;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DistrictController {
    Logger log = Logger.getLogger(DistrictController.class);



    @RequestMapping("/district")
    public Result Add(HttpServletRequest request){
        String dcode = request.getParameter("dcode");
        String type = request.getParameter("type");

        log.info(String.format("dcode=%s, type=%s", dcode, type));
        List list = new ArrayList();

        switch (type){
            case "1":
                District d1 = new District();
                d1.setdCode("110000");
                d1.setdName("北京市");
                list.add(d1);
                District d0 = new District();
                d0.setdCode("120000");
                d0.setdName("天津市");
                list.add(d0);
                break;
            case "2":
                District d2 = new District();
                d2.setdCode("110100");
                d2.setdName("北京市");
                list.add(d2);
                break;
            case "3":
                District d21 = new District();
                d21.setdCode("110101");
                d21.setdName("东城区");
                list.add(d21);
                District d22 = new District();
                d22.setdCode("110102");
                d22.setdName("西城区");
                list.add(d22);
                break;
        }


        Result result = new Result(0, "执行完毕", list);

        return result;
    }


}


