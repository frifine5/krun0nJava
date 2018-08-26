package com.cyk.v;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController {
    Logger log = Logger.getLogger(TestController.class);

    @RequestMapping("/test")
    public ModelAndView Add(HttpServletRequest request, HttpServletResponse response){
        String test = request.getParameter("test");
        log.info("test="+test);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("test");
        if("test".equals(test)){
            mv.addObject("user","测试者");
            mv.addObject("money",100);
        }else{
            mv.addObject("user","游客");
            mv.addObject("money",10);
        }
        return mv;
    }


}
