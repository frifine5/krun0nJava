package com.cyk.dsp.jswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PanelTest1 extends JPanel {
/*
https://blog.csdn.net/u011393661/article/details/12709189
 */

    public static void main(String[] args) {
        JPanel jPanel = new PanelTest1();
        JFrame jFrame = new JFrame("图片拖到测试");
//        jFrame.setLocation(200, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(jPanel);
        jFrame.setSize(frame_width, frame_height);
        jFrame.setVisible(true);
    }


    static int frame_width = 600;
    static int frame_height = 300;

    PicPanel pic = null;
    // 图片位置
    private int pic_x, pic_y;

    // 前一个位置
    int begin_x, begin_y;

    boolean inThePic = false;

    public PanelTest1(){
        pic = new PicPanel("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\Wukong.jpg");
        setLayout(null);
        add(pic);

        // 定位
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);
        pic.setLocation(pic_x, pic_y);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 鼠标左击按下事件
                // 检测落点在图片上
                if(inPicBounds(e.getX(), e.getY())){
                    //记录当前坐标
                    begin_x = e.getX();
                    begin_y = e.getY();
                    inThePic = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放事件
                inThePic = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(inThePic && checkPoint(e.getX(),e.getY())){
                    //边界 检查
                    pic_x =pic_x - (begin_x - e.getX());
                    pic_y =pic_y - (begin_y - e.getY());
//                System.out.println("pic_x=" + pic_x);
                    begin_x = e.getX();
                    begin_y = e.getY();
                    pic.setLocation(pic_x, pic_y);
                }
            }
        });




    }

    // 检测点击落点在图片上
    private boolean inPicBounds(int px, int py){
        if(px >= pic_x && px <= pic_x + pic.getWidth()
                && py >= pic_y && py <= pic_y + pic.getHeight() ){
            System.out.println("落在区域内");
            return  true;
        }else{
            System.out.println("落在区域外");
            return false;
        }
    }

    // 越界 检查
    private boolean checkPoint(int px, int py){
        if(px < 0|| py <0){
            return false;
        }else if(px >getWidth() || py > getHeight()){
            return false;
        }else{
            return true;
        }

    }




}

class PicPanel extends JPanel {

    int p_width = 0;
    int p_height = 0;

    Image im = null;
    int i = 0;

    public PicPanel(String picName){
        ImageIcon imageIcon = new ImageIcon(picName);
        im = imageIcon.getImage();
        p_width = imageIcon.getIconWidth();
        p_height = imageIcon.getIconWidth();
        setBounds(0, 0, p_width, p_height);
    }

    public void paint(Graphics g){
        g.drawImage(im, 0, 0, p_width, p_height, null);
    }

}