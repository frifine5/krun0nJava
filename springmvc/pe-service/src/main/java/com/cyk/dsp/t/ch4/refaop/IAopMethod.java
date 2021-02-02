package com.cyk.dsp.t.ch4.refaop;

import java.lang.reflect.Method;

public interface IAopMethod {

    void before();

    void after();

    void after(Method method);
}
