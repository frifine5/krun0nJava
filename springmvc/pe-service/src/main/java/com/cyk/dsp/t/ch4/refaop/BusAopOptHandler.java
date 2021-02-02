package com.cyk.dsp.t.ch4.refaop;

import java.lang.reflect.Method;

public class BusAopOptHandler implements IAopMethod {
    @Override
    public void before() {
        System.out.println("*** before do biz ***");
    }

    @Override
    public void after() {
        System.out.println("*** after do biz ***");
    }

    @Override
    public void after(Method method) {
        System.out.println("*** after do biz *** and biz method name is "
                + (null == method ? null : method.getName()));
    }
}
