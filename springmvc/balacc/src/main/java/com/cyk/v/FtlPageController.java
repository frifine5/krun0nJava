package com.cyk.v;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FtlPageController {
    Logger log = Logger.getLogger(FtlPageController.class);
    @RequestMapping("/")
    public String getPage(){
        return "index";
    }

    @RequestMapping("/chgr/{uri}")
    public String getPage(@PathVariable("uri") String uri){
        log.info(uri);
        return "/c/"+uri;
    }

    @RequestMapping("/doLogin")
    public ModelAndView Add(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        if(!"admin".equals(account)|| !"pwd123".equals(pwd)){
            mv.addObject("message","登录失败");
            return mv;
        }
        mv.addObject("user","管理员");
        mv.addObject("message","登录成功");
        return mv;
    }


}
