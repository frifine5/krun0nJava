package com.icepdf.test;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;


public class TestIcePdf1 {


    @Test
    public void covPdfToJpg() {
        String dir = "C:\\Users\\49762\\Desktop\\";
        String pdfName = "756b02d4c0834faba1f5fdba2a1e12e9.pdf";


        String pdf = dir + pdfName;

        Document document = new Document();
        long st = System.currentTimeMillis();

        try {
            document.setFile(pdf);
            float scale = 1.0f; //缩放比例
            float rotation = 0f; //旋转角度
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = (BufferedImage) document.getPageImage(i,
                        GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX,
                        rotation, scale);
                RenderedImage rendImage = image;
                File file = new File(dir + "images\\img_" + i + ".jpg");
                // 这里png作用是：格式是jpg但有png清晰度
                ImageIO.write(rendImage, "png", file);
                image.flush();
            }
            document.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        long et = System.currentTimeMillis();
        System.out.println("======================完成============================");

        System.out.printf("耗时%d毫秒", (et - st));


    }


    @Test
    public void covPdfToJpg2() {
        String dir = "C:\\Users\\49762\\Desktop\\";
        String pdfName = "756b02d4c0834faba1f5fdba2a1e12e9.pdf";


        String pdf = dir + pdfName;

        Document document = new Document();
        long st = System.currentTimeMillis();
        try {
            document.setFile(pdf);
            float scale = 1.0f; //缩放比例
            float rotation = 0f; //旋转角度

            int page = document.getNumberOfPages();
            System.out.printf("共%d页", page);
            List<byte[]> imgList = new ArrayList<>();

            for (int i = 0; i < page; i++) {
                BufferedImage image = (BufferedImage) document.getPageImage(i,
                        GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX,
                        rotation, scale);
                RenderedImage rendImage = image;

                OutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    ImageIO.write(rendImage, "jpg", out);
                    byte[] ecByts = ((ByteArrayOutputStream) out).toByteArray();
                    imgList.add(ecByts);
                    out.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (Exception e) {
                    }
                }

                image.flush();
            }

            for (int i = 0; i < imgList.size(); i++) {
                byte[] b = imgList.get(i);
                FileOutputStream output = new FileOutputStream(String.format(dir + "\\images\\images1-%d.jpg", i));
                output.write(b);
                output.flush();
                output.close();
            }


            document.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        long et = System.currentTimeMillis();
        System.out.println("======================完成============================");

        System.out.printf("耗时%d毫秒", (et - st));


    }



}
