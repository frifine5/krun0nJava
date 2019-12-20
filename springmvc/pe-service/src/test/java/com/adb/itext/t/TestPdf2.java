package com.adb.itext.t;

import com.common.utils.FileUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;

public class TestPdf2 {


    @Test
    public void testSGAnnotation() throws Exception{

        String dir = "C:\\Users\\49762\\Desktop\\itext\\";

        FileOutputStream out = new FileOutputStream(dir + "1.pdf");
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, out);

        writer.setLinearPageMode();

        doc.open();
        doc.add(new Paragraph("page 1"));

        Image image = Image.getInstance(dir + "image.png");
        float[] widths = { 1f, 4f };

        PdfPTable table = new PdfPTable(widths);

//插入图片
        table.addCell("This one");
        table.addCell(image);

////调整图片大小
//        table.addCell("This two");
//        table.addCell(new PdfPCell(image, true));


        doc.add(table);



        doc.close();

        writer.close();







    }



    @Test
    public void testGetImageXobj() throws Exception{
        String dir = "C:\\Users\\49762\\Desktop\\itext\\";
        String fp = dir + "2015839566-26328696749542401.pdf";

        PdfReader reader = new PdfReader(fp);

        PdfStream pdfStream69 = (PdfStream) reader.getPdfObjectRelease(69);


        PdfImageObject imgObj = new PdfImageObject( (PRStream) pdfStream69 );

        BufferedImage bufImage = imgObj.getBufferedImage();


        ImageIO.write(bufImage, "png", new FileOutputStream(dir + "img2.png"));
        System.out.println("over");


    }

    @Test
    public void testChgImgbk()throws Exception{
        String dir = "C:\\Users\\49762\\Desktop\\itext\\";
        String fp = dir + "img69.png";
        String destFp = dir + "img69-dest.png";

        BufferedImage image = ImageIO.read(new File(fp));

        int height = image.getHeight();
        int width = image.getWidth();

//        BufferedImage bufimg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
//        Graphics2D g = bufimg.createGraphics();
//        bufimg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
//        g.dispose();
//        g = bufimg.createGraphics();
//        g.setColor(new Color(255,0,0));
//        g.setStroke(new BasicStroke(1));
//        g.drawImage(image, 0, 0, null);
//        g.dispose();
//        ImageIO.write(bufimg, "PNG", new FileOutputStream(destFp));

        BufferedImage nImg = img_alpha(image, 0);
        ImageIO.write(nImg, "png", new FileOutputStream(destFp));


    }

    public BufferedImage img_alpha(BufferedImage imgsrc,int alpha) {
        String dir = "C:\\Users\\49762\\Desktop\\itext\\";
        try {
            //创建一个包含透明度的图片,半透明效果必须要存储为png合适才行，存储为jpg，底色为黑色
            BufferedImage back=new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int width = imgsrc.getWidth();
            int height = imgsrc.getHeight();
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int rgb = imgsrc.getRGB(i, j);
                    Color color = new Color(rgb);

                    int red = color.getRed();
                    int greed = color.getGreen();
                    int blue = color.getBlue();

                    if(red == 0 && greed == 0 && blue == 0){
                        Color newcolor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                        back.setRGB(i,j,newcolor.getRGB());
                    }else{
                        Color newcolor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
                        back.setRGB(i,j,newcolor.getRGB());
                    }


                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
