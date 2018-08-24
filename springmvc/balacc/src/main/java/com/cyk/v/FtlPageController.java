package com.cyk.v;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FtlPageController {
    Logger log = Logger.getLogger(FtlPageController.class);
    @RequestMapping("/")
    public String getPage(){
        return "index";
    }

    @RequestMapping("/page/{uri}")
    public String getPage(@PathVariable("uri") String uri){
        log.info(uri);
        return "/c/"+uri;
    }

}
