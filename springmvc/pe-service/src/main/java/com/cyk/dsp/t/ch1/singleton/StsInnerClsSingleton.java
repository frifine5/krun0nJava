package com.cyk.dsp.t.ch1.singleton;


/**
 * 静态内部类实现：
 */
public class StsInnerClsSingleton {


    private StsInnerClsSingleton(){
    }

    public static StsInnerClsSingleton getInstance(){
        return InnerClz.instance;
    }


    public static class InnerClz{
        private static final StsInnerClsSingleton instance = new StsInnerClsSingleton();
    }


    public static void main(String[] args) {




    }

}
