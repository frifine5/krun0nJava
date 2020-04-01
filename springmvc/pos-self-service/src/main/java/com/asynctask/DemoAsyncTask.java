package com.asynctask;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

@Component
public class DemoAsyncTask {


    @Async
    public Future<String> doTaskOne() throws Exception {
        System.out.println("Async, taskOne, Start...");
        long start = System.currentTimeMillis();
        Thread.sleep(new Random().nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("Async, taskOne, End, 耗时: " + (end - start) + "毫秒");
        return new AsyncResult<>("AsyncTaskOne Finished");
    }



}
