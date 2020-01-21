package com.user;

import com.common.globel.LocalCacheRepo;
import org.junit.Test;

public class CacheTest {



    @Test
    public void testCache(){

        for(int i= 0; i<5 ; i++){
            String key = "key-" + i;
            int t = (int)(Math.random()*20);
            String value = "皎洁日月吞天噬地功夫蟒";
            LocalCacheRepo.putCache(key, value.substring(0, t%10), t);

        }

        for (int j = 1; j <=10; j++) {// 每个1秒取一次值
            try{
                Thread.sleep(999);
                for(int i= 0; i<5 ; i++) {
                    String key = "key-" + i;
                    Object value = LocalCacheRepo.getCache(key);
                    System.out.printf("第%d秒：key=%s, value=%s\n", j, key, value);
                }
                System.out.println("---------------------");
            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }


    @Test
    public void testDupKey1()throws Exception{

        String key = "key1";
        LocalCacheRepo.putCache(key, "1");
        System.out.println(LocalCacheRepo.getCache(key));
        Thread.sleep(1000);

        LocalCacheRepo.putCache(key, System.currentTimeMillis());
        System.out.println(LocalCacheRepo.getCache(key));
        Thread.sleep(1000);

        LocalCacheRepo.putCache(key, "测试三秒",  3);

        System.out.println(LocalCacheRepo.getCache(key));
        Thread.sleep(3100);

        System.out.println("3秒后");
        System.out.println(LocalCacheRepo.getCache(key));





    }

}
