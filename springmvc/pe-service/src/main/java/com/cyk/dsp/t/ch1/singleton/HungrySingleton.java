package com.cyk.dsp.t.ch1.singleton;


/**
 * 饿汉模式：无论是否使用实例，在第一次被引用构建时，即将所需的内存分配给它
 */
public class HungrySingleton {


    private static final HungrySingleton singleton = new HungrySingleton();
    private HungrySingleton(){}

    public static synchronized HungrySingleton getInstance(){
        return singleton;

    }


}
