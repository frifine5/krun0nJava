package com.cyk.dsp.t.ch2;


/**
 * 适配器模式（Adapter）的定义如下：<br/>
 * 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。适配器模式分为类结构型模式和对象结构型模式两种，前者类之间的耦合度比后者高，且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。
 *
 * 该模式的主要优点如下:
 *
 * <li>客户端通过适配器可以透明地调用目标接口。</li>
 * <li>复用了现存的类，程序员不需要修改原有代码而重用现有的适配者类。</li>
 * <li>将目标类和适配者类解耦，解决了目标类和适配者类接口不一致的问题。</li>
 *
 *
 * 其缺点是：对类适配器来说，更换适配器的实现过程比较复杂。
 *
 *
 * <p>
 *  模式的应用场景
 * 适配器模式（Adapter）通常适用于以下场景。
 *
 *     以前开发的系统存在满足新系统功能需求的类，但其接口同新系统的接口不一致。
 *     使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。
 *
 *  </p>
 *
 * @author WangChengyu
 * 2019/6/27 10:29
 */
public class TestAdapter1 {


    public static void main(String[] args) {
        // 类结构适配器：采用继承，扩展接口的的方式进行适配
        Target adp1 = new ClassAdapter();
        adp1.request();

        // 对象结构适配器：采用组合对象，扩展接口的方式进行适配

        Target adp2 = new ObjAdapter(new Adaptee());
        adp2.request();

    }

}

interface Target {
    void request();
}

//适配者接口
class Adaptee {
    public void specificRequest() {
        System.out.println("适配者中的业务代码被调用！");
    }
}

//类适配器类
class ClassAdapter extends Adaptee implements Target {
    public void request() {
        System.out.println("类适配器调用适配者");
        specificRequest();
    }
}

class ObjAdapter implements Target{

    private Adaptee adaptee;

    public ObjAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    public void request() {
        System.out.println("对象适配器调用适配者");
        adaptee.specificRequest();
    }


}
