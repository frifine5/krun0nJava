package com;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class TestMain {

    public static void main(String[] args) {

        String fileName = args[0];
        System.out.printf("读取文件%s仅转换第一页为图片", fileName);

        Document document = new Document();
        long st = System.currentTimeMillis();

        try {
            document.setFile(fileName);
            float scale = 1.0f; //缩放比例
            float rotation = 0f; //旋转角度
            BufferedImage image = (BufferedImage) document.getPageImage(0,
                    GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX,
                    rotation, scale);
            RenderedImage rendImage = image;
            File file = new File("img_tmp.jpg");
            // 这里png作用是：格式是jpg但有png清晰度
            ImageIO.write(rendImage, "png", file);
            image.flush();
            document.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        long et = System.currentTimeMillis();
        System.out.println("====================== 转换完成 ============================");

        System.out.printf("耗时%d毫秒", (et - st));


    }


}
