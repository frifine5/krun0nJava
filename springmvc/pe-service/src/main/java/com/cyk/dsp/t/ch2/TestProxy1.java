package com.cyk.dsp.t.ch2;

import javax.swing.*;
import java.awt.*;

public class TestProxy1 {

    public static void main(String[] args) {
        Specialty obj = new SgProxy();
        obj.display();
    }


}


// D:\0nRun\springmvc\pe-service\src\main\resources\pic

interface Specialty{

    void display();

}

//真实主题：婺源特产
class WySpecialty extends JFrame implements Specialty
{
    private static final long serialVersionUID=1L;
    public WySpecialty()
    {
        super("韶关代理婺源特产测试");
        this.setLayout(new GridLayout(1,1));
        JLabel l1=new JLabel(new ImageIcon("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WuyuanSpecialty.jpg"));
        this.add(l1);
        this.pack();
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void display()
    {
        this.setVisible(true);
    }
}
//代理：韶关代理
class SgProxy implements Specialty
{
    private WySpecialty realSubject=new WySpecialty();
    public void display()
    {
        preRequest();
        realSubject.display();
        postRequest();
    }
    public void preRequest()
    {
        System.out.println("韶关代理婺源特产开始。");
    }
    public void postRequest()
    {
        System.out.println("韶关代理婺源特产结束。");
    }
}
