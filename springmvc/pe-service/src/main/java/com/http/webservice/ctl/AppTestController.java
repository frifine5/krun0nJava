package com.http.webservice.ctl;


import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class AppTestController {

    @RequestMapping(value = "/app/rev", method = {RequestMethod.GET,RequestMethod.POST})
    public Object revMsg4lApp(HttpServletRequest request){
        String s = "I got you in time: "+new Date();
        String msg = request.getParameter("msg");
        System.out.println("I got msg from app: "+msg);
        return s;
    }


    @RequestMapping(value = "/app/revIp", method = {RequestMethod.GET,RequestMethod.POST})
    public Object revMsg4lApp2(HttpServletRequest request){
        String s = "I got you in time: "+new Date();

        String remoteAddr = request.getRemoteAddr();
        System.out.println(String.format("客户端IP = %s", remoteAddr));


        return s +"\n"+ remoteAddr;
    }





}
