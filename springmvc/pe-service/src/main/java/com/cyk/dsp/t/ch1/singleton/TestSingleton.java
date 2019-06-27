package com.cyk.dsp.t.ch1.singleton;

import javax.swing.*;
import java.awt.*;

public class TestSingleton {


    public static void main(String[] args) {


//        // test1 lazySingleton
//         test1();

        // test2 hungrySingleton

        test2();

    }


    private static void test2() {

        JFrame jf = new JFrame("饿汉单例模式测试");
        jf.setLayout(new GridLayout(1, 2));
        Container contentPane = jf.getContentPane();
        CyK obj1 = CyK.getInstance();
        contentPane.add(obj1);
        CyK obj2 = CyK.getInstance();
        contentPane.add(obj2);
        if (obj1 == obj2) {
            System.out.println("他们是同一人！");
        } else {
            System.out.println("他们不是同一人！");
        }
        jf.pack();
        jf.setLocation(200, 200);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    private static void test1(){
        President pt1 = President.getInstance();
        pt1.getName();
        President pt2 = President.getInstance();
        pt2.getName();

    }

}


/**
 * 饿汉式单例 我
 */
class CyK extends JPanel {

    private static CyK instance = new CyK();

    private CyK() {

        JLabel l1 = new JLabel(new ImageIcon("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\cyk蛮小满.jpg"));
        this.add(l1);
    }

    public static CyK getInstance() {
        return instance;
    }


}


/**
 * 懒汉式单例总统类
 */
class President {
    private static volatile President instance = null;    //保证instance在所有线程中同步

    //private避免类在外部被实例化
    private President() {
        System.out.println("产生一个总统！");
    }

    public static synchronized President getInstance() {
        //在getInstance方法上加同步
        if (instance == null) {
            instance = new President();
        } else {
            System.out.println("已经有一个总统，不能产生新总统！");
        }
        return instance;
    }

    public void getName() {
        System.out.println("我是美国总统：特朗普。");
    }
}
