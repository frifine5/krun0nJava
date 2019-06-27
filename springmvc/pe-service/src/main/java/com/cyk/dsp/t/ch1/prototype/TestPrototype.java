package com.cyk.dsp.t.ch1.prototype;

import javax.swing.*;
import java.awt.*;

public class TestPrototype {

    public static void main(String[] args) {
        JFrame jf = new JFrame("原型模式测试");
        jf.setLayout(new GridLayout(1, 2));
        Container contentPane = jf.getContentPane();
        Cykong obj1 = new Cykong();
        contentPane.add(obj1);
        Cykong obj2 = (Cykong) obj1.clone();
        contentPane.add(obj2);

        int c = 2;

        for (int i = 0; i < c; i++) {
            Cykong obj = (Cykong) obj1.clone();
            contentPane.add(obj);
        }

        jf.pack();
        jf.setLocation(200, 200);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}


/**
 * 原型模式 java实现cloneable接口
 */
class Cykong extends JPanel implements Cloneable {
    private static final long serialVersionUID = 5543049531872119328L;

    public Cykong() {
        JLabel l1 = new JLabel(new ImageIcon("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\Wukong.jpg"));
        this.add(l1);
    }

    public Object clone() {
        Cykong w = null;
        try {
            w = (Cykong) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("拷贝悟空失败!");
        }
        return w;
    }
}
