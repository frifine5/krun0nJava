package com.pdfpng;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class Pdf2PngService {

    private Logger logger = LoggerFactory.getLogger(Pdf2PngService.class);


    public List<byte[]> converPdfToPng(String pdfPath) {
        Document document = new Document();
        long st = System.currentTimeMillis();
        try {
            document.setFile(pdfPath);
            float scale = 1.0f; //缩放比例
            float rotation = 0f; //旋转角度

            int page = document.getNumberOfPages();
            logger.info("待转换文件共{}页", page);
            List<byte[]> imgList = new ArrayList<>();

            for (int i = 0; i < page; i++) {
                BufferedImage image = (BufferedImage) document.getPageImage(i,
                        GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX,
                        rotation, scale);
                RenderedImage rendImage = image;
                OutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    ImageIO.write(rendImage, "png", out);
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
            document.dispose();
            return imgList;
        } catch (Exception e) {
            logger.error("转换出错", e);
        }
        return null;
    }


}
