package com.cyk.dsp.t.ch1.singleton;

import java.lang.reflect.Constructor;

public enum EnumSingleton {


    INSTANCE;

    public EnumSingleton getInstance(){
        return INSTANCE;
    }



}


class InnerTest{


    public static void main(String[] args) throws Exception {

        test_reflect1();


    }

    public static void test_reflect1() throws Exception {
//        Constructor<EnumSingleton> desMan1 = EnumSingleton.class.getDeclaredConstructor(null); // 无空参构造

        EnumSingleton obj1 = EnumSingleton.INSTANCE;

        // Exception in thread "main" java.lang.NoSuchMethodException: com.cyk.dsp.t.ch1.singleton.EnumSingleton.<init>(java.lang.String, java.lang.Integer)
//        Constructor<EnumSingleton> desMan1 = EnumSingleton.class.getDeclaredConstructor(String.class,  Integer.class); // 有参构造,但不要用Integer.class



        // Exception in thread "main" java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        //	at java.lang.reflect.Constructor.newInstance(Constructor.java:417)
        Constructor<EnumSingleton> desMan1 = EnumSingleton.class.getDeclaredConstructor(String.class,  int.class ); // 有参构造
        desMan1.setAccessible(true);

        EnumSingleton obj2 = desMan1.newInstance();

        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj1 == obj2);


    }


    public void test1(){
        EnumSingleton i1 = EnumSingleton.INSTANCE;
        EnumSingleton i2 = EnumSingleton.INSTANCE;

        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i1 == i2);
    }

}