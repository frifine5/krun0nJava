package com.image;

import com.common.PsaImageUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestGenGcirImg1 {


    @Test
    public void testRotateText() {
        long st = System.currentTimeMillis();
        int fontSize = 68;
        int r = 496;
        int broad = 10;
        String sealName = "测试机构使用章";
        sealName = "深圳市福田区梅林街道国微汽车配件店";
        String type = "测试专用章";

        char[] texts = sealName.toCharArray();
        int len = texts.length;
        int totalDegree = len > 19? 240: len>15? 180: len>11? 120: 120;

        int eachDegree = (int) (totalDegree / len);
        int start = -totalDegree / 2 + (len%2>0 ? eachDegree: eachDegree/2) ;
        System.out.println("每个字占的角度数：" + eachDegree);

        BufferedImage bi = drawCircle(r, broad);

        for (int i = 0; i < len; i++) {
            BufferedImage step = drawText2(r, broad, fontSize, texts[i], start + eachDegree * i);
            bi = PsaImageUtil.mergeTogether(step, bi, true);
        }

        bi = PsaImageUtil.resizeImage(bi, 496, 496);
        BufferedImage inBi = drawInnerImage(r, broad);
        BufferedImage typeBi = drawTxtAndRz(broad, type, fontSize, 0.6f, 0, 1);
        typeBi = PsaImageUtil.scaleRect(typeBi, .8f);

        bi = PsaImageUtil.mergeTogether(inBi, bi, true);
        bi = PsaImageUtil.mergeTogether(typeBi, bi, 0, -(int) (r * .35));


        String destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());
        try {
            ImageIO.write(bi, "PNG", new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        bi = PsaImageUtil.resizeImage(bi, 1.5f);
//        destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());
//        try {
//            ImageIO.write(bi, "PNG", new File(destFile));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        System.out.println("耗时：" + (System.currentTimeMillis() - st));

    }


    public BufferedImage drawText(int canvasLen, int broad, int fontSize, char theTxt, int degree) {

        int fontThickness = 5;

        int canvasWidth = canvasLen;
        int canvasHeight = canvasLen;

        float cetX = canvasWidth / 2;
        float cetY = canvasHeight / 2;

        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        /***********draw circle*************/
        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - broad;
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        Shape circle = new Arc2D.Double(broad, broad, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);
        g2d.draw(circle);

//        Shape cpoint = new Arc2D.Double(cetX, cetY, 5,  5, 0, 360, Arc2D.OPEN);
//        g2d.draw(cpoint);

        g2d.setStroke(new BasicStroke(fontThickness));


        // 写字
        Font typeFont = new Font("宋体", Font.BOLD, fontSize);

        String txt = theTxt + "";
        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds(txt, mdCtx);
        g2d.setFont(typeFont);
        g2d.drawString(txt, (float) (cetX - mdBound.getWidth() / 2), (float) (mdBound.getHeight() + broad));

        g2d.dispose();//销毁资源

//        System.out.printf("宽高：%s, %s\n", bi.getWidth(), bi.getHeight());

        bi = PsaImageUtil.rotateImg(bi, degree);
//                scaleWidth(bi, .7f);

        return bi;
    }


    @Test
    public void testRzTxt() {
        long st = System.currentTimeMillis();
        int fontSize = 68;
        int canvasLen = 496;
        int broad = 10;
        String theTxt = "测";

        int fontThickness = 5;

        int canvasWidth = canvasLen;
        int canvasHeight = canvasLen;

        float cetX = canvasWidth / 2;
        float cetY = canvasHeight / 2;

        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        /***********draw circle*************/
        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - broad;
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        Shape circle = new Arc2D.Double(broad, broad, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);
        g2d.draw(circle);

        g2d.setStroke(new BasicStroke(2));//设置画笔的粗度
        Shape cpoint = new Arc2D.Double(cetX - 1, cetY - 1, 2, 2, 0, 360, Arc2D.OPEN);
        g2d.draw(cpoint);

        g2d.setStroke(new BasicStroke(fontThickness));


        // 写字
        Font txtFont = new Font("宋体", Font.BOLD, fontSize);

        String txt = theTxt + "";
        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = txtFont.getStringBounds(txt, mdCtx);


        g2d.setFont(txtFont);
        g2d.drawString(txt, (float) (cetX - mdBound.getWidth() / 2), (float) (mdBound.getHeight() + broad));


        g2d.dispose();//销毁资源

//        System.out.printf("宽高：%s, %s\n", bi.getWidth(), bi.getHeight());
//
//        bi = PsaImageUtil.rotateImg(bi, 0);
////                scaleWidth(bi, .7f);
        String destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());

        bi = PsaImageUtil.resizeImage(bi, 496, 496);
        try {
            ImageIO.write(bi, "PNG", new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - st));

    }


    @Test
    public void testMergeCaText() {
        long st = System.currentTimeMillis();
        int fontSize = 68;
        int canvasLen = 496;
        int broad = 10;
        String theTxt = "测";
        int fontBroad = 10;

        BufferedImage bi = drawCircle(canvasLen, broad);

        BufferedImage txtBi = drawTxtAndRz(fontBroad, theTxt, fontSize, 0.6f, 0, 1);

        PsaImageUtil.mergeTogether(txtBi, bi, 0, (int) (canvasLen / 2 - fontSize * .8));


        String destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());

        try {
            ImageIO.write(bi, "PNG", new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - st));

    }


    public BufferedImage drawText2(int canvasLen, int broad, int fontSize, char theTxt, int degree) {
        int fontBroad = 5;
        String txt = theTxt + "";

        BufferedImage bi = drawCircle(canvasLen, broad);
        BufferedImage txtBi = drawTxtAndRz(fontBroad, txt, fontSize, 0.6f, 0, 1);
        PsaImageUtil.mergeTogether(txtBi, bi, 0, (int) (canvasLen / 2 - fontSize * .8));

        bi = PsaImageUtil.rotateImg(bi, degree);

        return bi;
    }


    public BufferedImage drawCircle(int canvasLen, int broad) {

        int fontThickness = 5;

        int canvasWidth = canvasLen;
        int canvasHeight = canvasLen;

        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        /***********draw circle*************/
        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - broad;
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        Shape circle = new Arc2D.Double(broad, broad, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);
        g2d.draw(circle);
        g2d.setStroke(new BasicStroke(fontThickness));
        g2d.dispose();//销毁资源

        return bi;
    }

    public BufferedImage drawTxtAndRz(int fontBStroke, String txt, int fontSize, float rz, float rx, float ry) {
        int width = fontSize;
        int height = fontSize + fontBStroke;
        if (txt.length() > 1) {
            width = txt.length() * fontSize + fontBStroke * 2;
        }

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();
        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(fontBStroke));//设置画笔的粗度
        // 写字
        Font typeFont = new Font("隶书", Font.PLAIN, fontSize);

        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds(txt, mdCtx);
        g2d.setFont(typeFont);
        float mrx = rx == 0 ? 0 : (float) (mdBound.getX() + mdBound.getCenterY());
        float mry = ry == 0 ? 0 : (float) (mdBound.getHeight() + mdBound.getCenterY());

        g2d.drawString(txt, mrx, mry);
        g2d.dispose();//销毁资源
        bi = PsaImageUtil.scaleWidth(bi, rz);

        return bi;
    }


    public BufferedImage drawInnerImage(int canvasLen, int broad) {

        int fontThickness = 5;

        int canvasWidth = canvasLen;
        int canvasHeight = canvasLen;

        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        /***********draw circle*************/
        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - broad;
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        Shape circle = new Arc2D.Double(broad, broad, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);
        g2d.draw(circle);
        g2d.setStroke(new BasicStroke(fontThickness));


        /*********** 画五角星 *************/
        double r1 = 40;
        double r2 = (Math.cos(0.2d * Math.PI) + Math.sin(0.2d * Math.PI) / Math.tan(0.1d * Math.PI)) * r1;

//        System.out.println("五角星两个内径长度");
//        System.out.printf("r1=%s, r2=%s\n", r1, r2);

        // point x,y
        double x = fontThickness + circleRadius;
        double y = (fontThickness + circleRadius);

        // calc point; outer point
        double p1x = x, p1y = y - r2;
        double p2x = x + r2 * Math.cos(0.1d * Math.PI), p2y = y - r1 * Math.cos(0.2d * Math.PI);
        double p3x = x + r2 * Math.sin(.2d * Math.PI), p3y = y + r2 * Math.cos(0.2d * Math.PI);
        double p4x = x - r2 * Math.sin(.2d * Math.PI), p4y = y + r2 * Math.cos(0.2d * Math.PI);
        double p5x = x - r2 * Math.cos(0.1d * Math.PI), p5y = y - r1 * Math.cos(0.2d * Math.PI);

        // A C E B D A
        int[] xPts0 = {(int) p1x, (int) p3x, (int) p5x, (int) p2x, (int) p4x, (int) p1x};
        int[] yPts0 = {(int) p1y, (int) p3y, (int) p5y, (int) p2y, (int) p4y, (int) p1y};

        // get a Triangle
        double mdx = x, mdy = y + r1;
        int[] tx0 = {(int) p2x, (int) mdx, (int) p5x};
        int[] ty0 = {(int) p2y, (int) mdy, (int) p5y};

        g2d.setPaint(Color.RED);
        g2d.fillPolygon(xPts0, yPts0, 6);
        g2d.fillPolygon(tx0, ty0, 3);

        g2d.dispose();//销毁资源

        return bi;
    }


    @Test
    public void drawText() {
        long st = System.currentTimeMillis();
        int fontSize = 68;
        int canvasLen = 496;
        int broad = 10;
        String theTxt = "测试专用章";
        int fontBroad = 10;

        BufferedImage bi = drawTxtAndRz(fontBroad, theTxt, fontSize, 0.6f, 0, 1);
        bi = PsaImageUtil.scaleRect(bi, .8f);

        String destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());
        try {
            ImageIO.write(bi, "PNG", new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - st));


    }


    @Test
    public void drawOvalText() {
        long st = System.currentTimeMillis();
        BufferedImage bi = drawOval(300, 200, 10, 0);
        BufferedImage inBi = drawOval(300, 200, 2, 20);
        bi = PsaImageUtil.mergeTogether(inBi, bi, true);
        String destFile = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());
        try {
            ImageIO.write(bi, "PNG", new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - st));

    }


    public BufferedImage drawOval(int width, int height, int broad, int padding) {

        int w = width - 2 * padding;
        int h = height - 2 * padding;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度

        /***********draw circle*************/
        // 圆弧半径

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度
        Shape oval = new Arc2D.Double(broad + padding, broad + padding, w - 2 * broad, h - 2 * broad, 0, 360, Arc2D.OPEN);
        g2d.draw(oval);
        g2d.dispose();//销毁资源

        return bi;
    }


}
