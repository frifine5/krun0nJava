package com.t;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestFtlController {

    @RequestMapping("/test")
    public Object getFirstPage() {

        ModelAndView result = new ModelAndView("test.ftl");

        System.out.println("in ftl test -------- ");

        Animal a1 = new Animal();
        a1.setName("小狗");
        a1.setPrice(88);
        Animal a2 = new Animal();
        a2.setName("小喵");
        a2.setPrice(80);

        List<Animal> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);

        Map<String,Object> sexMap=new HashMap<>();
        sexMap.put("1", "男");
        sexMap.put("0","女");

        Map<String,Object> map = new HashMap<>();
        map.put("user", "冉冉");
        map.put("score", 13);
        map.put("team", "一班,二班");
        map.put("animals", list);
        map.put("sexMap",sexMap);

        result.addAllObjects(map);

        return map;
//        return result;
    }


    @RequestMapping("/")
    public String getPage(){
        return "index";
    }

    @RequestMapping("/page/{uri}")
    @ResponseBody
    public Object getPage(@PathVariable("uri") String uri){
        System.out.println(uri);
        ModelAndView mav = new ModelAndView(uri);
        return mav;
    }

}


