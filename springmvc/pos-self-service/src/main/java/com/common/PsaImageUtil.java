package com.common;


import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class PsaImageUtil {

    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, null, null);

        return res;

    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }


    /**
     * 旋转图片
     *
     * @param image  原图
     * @param degree 旋转角度
     * @return
     */
    public static BufferedImage rotateImg(BufferedImage image, int degree) {
        int iw = image.getWidth();// 原始图象的宽度
        int ih = image.getHeight();// 原始图象的高度
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;


        degree = degree % 360;
        if (degree < 0) degree = 360 + degree;// 将角度转换到0-360度之间
        double ang = Math.toRadians(degree);// 将角度转为弧度

        // * 确定旋转后的图象的高度和宽度
        if (degree == 180 || degree == 0 || degree == 360) {
            w = iw;
            h = ih;
        } else if (degree == 90 || degree == 270) {
            w = ih;
            h = iw;
        } else {
            // int d = iw + ih;
            double cosVal = Math.abs(Math.cos(ang));
            double sinVal = Math.abs(Math.sin(ang));
            w = (int) (sinVal * ih) + (int) (cosVal * iw);
            h = (int) (sinVal * iw) + (int) (cosVal * ih);
        }
        x = (w / 2) - (iw / 2);// 确定原点坐标
        y = (h / 2) - (ih / 2);
        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = rotatedImage.createGraphics();
        rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        gs.dispose();
        gs = rotatedImage.createGraphics();
        gs.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2, h / 2);       // 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(image, rotatedImage);
        image = rotatedImage;

        return image;
    }


    /**
     * 缩放图片
     *
     * @param bi        原图
     * @param scaleSize 缩放大小
     */
    public static BufferedImage resizeImage(BufferedImage bi, int scaleSize) {

        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float scale = width / scaleSize;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(scaleSize, (int) (height / scale), BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance(scaleSize, (int) (height / scale), Image.SCALE_SMOOTH), 0,
                0, null);
        return buffImg;
    }

    /**
     * 等比例缩放
     */
    public static BufferedImage scaleRect(BufferedImage bi, float scale) {

        float width = bi.getWidth() * scale;
        float height = bi.getHeight() * scale;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH), 0, 0, null);
        return buffImg;
    }

    /**
     * 按指定宽高重画
     */
    public static BufferedImage resizeImage(BufferedImage bi, int width, int height) {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return buffImg;
    }


    /**
     * 宽高比缩放
     */
    public static BufferedImage resizeImage(BufferedImage bi, float wh) {

        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float nHeight = height / wh;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage((int) width, (int) nHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance((int) width, (int) nHeight, Image.SCALE_SMOOTH), 0, 0, null);
        return buffImg;
    }

    /**
     * 按宽缩放
     */
    public static BufferedImage scaleWidth(BufferedImage bi, float scale) {

        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float nWidth = width * scale;
        BufferedImage buffImg = null;
        buffImg = new BufferedImage((int) nWidth, (int) height, BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance((int) nWidth, (int) height, Image.SCALE_SMOOTH), 0, 0, null);
        return buffImg;
    }


    /**
     * 画定制的企业章
     *
     * @param canvas 长宽
     * @param broad  外圆宽
     * @param name   外弧长文字
     * @param type   章类型文字
     * @return bufferedImage
     */
    public static BufferedImage drawImg(int canvas, int broad, String name, String type, int colorType) {
        Color color = Color.RED;
        if (1 == colorType) {// 仅指定蓝；默认红
            color = Color.BLUE;
        }


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
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(fontThickness));//设置画笔的粗度

        Shape circle = new Arc2D.Double(fontThickness, fontThickness, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);

        g2d.draw(circle);
        g2d.setStroke(new BasicStroke(5));


        // 画五角星
        double r1 = 40;
        double r2 = (Math.cos(0.2d * Math.PI) + Math.sin(0.2d * Math.PI) / Math.tan(0.1d * Math.PI)) * r1;

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

        g2d.setPaint(color);
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

        // 定义文字内径长
        double newRadius = circleRadius - fontSize - 5;
//        System.out.println("新内径：newRadius=" + newRadius);

        double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));//每个间距对应的角度

//        System.out.println("计算字长所需的弧长比：" + (radianPerInterval * countOfMsg) / (2 * Math.PI));

        //第一个元素的角度
        double firstAngle = countOfMsg * radianPerInterval / 2.0 + Math.PI / 2;

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
        return bi;
    }


    /**
     * 图片合成
     */
    public static BufferedImage mergeTogether(BufferedImage b, BufferedImage d, boolean centerAlign) {
        Graphics2D g = null;
        int dw = d.getWidth();
        int dh = d.getHeight();
//            System.out.printf("底图宽高：%s, %s\n", dw, dh);
        int w = b.getWidth();
        int h = b.getHeight();
//            System.out.printf("上图宽高：%s, %s\n", w, h);
//            System.out.println("是否中心对齐: " + centerAlign);
        int x = 0;
        int y = 0;
        if (centerAlign) {
            x = (dw - w) / 2;
            y = (dh - h) / 2;
        }
//            System.out.printf("原点(x, y) = (%s, %s)\n", x, y);
        g = d.createGraphics();
        // 画线平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        // 合成
        g.drawImage(b, x, y, w, h, null);
        g.dispose();
        return d;
    }


    /**
     * 图片合成
     */
    public static BufferedImage mergeTogether(BufferedImage b, BufferedImage d, int xToCenter, int yToCenter) {
        Graphics2D g = null;
        int dw = d.getWidth();
        int dh = d.getHeight();
        int w = b.getWidth();
        int h = b.getHeight();

        int x = (dw - w)/2 - xToCenter;
        int y = (dh - w)/2 - yToCenter;

        g = d.createGraphics();
        // 画线平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        // 合成
        g.drawImage(b, x, y, w, h, null);
        g.dispose();
        return d;
    }


}
