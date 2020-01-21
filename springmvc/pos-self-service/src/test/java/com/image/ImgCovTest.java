package com.image;

import com.common.FileUtil;
import com.pdfpng.PersonImageService;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImgCovTest {


    @Test
    public void testSvgToPng(){
        String sBstr = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj48c3ZnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgdmVyc2lvbj0iMS4xIiB3aWR0aD0iMTgwIiBoZWlnaHQ9IjU0Ij48cGF0aCBzdHJva2UtbGluZWpvaW49InJvdW5kIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlPSIjZjAwIiBmaWxsPSJub25lIiBkPSJNIDM4IDEgYyAtMC4wNyAwLjA1IC0zLjE5IDEuNzggLTQgMyBjIC0xLjYgMi40IC0xLjkyIDYuOTIgLTQgOSBjIC03LjY0IDcuNjQgLTIwLjYgMTYuMTEgLTI4IDIzIGMgLTAuODQgMC43OCAtMSA0IC0xIDQiLz48cGF0aCBzdHJva2UtbGluZWpvaW49InJvdW5kIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlPSIjZjAwIiBmaWxsPSJub25lIiBkPSJNIDczIDkgYyAwLjA5IDAgMy45MiAtMC42NSA1IDAgYyAxLjY5IDEuMDEgMy43OSAzLjk5IDUgNiBjIDAuNjQgMS4wNiAwLjkxIDIuNjcgMSA0IGMgMC4yMyAzLjIyIDAuNyA3LjA2IDAgMTAgYyAtMC44NSAzLjU2IC00LjY1IDguNyAtNSAxMSBjIC0wLjExIDAuNjkgMS45OSAxLjggMyAyIGMgMS45NSAwLjM5IDQuNzQgMC4yOCA3IDAgYyAyLjk4IC0wLjM3IDYuMTYgLTEuMTMgOSAtMiBsIDQgLTIiLz48cGF0aCBzdHJva2UtbGluZWpvaW49InJvdW5kIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlPSIjZjAwIiBmaWxsPSJub25lIiBkPSJNIDE0MiAxIGMgMC4xOSAwIDcuNzggLTAuODEgMTEgMCBjIDQuMTkgMS4wNSA4Ljc0IDMuODcgMTMgNiBjIDEuMDcgMC41NCAyLjIyIDEuMjIgMyAyIGMgMC43OCAwLjc4IDEuNzUgMiAyIDMgYyAwLjM0IDEuMzggMC41NiA0LjAyIDAgNSBjIC0wLjUgMC44OCAtMi45OSAxLjEzIC00IDIgYyAtMS4xMyAwLjk3IC0xLjc5IDMuMjMgLTMgNCBjIC0yLjA3IDEuMzEgLTcuMzYgMi4zNiAtOCAzIGMgLTAuMjcgMC4yNyAxLjkzIDEuNjkgMyAyIGMgNS40OCAxLjU2IDEzLjcgMi4yOCAxOCA0IGMgMS4wNCAwLjQyIDEuNzEgMi43IDIgNCBjIDAuMzIgMS40NiAwLjMyIDMuNTQgMCA1IGMgLTAuMjkgMS4zIC0xLjAxIDMuMTUgLTIgNCBjIC0zLjIgMi43NCAtMTIgOCAtMTIgOCIvPjwvc3ZnPg==";
        String pngFile = "/home/dtmp/tmp-img-1.png";
        byte[] data = null;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            byte[] bytes = Base64.getDecoder().decode(sBstr);
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            data = outputStream.toByteArray();
            outputStream.flush();

        }catch (Exception e){
            e.printStackTrace();
        }

        if(null == data) return;
        String dataStr = Base64.getEncoder().encodeToString(data);
        System.out.println(dataStr);


    }



    @Test
    public void testGenPsImg() throws Exception{

        PersonImageService psImgService = new PersonImageService();
//        byte[] imgData = psImgService.genPsSquareSeal("穆罕默德", 1.0f);
//        byte[] imgData = psImgService.genPsSquareSealSix("这是五个字", 1.0f);
        byte[] imgData = psImgService.genPsRectSealLonger("迪丽热巴·迪力木拉提", 1.0f, 1.0f);


        FileUtil.writeInFiles("/home/dtmp/rect/迪丽热巴-3.png", imgData);

    }


    @Test
    public void test1(){

        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        // 增加下面代码使得背景透明
        bi = g2d.getDeviceConfiguration().createCompatibleImage(1, 1, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bi.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
            ImageIO.write(bi, "png", os);
            byte[] a = os.toByteArray();
            System.out.println(Base64.getEncoder().encodeToString(a));
        } catch (IOException e) {
            throw new RuntimeException("图片生成失败", e);
        }

    }





}
