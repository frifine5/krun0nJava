package com.t;

import com.t.dao.IOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    IOrderDao iOrderDao;


    @RequestMapping("/test/getDb")
    public Object test(String date){
        if(null == date || "".equals(date)) return "please input parameters";
        List list = iOrderDao.findName(date);
        return list;
    }

}
