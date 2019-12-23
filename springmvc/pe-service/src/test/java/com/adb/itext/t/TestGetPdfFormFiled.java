package com.adb.itext.t;

import com.common.utils.FileUtil;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.io.RASInputStream;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.net.MalformedURLException;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.awt.geom.RectangularShape;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class TestGetPdfFormFiled {



    @Test
    public void testGetForm()throws Exception{

        String pdf = "C:\\Users\\49762\\Desktop\\7e40c7236c054c19824ac9df3fb05f79.pdf";


        PdfReader pdfReader = new PdfReader(pdf);

        AcroFields acroFields = pdfReader.getAcroFields();

        Map<String, AcroFields.Item> fields = acroFields.getFields();
        System.out.println("fields size = " + fields.size());



        AcroFields.Item gcqm11 = fields.get("Signature1");

        System.out.println(gcqm11.getValue(0));
        System.out.println(gcqm11.getWidget(0));

        System.out.println("页码：" + gcqm11.getPage(0));
        System.out.println("框坐标：");
        PdfObject gcqm11Rect = gcqm11.getValue(0).get(PdfName.RECT);
        System.out.println(gcqm11Rect);

        System.out.println("finish it");





    }




    @Test
    public void testGetFromXiaoxin()throws Exception{
        String dir = "D:\\work documents\\统一电子印章\\广东数广-公众侧\\文档查验itext对比\\小信签完的文件1901\\";
        String pdf = dir + "tmp-1ad527d31bf945e7817b91c6c18c5ec0.pdf";
//        pdf = dir + "tmp-7c1e194757ac40d68793ca1c5e0db7ec.pdf";
//        pdf = dir + "tmp-8f9038c4f95d48769ae515c5fc52ea50.pdf";


        PdfReader pdfReader = new PdfReader(pdf);
        AcroFields acroFields = pdfReader.getAcroFields();

        Map<String, AcroFields.Item> fields = acroFields.getFields();
        System.out.println("fields size = " + fields.size());

        PdfObject obj1193 = pdfReader.getPdfObject(1193);
        System.out.println(obj1193);

//        extractImage2(pdf);

        PdfDictionary dic1193 = (PdfDictionary)obj1193;

        byte[] contents1193 = dic1193.get(PdfName.CONTENTS).getBytes();
        System.out.println(contents1193.length);
        System.out.println(Util.byteToHex(contents1193));
        byte[] bytInt = new byte[4];
        int len = 0;
        if( contents1193[1] == -126){
            System.arraycopy(contents1193, 2, bytInt, 1, 2);
            len = (Util.byteToInt(bytInt)) + 4;
        }else{
            System.arraycopy(contents1193,1, bytInt, 0, 1);
            len = (Util.byteToInt(bytInt)) + 2;
        }
        System.out.println(len);
        byte[] cutByt = new byte[len];
        System.arraycopy(contents1193, 0, cutByt, 0, len);
        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\tmp.p7b", cutByt);

        String uid =  dic1193.getAsString(new PdfName("uid")).toString();
        System.out.println("uid = " + uid);
        System.out.println(new String(Base64.getDecoder().decode(uid)));
        System.out.println(dic1193.get(new PdfName("sealType")));
        System.out.println(new String(Base64.getDecoder().decode(dic1193.get(new PdfName("sealType")).toString())));
        System.out.println(dic1193.get(new PdfName("sealCode")));
        System.out.println(new String(Base64.getDecoder().decode(dic1193.get(new PdfName("sealCode")).toString())));
        System.out.println(dic1193.get(new PdfName("sealName")));
        System.out.println(new String(Base64.getDecoder().decode(dic1193.get(new PdfName("sealName")).toString())));

        System.out.println("finish it !");

    }


    void extractImage(String filename)throws Exception {

        PdfReader reader = null;
        try {
            //读取pdf文件
            reader = new PdfReader(filename);
            //获得pdf文件的页数
            int sumPage = reader.getNumberOfPages();
            //读取pdf文件中的每一页
            for (int i = 1; i <= sumPage; i++) {
                //得到pdf每一页的字典对象
                PdfDictionary dictionary = reader.getPageN(i);
                //通过RESOURCES得到对应的字典对象
                PdfDictionary res = (PdfDictionary) PdfReader.getPdfObject(dictionary.get(PdfName.RESOURCES));
                //得到XOBJECT图片对象
                PdfDictionary xobj = (PdfDictionary) PdfReader.getPdfObject(res.get(PdfName.XOBJECT));
                if (xobj != null) {
                    for (Iterator it = xobj.getKeys().iterator(); it.hasNext(); ) {
                        PdfObject obj = xobj.get((PdfName) it.next());
                        if (obj.isIndirect()) {
                            PdfDictionary tg = (PdfDictionary) PdfReader.getPdfObject(obj);
                            PdfName type = (PdfName) PdfReader.getPdfObject(tg.get(PdfName.SUBTYPE));
                            if (PdfName.IMAGE.equals(type)) {
                                PdfObject object = reader.getPdfObject(obj);
                                if (object.isStream()) {
                                    PRStream prstream = (PRStream) object;
                                    byte[] b;
                                    try {
                                        b = reader.getStreamBytes(prstream);
                                    } catch (UnsupportedPdfException e) {
                                        b = reader.getStreamBytesRaw(prstream);
                                    }
                                    FileOutputStream output = new FileOutputStream(String.format("C:\\Users\\49762\\Desktop\\images\\output%d.jpg", i));
                                    output.write(b);
                                    output.flush();
                                    output.close();
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    void extractImage2(String filename) throws Exception{

        PdfReader pdfReader = new PdfReader(filename);
        int pageSize = pdfReader.getNumberOfPages();
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

        List<byte[]> arraysByte = new ArrayList<>();
        List<float[]> arrays = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {

            int pageNum = i;
            Rectangle pageRect = pdfReader.getPageSize(1);
            float height = pageRect.getHeight();
            float width = pageRect.getWidth();

            pdfReaderContentParser.processContent(pageNum, new RenderListener() {
                @Override
                public void beginTextBlock() {

                }

                @Override
                public void renderText(TextRenderInfo renderInfo) {

                }

                @Override
                public void endTextBlock() {

                }

                @Override
                public void renderImage(ImageRenderInfo renderInfo) {
                    PdfImageObject image0;
                    try {
                        image0 = renderInfo.getImage();
                        byte[] imageByte = image0.getImageAsBytes();
                        Image imageInPDF;
                        imageInPDF = Image.getInstance(imageByte);
                        if (image0 != null && imageInPDF.equals(image0)) {
                            float[] resu = new float[3];
                            // 0=>x;1=>y;2=>z;
                            // z的值始终未1
                            resu[0] = renderInfo.getStartPoint().get(0);
                            resu[1] = renderInfo.getStartPoint().get(1);
                            resu[2] = 1;
                            arrays.add(resu);
                        }
                        byte[] by = image0.getImageAsBytes();
                        arraysByte.add(by);
                    } catch (BadElementException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }

        System.out.println("images size = " + arraysByte.size());
        System.out.println("images poc size="+arrays.size());

        for (int i = 0; i < arraysByte.size(); i++) {
            byte[] b = arraysByte.get(i);
            FileOutputStream output = new FileOutputStream(String.format("C:\\Users\\49762\\Desktop\\images\\images-%d.jpg", i));
            output.write(b);
            output.flush();
            output.close();
        }


    }



    @Test
    public void testTransfer2Images()throws Exception{
        String pdf = "C:\\Users\\49762\\Desktop\\B2.pdf";

    }




    @Test
    public void testGetOriginData()throws Exception{

        String pdf = "C:\\Users\\49762\\Desktop\\B2.pdf";

        PdfReader pdfReader = new PdfReader(pdf);

        AcroFields acroFields = pdfReader.getAcroFields();
        ArrayList<String> sigNames = acroFields.getSignatureNames();
        for(String sigName: sigNames){

            System.out.println("域名 = " + sigName);
            PdfDictionary sig1Dic = acroFields.getSignatureDictionary(sigName);
            byte[] sigValue = sig1Dic.get(PdfName.CONTENTS).getBytes();
            PdfArray rangeArray = (PdfArray)sig1Dic.get(PdfName.BYTERANGE);
            long[] range =  rangeArray.asLongArray();

            RandomAccessFileOrArray randomAccessFileOrArray = pdfReader.getSafeFile();
            InputStream rg = new RASInputStream(new RandomAccessSourceFactory()
                    .createRanged(randomAccessFileOrArray.createSourceView(), range ));

            byte buf[] = new byte[8192];
            int n;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while (-1 != (n = rg.read(buf))) {
                outputStream.write(buf, 0, n);
            }
            byte[] originData = outputStream.toByteArray();
            System.out.println("data.total.length = " + originData.length);

            byte[] md = SM3Util.sm3Digest(originData);
            System.out.println("hash value:\t" + Util.byteToHex(md));

            /*

            // 执行验签
            String hpk = "04B869DBB74ACDF04925F711148B553F1392B36786628B3F05BEBEF04E0F37DFBED3BEB89128445B00B3CA3B5A4FF6CDBF3703FA74F1810547F5D567F7538E8B43";
            String hsk = "675160FFEAF0CAB760BD12C39F79CA6499238D76D2C845DF6A77AD69F3D1A4F1";

            GMTSM2 sm2 = GMTSM2.getInstance();

            boolean verify = sm2.sm2Verify(md, sigValue, Util.hexToByte(hpk));

            System.out.printf("%s 验证结果：%s\n", sigName, verify);

            Map<String, AcroFields.Item> items = acroFields.getFields();
            AcroFields.Item item = items.get(sigName);
            PdfArray rect = (PdfArray)item.getValue(0).get(PdfName.RECT);
            long[] rects = rect.asLongArray();
            System.out.printf("坐标： %d, %d, %d, %d\n 宽 = %d, 高 = %d\n",
                    rects[0], rects[1], rects[2], rects[3],
                    rects[2] - rects[0] , rects[3] - rects[1] );

*/

        }




    }

}
