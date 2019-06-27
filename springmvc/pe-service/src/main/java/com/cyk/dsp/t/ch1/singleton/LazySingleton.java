package com.cyk.dsp.t.ch1.singleton;

public class LazySingleton {


    private static volatile LazySingleton singleton = null;

    private LazySingleton(){ }

    public static synchronized LazySingleton getInstance(){
        if (null==singleton) singleton = new LazySingleton();
        return singleton;
    }


    public static void main(String[] args) {

        LazySingleton instance1 = LazySingleton.getInstance();
        LazySingleton instance2 = LazySingleton.getInstance();

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance2 == instance1);


    }


}
