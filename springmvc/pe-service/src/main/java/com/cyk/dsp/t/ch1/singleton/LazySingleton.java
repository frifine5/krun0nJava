package com.cyk.dsp.t.ch1.singleton;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 懒汉模式：初始使用时，不立即分配空间，而在第一次调用时。但调用时需要加锁来保持唯一性；并用volatile来防止指令重排。
 */
public class LazySingleton {


    private static volatile LazySingleton singleton = null;
    private static boolean chk = false; // 关键字标志位

    private LazySingleton(){
        synchronized (LazySingleton.class){
            if(!chk){
                chk = true;
            }else{
                throw new RuntimeException("不要试图使用反射进行破解单例模式");
            }
        }

    }

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

    public static void main(String[] args)  throws Exception{
//        test1();

//        test2_reflect1();
        test2_reflect2();
    }


    public static void test2_reflect2() throws Exception{
        Constructor<LazySingleton> destoryMan1 = LazySingleton.class.getDeclaredConstructor(null);
        destoryMan1.setAccessible(true);

        LazySingleton obj1 = destoryMan1.newInstance(null);
        // -------------
        /**
         * 此类方式，依旧可以使用反射找到关键字标识位，改变标识位来继续破解
         */
        Field desChkField = LazySingleton.class.getDeclaredField("chk");
        desChkField.set(obj1, false);

        LazySingleton obj2 = destoryMan1.newInstance(null);

        System.out.println(obj1);
        System.out.println(obj2);

        System.out.println(obj1 == obj2);
        System.out.println( "反射破解结果：" + ( obj1 == obj2 ? "未破 - 单例懒汉模式": "破解 - 单例懒汉模式") );





    }

    /**
     * 用反射来破解1
     */
    public static void test2_reflect1() throws Exception{
//        LazySingleton obj1 = LazySingleton.getInstance();

        Constructor<LazySingleton> destoryMan1 = LazySingleton.class.getDeclaredConstructor(null);
        destoryMan1.setAccessible(true);

        LazySingleton obj1 = destoryMan1.newInstance(null);
        LazySingleton obj2 = destoryMan1.newInstance(null);

        System.out.println(obj1);
        System.out.println(obj2);

        System.out.println(obj1 == obj2);
        System.out.println( "反射破解结果：" + ( obj1 == obj2 ? "未破 - 单例懒汉模式": "破解 - 单例懒汉模式") );


        // -------------
        /**
         * 此类方式，依旧可以使用反射找到关键字标识位，改变标识位来继续破解
         * 看2
         */

    }


    /**
     * 一般人的测试
     */
    public static void test1(){

        LazySingleton instance1 = LazySingleton.getInstance();
        LazySingleton instance2 = LazySingleton.getInstance();

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance2 == instance1);

    }


}
