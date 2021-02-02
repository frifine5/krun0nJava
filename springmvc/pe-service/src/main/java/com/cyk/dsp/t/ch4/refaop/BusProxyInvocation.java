package com.cyk.dsp.t.ch4.refaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BusProxyInvocation implements InvocationHandler {


    /** 操作者 */
    Object proxy;

    /** 被处理的对象 */
    Object bizObj;


    /**
     * 动态生成方法被处理过后的对象 (写法固定)
     *
     * @param delegate
     * @param proxy
     * @return
     */
    public Object bind(Object delegate, Object proxy) {

        this.proxy = proxy;
        this.bizObj = delegate;
        return Proxy.newProxyInstance(
                this.bizObj.getClass().getClassLoader(),
                this.bizObj.getClass().getInterfaces(),
                this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try{

            //反射得到操作者的实例
            Class clazz = this.proxy.getClass();
            //反射得到操作者的before方法
            Method before = clazz.getDeclaredMethod("before");
            //反射执行before方法
            before.invoke(this.proxy);
            //执行要处理对象的原本方法
            result = method.invoke(this.bizObj, args);
            //反射得到操作者的after方法
            Method after = clazz.getDeclaredMethod("after");
            //反射执行after方法
            after.invoke(this.proxy);

            //反射得到操作者的after(method)方法
            Method afterMethod = clazz.getDeclaredMethod("after", new Class[] {Method.class});
            //反射执行after(method)方法
            afterMethod.invoke(this.proxy, new Object[]{method});

        }catch (Exception e){e.printStackTrace();}

        System.out.println("\n方法返回的结果");
        System.out.println(result);
        return result;
    }


}
