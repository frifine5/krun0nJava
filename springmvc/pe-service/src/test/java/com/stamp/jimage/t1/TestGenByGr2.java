package com.stamp.jimage.t1;

import com.common.utils.FileUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class TestGenByGr2 {


    @Test
    public void test1() throws Exception {

        drawImg(496, 12, "在线平台制作章一二", "企业专用章", .5f);

        // 东莞市骏和通信器材有限公司
//        drawImg(496, 12, "东莞市厚街赤岭第一分公司2", "企业专用章");


    }

    public void drawImg(int canvas, int broad, String name, String type, float ri) {

        int canvasWidth = canvas;
        int canvasHeight = canvasWidth;
        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);


        /***********draw circle*************/
        int fontThickness = broad;
        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - fontThickness;
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(fontThickness));//设置画笔的粗度

        Shape circle = new Arc2D.Double(fontThickness, fontThickness, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);

        g2d.draw(circle);
        g2d.setStroke(new BasicStroke(5));


        // 画五角星

        double r1 = 40;
        double r2 = (Math.cos(0.2d * Math.PI) + Math.sin(0.2d * Math.PI) / Math.tan(0.1d * Math.PI)) * r1;

        System.out.println("五角星两个内径长度");
        System.out.printf("r1=%s, r2=%s\n", r1, r2);

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


        // 画下行文字

        Font typeFont = new Font("宋体", Font.PLAIN, 48);

        String foot = type;
        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds(foot, mdCtx);
        g2d.setFont(typeFont);
        g2d.drawString(foot, (float) (circleRadius - mdBound.getCenterX() + fontThickness),
                (float) (circleRadius * 1.5 - mdBound.getCenterY() + fontThickness));


        // 画上弧文字

//        String name = "中国科学院信息工程研究所()中国科学院信息工程研究所";
        int fontSize = 36;

        int len = name.length();
        if (len < 10) {
            fontSize = 60;
        } else if (len < 25) {
            fontSize = 56;
        } else { // 超过25个字，先按28pt算
            fontSize = 42;
        }


        Font f = new Font("宋体", Font.PLAIN, fontSize);
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(name, context);


        double msgWidth = bounds.getWidth();
        double msgHeight = bounds.getHeight();
        double msgX = bounds.getX();
        double msgY = bounds.getY();

        System.out.println(String.format("msg: w=%s, h=%s, x=%s, y=%s",
                msgWidth, msgHeight, msgX, msgY));


        int countOfMsg = name.length();
        double interval = msgWidth / (countOfMsg - 1);//计算间距
        System.out.println("字数：" + countOfMsg);
        System.out.println(interval);

        // 定义文字内径长
        double newRadius = circleRadius - fontSize - 5;
        System.out.println("新内径：newRadius=" + newRadius);

        double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));//每个间距对应的角度

        System.out.println("计算字长所需的弧长比：" + (radianPerInterval * countOfMsg) / (2 * Math.PI));

        //第一个元素的角度
        double firstAngle = countOfMsg * radianPerInterval / 2.0 + Math.PI / 2;

        System.out.println("第一个元素的角度：" + firstAngle);

        for (int i = 0; i < countOfMsg; i++) {
            double aa = firstAngle - i * radianPerInterval;
            double ax = newRadius * Math.sin(Math.PI / 2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
            double ay = newRadius * Math.cos(aa - Math.PI / 2);//同上类似，这样处理就不必再考虑正负的问题了
            AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2 - aa);// ,x0 + ax, y0 + ay);
            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(name.substring(i, i + 1), (float) (circleRadius + ax + fontThickness),
                    (float) (circleRadius - ay + fontThickness));
        }


        g2d.dispose();//销毁资源
        String savepath = String.format("C:\\Users\\49762\\Desktop\\image_%s.png", System.currentTimeMillis());

        // resize it

        BufferedImage nbi = resizeImage(bi, (int)(ri*canvas));

        try {
            ImageIO.write(nbi, "PNG", new File(savepath));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public BufferedImage resizeImage(BufferedImage bi, int scaleSize)  {

        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float scale=width/scaleSize;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(scaleSize, (int)(height/scale), BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance(scaleSize, (int)(height/scale), Image.SCALE_SMOOTH), 0,
                0, null);
        return buffImg;
    }

    @Test
    public void test2() throws Exception {

        int canvasWidth = 400;
        int canvasHeight = 400;
        double lineArc = 270 * (Math.PI / 180);//角度转弧度
        String savepath = String.format("C:\\Users\\49762\\Desktop\\itext\\image_%s.png", System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
        String head = "中国科学院信息工程研究所-1024";
        String foot = "受理专用章";
        String center = format.format(new Date());
        BufferedImage image = TestGenByGr2.getSeal(head, center, foot, canvasWidth, canvasHeight, lineArc);
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。


        try {
            ImageIO.write(image, "PNG", os);
            byte[] data = os.toByteArray();
            System.out.println(Base64.getEncoder().encodeToString(data));
            FileUtil.writeInFiles(savepath, data);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static BufferedImage getSeal(String head, String center, String foot, int canvasWidth, int canvasHeight, double lineArc) {
        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        //设置画笔
//        g2d.setPaint(Color.BLACK);
//        g2d.fillRect(0, 0, canvasWidth, canvasHeight);
//        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
//        g2d.dispose();
//        g2d = bi.createGraphics();


        // 圆弧半径
        int circleRadius = Math.min(canvasWidth, canvasHeight) / 2 - 5;

        /***********draw circle*************/
        int fontThickness = 5;

        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(fontThickness));//设置画笔的粗度
        Shape circle = new Arc2D.Double(fontThickness, fontThickness, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);


//        Shape circle = new Arc2D.Double(fontThickness, 100, 400, 400, 0, 180, Arc2D.OPEN);


        g2d.draw(circle);
        g2d.setStroke(new BasicStroke(1));
        /************************************/

        /***************draw line*******************/
//        double halfHeight = circleRadius * (Math.cos(lineArc));
//        double halfWidth = circleRadius * (Math.sin(lineArc));
//
//        g2d.drawLine((int) (circleRadius - halfWidth), (int) (circleRadius - halfHeight), (int) (circleRadius + halfWidth), (int) (circleRadius - halfHeight));//
//        g2d.drawLine((int) (circleRadius - halfWidth), (int) (circleRadius + halfHeight), (int) (circleRadius + halfWidth), (int) (circleRadius + halfHeight));//
        /***********************END********************/


        int fontSize = 30;
        Font f = new Font("宋体", Font.PLAIN, fontSize);
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(center, context);

        System.out.println("X:" + bounds.getX());
        System.out.println("Y:" + bounds.getY());
        System.out.println("height:" + bounds.getHeight());
        System.out.println("center y:" + bounds.getCenterY());
        g2d.setFont(f);
//        g2d.drawString(center, (float) (circleRadius - bounds.getCenterX()), (float) (circleRadius - bounds.getCenterY()));

        /********************END*********************/

        /*****************draw foot*******************/
        fontSize = 50;
        f = new Font("宋体", Font.PLAIN, fontSize);
        context = g2d.getFontRenderContext();
        bounds = f.getStringBounds(foot, context);
        g2d.setFont(f);
        g2d.drawString(foot, (float) (circleRadius - bounds.getCenterX() + fontThickness),
                (float) (circleRadius * 1.5 - bounds.getCenterY() + fontThickness));


        /***************draw string head**************/
        fontSize = 42;
        f = new Font("宋体", Font.PLAIN, fontSize);
        context = g2d.getFontRenderContext();
        bounds = f.getStringBounds(head, context);

        double msgWidth = bounds.getWidth();
        int countOfMsg = head.length();
        double interval = msgWidth / (countOfMsg - 1);//计算间距

        // bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
        double newRadius = circleRadius + bounds.getY() - fontThickness;

        double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));//每个间距对应的角度

        System.out.println("计算字长所需的弧长比：" + (radianPerInterval * countOfMsg) / (2 * Math.PI));

        //第一个元素的角度
        double firstAngle;
        if (countOfMsg % 2 == 1) {//奇数
            firstAngle = (countOfMsg - 1) * radianPerInterval / 2.0 + Math.PI / 2 + 0.08;
        } else {//偶数
            firstAngle = (countOfMsg / 2.0 - 1) * radianPerInterval + radianPerInterval / 2.0 + Math.PI / 2 + 0.08;
        }

        for (int i = 0; i < countOfMsg; i++) {
            double aa = firstAngle - i * radianPerInterval;
            double ax = newRadius * Math.sin(Math.PI / 2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
            double ay = newRadius * Math.cos(aa - Math.PI / 2);//同上类似，这样处理就不必再考虑正负的问题了
            AffineTransform transform = AffineTransform.getRotateInstance(Math.PI / 2 - aa);// ,x0 + ax, y0 + ay);
            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(head.substring(i, i + 1), (float) (circleRadius + ax + fontThickness),
                    (float) (circleRadius - ay - fontThickness));
        }

        g2d.dispose();//销毁资源
        return bi;
    }


    @Test
    public void test3() throws Exception {

        String STORE_PATH = "C:\\Users\\49762\\Desktop\\印模制作\\tmp2\\result.png";
        String TEMPLATE_PATH = "C:\\Users\\49762\\Desktop\\印模制作\\tmp2\\template.jpeg";


        long start = System.currentTimeMillis();

//        BufferedImage image = ImageIO.read(new File(TEMPLATE_PATH));
        BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);


        // create graphics
        Graphics2D graphics = image.createGraphics();

        // set graphics profile
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        // draw circular
        graphics.setPaint(Color.BLUE);
        graphics.drawArc(200, 200, 200, 200, 90, 450 - 30);
        graphics.fillArc(200, 200, 200, 200, 90, 450 - 30);

        // draw sector
        graphics.setPaint(Color.RED);
        graphics.drawArc(200, 200, 200, 200, 90 - 30, 30);
        graphics.fillArc(200, 200, 200, 200, 90 - 30, 30);


        double r1 = 50;
        double r2 = (Math.cos(0.2d * Math.PI) + Math.sin(0.2d * Math.PI) / Math.tan(0.1d * Math.PI)) * r1;

        System.out.println("五角星两个内径长度");
        System.out.printf("r1=%s, r2=%s\n", r1, r2);

        // point x,y
        double x = 200, y = 200;

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

        graphics.setPaint(Color.RED);
        graphics.fillPolygon(xPts0, yPts0, 6);
        graphics.fillPolygon(tx0, ty0, 3);


        // store and end
        graphics.dispose();
        ImageIO.write(image, "PNG", new File(STORE_PATH));

        System.out.println(System.currentTimeMillis() - start);


    }


    @Test
    public void test4() {

        double r1 = 50;
        double r2 = (Math.cos(0.2d * Math.PI) + Math.sin(0.2d * Math.PI) / Math.tan(0.1d * Math.PI)) * r1;

        System.out.println("五角星两个内径长度");
        System.out.printf("r1=%s, r2=%s\n", r1, r2);


        System.out.println(r2 * Math.sin(.1d * Math.PI));
        System.out.println(r1 * Math.cos((.2d) * Math.PI));

        System.out.println(Math.cos((45 / 180) * Math.PI));


//        rectangle


    }


    @Test
    public void test9() throws Exception {

        int width = 600;
        int height = 600;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        String s = "------ Java 2d 旋转";
        Font f = new Font("宋体", Font.BOLD, 16);
        Color[] colors = {Color.ORANGE, Color.LIGHT_GRAY};
        g2d.setFont(f);
        //   平移原点到图形环境的中心
        g2d.translate(width / 2, height / 2);
        //   旋转文本
        for (int i = 0; i < 4; i++) {
            g2d.rotate(90 * Math.PI / 180);
            g2d.setPaint(colors[i % 2]);
            g2d.drawString(s, 20, 5);
        }

        g2d.dispose();
        ImageIO.write(bi, "png", new FileOutputStream("C:\\Users\\49762\\Desktop\\itext\\f.png"));


    }


    @Test
    public void testRotate2Text() throws  Exception{

        int width = 600;
        int height = 600;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();

        String text = "测试Graphics2D旋转文字";

        float radius = 200;

        Rectangle rec = new Rectangle(width, height);

        g2d.setColor(Color.WHITE);
        g2d.fill(rec);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("宋体", Font.BOLD, 16));

        g2d.translate(rec.getCenterX(), rec.getCenterY());// 平移原点到中心

        g2d.translate(0, -radius); // 原点到 -r

        int length = text.length();

        double evAg = (120/length) * Math.PI / 180;
        System.out.println(" eve angle = " + evAg);

        for (int i = 0; i < length; i++) {
            g2d.drawString(text.substring(i, i + 1), 0, 0);
            g2d.rotate(evAg, 0, radius);
        }
        g2d.dispose();
        ImageIO.write(bi, "png", new FileOutputStream("C:\\Users\\49762\\Desktop\\itext\\img_2.png"));

    }


}