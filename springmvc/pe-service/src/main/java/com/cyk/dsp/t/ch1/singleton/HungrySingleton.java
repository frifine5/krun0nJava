package com.cyk.dsp.t.ch1.singleton;

public class HungrySingleton {


    private static final HungrySingleton singleton = new HungrySingleton();
    private HungrySingleton(){}

    public static synchronized HungrySingleton getInstance(){
        return singleton;

    }


}
