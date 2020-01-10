package com.common.globel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 一个小规模的单点缓存全局类
 * @author WangChengyu
 * 2020/1/9 16:25
 */
public class LocalCacheRepo {

    public static Map<String, Map<Object, Long>> cache = new HashMap<>(100);


    /**
     * 长存
     */
    public static void putCache(String key, Object value){
        putCache(key, value, -1);
    }

    /**
     * 指定时效存
     * @param key
     * @param value
     * @param expire
     */
    public static void putCache(String key, Object value, int expire){

        long expireTime = System.currentTimeMillis() + expire * 1000;
        if(expire == -1){
            expireTime = -1;
        }
        Map<Object, Long> theKv = new HashMap<>();
        theKv.put(value, expireTime);
        synchronized (cache){
            cache.put(key, theKv);
        }
    }

    /**
     * 取
     * @param key
     * @return
     */
    public static Object getCache(String key){
        synchronized (cache){
            Map<Object, Long> itValue = cache.get(key);
            if(itValue == null) return null;
            Iterator<Map.Entry<Object, Long>> iterator = itValue.entrySet().iterator();
            if(iterator.hasNext()){
                Map.Entry<Object, Long> next = iterator.next();
                Object value = next.getKey();
                Long timeStamp = next.getValue();
                if(-1 == timeStamp || System.currentTimeMillis()< timeStamp) return value;
                itValue.remove(key); return null;
            }
            return null;
        }
    }


    /**
     * 删除
     */
    public static Object rmCache(String key){
        synchronized (cache){
            Map<Object, Long> itValue = cache.get(key);
            if(itValue == null) return null;
            Iterator<Object> keyIte = itValue.keySet().iterator();
            while(keyIte.hasNext()){
                Object value = keyIte.next();
                cache.remove(key);
                return value;
            }
            return null;
        }
    }


}
