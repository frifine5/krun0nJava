package com.ca.service;

import com.number.SnowFlakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenNumberService {

    private final static Map<String, SnowFlakeGenerator> numSelector =
//            new HashMap<>(); //基于hashcode的key，线程不安全
//            Collections.synchronizedMap(new HashMap<>());// 线程安全，较慢
                new ConcurrentHashMap<>();// 线程安全

    static{
        numSelector.put("0-0", new SnowFlakeGenerator.Factory().create(0, 0));
    }

    @Value("${number.generator.idc:0}")
    int idcId;

    @Value("${number.generator.machine:0}")
    int machineId;


    /**
     * 未指定数据中心ID和机器ID时获取默认的0-0的发号器| 0-0 作为中心发号器
     * @return
     */
    public long getCenterDefault(){
        return numSelector.get("0-0").nextId();
    }

    /**
     * 指定数据中心ID和机器ID，获取发号器；如果该发号器未初始化，则自动初始化
     * @param idcId  数据中心ID
     * @param machineId 机器ID
     * @return long
     */
    public long getNumber(int idcId, int machineId){
        String key = ""+idcId+"-"+machineId;
        if(!numSelector.containsKey(key)){
            numSelector.put(key, new SnowFlakeGenerator.Factory().create(idcId, machineId));
        }
        return numSelector.get(key).nextId();
    }

    /**
     * 按配置文件中的数据中心ID和机器ID，获取发号器；如果该发号器未初始化，则自动初始化
     * @return long
     */
    public long getNumber(){
        String key = ""+idcId+"-"+machineId;
        if(!numSelector.containsKey(key)){
            numSelector.put(key, new SnowFlakeGenerator.Factory().create(idcId, machineId));
        }
        return numSelector.get(key).nextId();
    }

    /**
     * 生成简单的数字随机数
     */
    public String simpleRandom(int len){
        if(len < 1){
            throw new RuntimeException("Specified length is negative: 指定的随机数数位长度为负数[len="+len+"]");
        }
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sbr.append(((int)(Math.random()*10)));
        }
        return sbr.toString();
    }

}
