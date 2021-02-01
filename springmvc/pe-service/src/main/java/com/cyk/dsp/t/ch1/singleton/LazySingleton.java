package com.cyk.dsp.t.ch1.singleton;


/**
 * 懒汉模式：初始使用时，不立即分配空间，而在第一次调用时。但调用时需要加锁来保持唯一性；并用volatile来防止指令重排。
 */
public class LazySingleton {


    private static volatile LazySingleton singleton = null;

    private LazySingleton(){ }

    public static synchronized LazySingleton getInstance(){
        if (null==singleton) singleton = new LazySingleton();
        return singleton;
    }

    /**
     * 双锁机制下：无volatile会因为指令重排的可能，而拿到一个已申明，但未初始化的对象。
     * @param args
     */
/*
    public static synchronized LazySingleton getInstance(){
        if (null==singleton){
            synchronized (LazySingleton.class){
                if(null == singleton){
                    singleton = new LazySingleton();
                }
            }
        }
        return singleton;
    }
*/

    public static void main(String[] args) {

        LazySingleton instance1 = LazySingleton.getInstance();
        LazySingleton instance2 = LazySingleton.getInstance();

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance2 == instance1);


    }


}
