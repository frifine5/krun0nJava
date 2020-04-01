package com.http;

import com.asynctask.DemoAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;


@RestController
public class AsyncTaskController {

    Logger logger = LoggerFactory.getLogger(AsyncTaskController.class);

    @Autowired
    DemoAsyncTask asyncTask;


    @RequestMapping(value = "/t/async/taskOne", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object taskOne(HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = asyncTask.doTaskOne();
        while(true) {
            if(task1.isDone()  ) {
                // 任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        String result = "任务全部完成，总耗时：" + (end - start) + "毫秒";
        logger.info(result);
        return result;
    }


    @RequestMapping(value = "/t/async/taskTwo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object taskTwo(HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis();

        asyncTask.doTaskOne();

        Thread.sleep(100);
        long end = System.currentTimeMillis();
        String result = "任务全部完成，总耗时：" + (end - start) + "毫秒";
        logger.info(result);
        return result;
    }


}
