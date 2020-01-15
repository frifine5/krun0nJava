package com.pdfpng;

import com.common.ParamsUtil;
import com.common.PsaImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 个人私章
 * @author WangChengyu
 * 2020/1/14 11:21
 */
public class PersonImageService {




    /**
     * 方章：固名缩比 196像素, 2-4个字
     * */
    public byte[] genPsSquareSeal(String name, float ri){
        int canvasWidth = 196;// 正方形以宽为准
        int canvasHeight = 196;
        int broad = 5;
        if(ParamsUtil.checkNull(name)){
            throw new RuntimeException("名字为空");
        }
        if(name.length()<2 || name.length()>4){
            throw new RuntimeException("名字超长(4)");
        }

        // 画布
        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();
        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        /***********draw rectangle*************/
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度
        Shape rect = new Rectangle2D.Double(broad, broad, canvasWidth - broad * 2, canvasHeight - broad * 2);
        g2d.draw(rect);


        /***********draw name*************/

        char[] nameChars = name.toCharArray(); // 分解为单字数组
        int realLen = nameChars.length;
        // style ?  2-4字：楷体方章，两纵两横； 大于4字，按字数和方纵来，一般为偶数
        String n1 = nameChars[0] + "";
        String n2 = nameChars[1] + "";
        String n3 = realLen>2? nameChars[2] + "" : "之";
        String n4 = realLen>3? nameChars[3] + "" : "印";

        float cetX = canvasWidth/2;
        float cetY = canvasHeight/2;

        int fontSize  = 72;
        String fontName = "楷体"; // 楷体 黑体 宋体 隶书
        Font typeFont = new Font(fontName, Font.BOLD, fontSize);

        String text = n1;
        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds(text, mdCtx);
        g2d.setFont(typeFont);
        g2d.drawString(text, cetX, (float)(cetY - (mdBound.getHeight() + mdBound.getY()) ));

        text = n2;
        g2d.setFont(typeFont);
        g2d.drawString(text, cetX, (float)(cetY  + mdBound.getHeight() + mdBound.getCenterY()));

        text = n3;
        g2d.setFont(typeFont);
        g2d.drawString(text, (float) (cetX - mdBound.getWidth()), (float)(cetY - (mdBound.getHeight() + mdBound.getY()) ));

        text = n4;
        g2d.setFont(typeFont);
        g2d.drawString(text, (float) (cetX  - mdBound.getWidth()), (float)(cetY  + mdBound.getHeight() + mdBound.getCenterY()));

        g2d.dispose();//销毁资源

        /***********resize image*************/
        BufferedImage nbi = null;
        if(1.0 == ri){
            nbi = bi;
        }else{
            nbi = PsaImageUtil.resizeImage(bi, (int)(ri*canvasWidth));
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
            ImageIO.write(nbi, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("图片生成失败", e);
        }
    }

    /**
     * 方章：固名缩比 196像素, 5-6个字
     * */
    public byte[] genPsSquareSealSix(String name, float ri){
        int canvasWidth = 196;// 正方形以宽为准
        int canvasHeight = 196;
        int broad = 5;
        if(ParamsUtil.checkNull(name)){
            throw new RuntimeException("名字为空");
        }
        if(name.length()<4 || name.length()>6){
            throw new RuntimeException("名字超长(6)");
        }

        // 画布
        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();
        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        /***********draw rectangle*************/
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度
        Shape rect = new Rectangle2D.Double(broad, broad, canvasWidth - broad * 2, canvasHeight - broad * 2);
        g2d.draw(rect);


        /***********draw name*************/

        char[] nameChars = name.toCharArray(); // 分解为单字数组
        int realLen = nameChars.length;
        // style ?  5-6字：楷体方章，两纵三横
        String n1 = nameChars[0] + "";
        String n2 = nameChars[1] + "";
        String n3 = nameChars[2] + "";
        String n4 = nameChars[3] + "";
        String n5 = nameChars[4] + "";
        String n6 = realLen>5? nameChars[5] + "" : "印";

        float cetX = canvasWidth/2;
        float cetY = canvasHeight/2;

        int fontSize  = 52;
        String fontName = "楷体"; // 楷体 黑体 宋体 隶书
        Font typeFont = new Font(fontName, Font.BOLD, fontSize);

        String text = "典";
        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds(text, mdCtx);
        g2d.setFont(typeFont);

        text = n1;
        g2d.drawString(text, cetX + 10f, (float)(cetY - (mdBound.getHeight() + mdBound.getCenterY())));

        text = n2;
        g2d.drawString(text, cetX + 10f, (float)(cetY - mdBound.getCenterY()));

        text = n3;
        g2d.drawString(text, cetX + 10f, (float)(cetY  + mdBound.getHeight() - mdBound.getCenterY()));

        text = n4;
        g2d.drawString(text, (float) (cetX - mdBound.getWidth()) - 10f, (float)(cetY - (mdBound.getHeight() + mdBound.getCenterY())));

        text = n5;
        g2d.drawString(text, (float) (cetX  - mdBound.getWidth()) - 10f, (float)(cetY - mdBound.getCenterY()));

        text = n6;
        g2d.drawString(text, (float) (cetX  - mdBound.getWidth()) - 10f, (float)(cetY  + mdBound.getHeight() - mdBound.getCenterY()));


        g2d.dispose();//销毁资源

        /***********resize image*************/
        BufferedImage nbi = null;
        if(1.0 == ri){
            nbi = bi;
        }else{
            nbi = PsaImageUtil.resizeImage(bi, (int)(ri*canvasWidth));
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
            ImageIO.write(nbi, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("图片生成失败", e);
        }
    }


    /**
     * 矩形章：基础高196像素 宽按字数*52 + broads，大于6个字，最长16个字
     * */
    public byte[] genPsRectSealTen(String name, float ri, float wh){
        int fontSize = 52;
        int nlen = name.length();
        if(name.length()>16|| nlen<7){
            throw new RuntimeException("名字超长(16)");
        }

        int canvasWidth, canvasHeight;
        canvasHeight =  196;
        int broad = 5;
        String t1, t2;
        int hfLen = nlen%2 ==0? nlen/2: nlen/2 +1;
        if(name.contains("·")){
            t1 = name.substring(0, name.indexOf("·") + 1);
            t2 = name.substring(name.indexOf("·") + 1);
        }else{
            t1 = name.substring(0, hfLen);
            t2 = name.substring(hfLen);
            if(nlen%2 == 1){
                t2 = t2 + "印";
            }
        }

        // 计算宽的值
        int wcount = t1.length();
        if(t1.length()>= t2.length()){
            wcount = t1.length();
        }else{
            wcount = t2.length();
        }

        canvasWidth = fontSize * wcount + broad * 8;

        BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();
        // 画线平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        /***********draw rectangle*************/
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(broad));//设置画笔的粗度
        Shape rect = new Rectangle2D.Double(broad, broad, canvasWidth - broad * 2, canvasHeight - broad * 2);
        g2d.draw(rect);


        /*********** 写字 *************/

        g2d.setPaint(Color.RED);
        g2d.setStroke(new BasicStroke(5));
        String fontName = "楷体"; // 楷体 黑体 宋体 隶书
        Font typeFont = new Font(fontName, Font.BOLD, fontSize);

        FontRenderContext mdCtx = g2d.getFontRenderContext();
        Rectangle2D mdBound = typeFont.getStringBounds("单", mdCtx);
        g2d.setFont(typeFont);
        g2d.drawString(t1, (float)broad*2, (float)(canvasHeight/2 + mdBound.getCenterY()));
        g2d.drawString(t2, (float)broad*2, (float)(canvasHeight/2 + mdBound.getCenterY() + mdBound.getHeight()));
        g2d.dispose();

        BufferedImage nbi = null;

        if((1.0 == ri|| ri<=0) && wh<=0){
            nbi = bi;
        }else{
            int nHeight = (int)(canvasHeight * ri);
            int nWidth = (int)(canvasHeight * ri * wh);
            nbi = PsaImageUtil.resizeImage(bi, nWidth, nHeight);
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
            ImageIO.write(nbi, "png", os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("图片生成失败", e);
        }
    }


}
