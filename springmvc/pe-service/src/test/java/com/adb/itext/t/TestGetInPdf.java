package com.adb.itext.t;


import com.common.utils.FileUtil;
import com.common.utils.ParamsUtil;
import com.google.common.collect.Lists;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.security.*;
import com.sm2.GMTSM2;
import com.sm3.SM3Digest;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.*;
import java.util.List;

public class TestGetInPdf {


    @Test
    public void test1GetLocation() throws Exception {

        String pdf = "C:\\Users\\49762\\Desktop\\12345.pdf";

        pdf = "D:\\work documents\\统一电子印章\\广东数广-公众侧\\文档查验itext对比\\test.pdf";
//        pdf = "C:\\Users\\49762\\Documents\\WeChat Files\\bearpandaer\\FileStorage\\File\\2019-12\\ShouQuanWeiTuoShu.pdf";
//        pdf = "C:\\Users\\49762\\Documents\\WeChat Files\\bearpandaer\\FileStorage\\File\\2019-12\\关于广报中心进驻办公区域调整的搬迁通知.pdf";
        pdf = "C:\\Users\\49762\\Desktop\\授权书.pdf";
//        pdf = "C:\\Users\\49762\\Desktop\\a1.pdf";
        pdf = "C:\\Users\\49762\\Desktop\\wtr-00.pdf";


        Image signImage = Image.getInstance("D:\\home\\orgStamp2.png");


        List<float[]> keyWordsList = getKeyWords1(FileUtil.fromDATfile(pdf),
                "业1214143151");
        if (ParamsUtil.checkListNull(keyWordsList)) {
            System.out.println("没查到关键字");
//            return;
        }

        byte[] tmpData = FileUtil.fromDATfile(pdf);

        for (float[] eObj : keyWordsList) {
            if (eObj.length < 4) {
                System.out.println("此项不全，跳过");
                continue;
            }
            String ts = String.format("x=%s,\t y=%s,\t page=%s,\t height=\t%s\t\t%sf, %sf",
                    eObj[1], eObj[2], eObj[0], eObj[3], eObj[1], eObj[2]);
            System.out.println(ts);
//            tmpData = testSignByRsa(eObj, tmpData,
//                    Base64.getEncoder().encodeToString("测试位置rsa签".getBytes()),
//                    "{123}", signImage);

            tmpData = testSignByEcc(eObj, tmpData,
                    Base64.getEncoder().encodeToString("测试位置ecc签".getBytes()),
                    "{123}", signImage);

        }

        tmpData = testSignByEcc(null, tmpData,
                Base64.getEncoder().encodeToString("测试位置ecc签".getBytes()),
                "{123}", signImage);


        if(null != tmpData){
            FileOutputStream fos = new FileOutputStream("C:\\Users\\49762\\Desktop\\B2.pdf");
            int len = tmpData.length;
            fos.write(tmpData, 0, len);
            fos.flush();
            fos.close();
        }


    }


    // 定义返回页码


     private static List<float[]> getKeyWords(byte[] fileData, final String keyWord) {
        List<float[]> arrays = Lists.newArrayList();
        try {
            PdfReader pdfReader = new PdfReader(fileData);
            int pageSize = pdfReader.getNumberOfPages();
            System.out.println("页数=" + pageSize);
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

            for (int i = 1; i <= pageSize; i++) {

                int pageNum = i;
                Rectangle pageRect = pdfReader.getPageSize(1);
                float height = pageRect.getHeight();
                float width = pageRect.getWidth();

                pdfReaderContentParser.processContent(pageNum, new RenderListener() {

                    StringBuilder sb = new StringBuilder("");
                    int maxLength = keyWord.length();

                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        // 只适用 单字块文档 以及 关键字整个为一个块的情况
                        // 设置 关键字文本为单独的块，不然会错位
                        boolean isKeywordChunk = textRenderInfo.getText().length() == maxLength;
                        if (isKeywordChunk) {
                            // 文档按照 块 读取
                            sb.delete(0, sb.length());
                            sb.append(textRenderInfo.getText());
                        } else {
                            // 有些文档 单字一个块的情况
                            // 拼接字符串
                            sb.append(textRenderInfo.getText());
                            // 去除首部字符串，使长度等于关键字长度
                            if (sb.length() > maxLength) {
                                sb.delete(0, sb.length() - maxLength);
                            }
                        }
                        // 判断是否匹配上
                        if (keyWord.equals(sb.toString())) {

                            // 计算中心点坐标

                            com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
                                    .getBoundingRectange();
                            com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
                                    .getBoundingRectange();

                            float centreX;
                            float centreY;
                            if (isKeywordChunk) {
                                centreX = baseFloat.x + ascentFloat.width / 2;
                                centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
                            } else {
                                centreX = baseFloat.x + ascentFloat.width - (maxLength * ascentFloat.width / 2);
                                centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
                            }

                            float[] resu = new float[4];
                            resu[0] = pageNum;
                            resu[1] = centreX;
                            resu[2] = centreY;
                            resu[3] = height;
                            arrays.add(resu);
                            // 匹配完后 清除
                            sb.delete(0, sb.length());
                        }
                    }


                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                        // nothing
                    }

                    @Override
                    public void endTextBlock() {
                        // nothing
                    }

                    @Override
                    public void beginTextBlock() {
                        // nothing
                    }
                });


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrays;
    }


    private static List<float[]> getKeyWords1(byte[] fileData, final String keyWord) {
        List<float[]> arrays = Lists.newArrayList();
        try {
            PdfReader pdfReader = new PdfReader(fileData);
            int pageSize = pdfReader.getNumberOfPages();
            System.out.println("页数=" + pageSize);
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);


            for (int i = 1; i <= pageSize; i++) {

                int pageNum = i;
                Rectangle pageRect = pdfReader.getPageSize(1);
                float height = pageRect.getHeight();
                float width = pageRect.getWidth();

                pdfReaderContentParser.processContent(pageNum, new RenderListener() {

                    int keyLen = keyWord.length();
                    StringBuilder sb = new StringBuilder("");
                    int maxLength = keyWord.length();



                    List<Integer> lopAll(String text, String key){
                        List<Integer> list = new ArrayList<>();
                        String tmp = text;
                        int klen = key.length();
                        int itLen = 0;
                        while(tmp.contains(key)){
                            int index = tmp.indexOf(key);
                            System.out.println("---> " + index);
                            System.out.println(tmp);
                            if(index<0) break;
                            list.add(index + itLen);// 真实索引
                            tmp = tmp.substring(index + klen);
                            itLen += klen;
                        }
                        if(list.size()>0) return list;
                        return null;
                    }

                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {

                        boolean isKeywordChunk = textRenderInfo.getText().length() == maxLength;
                        if (isKeywordChunk) {
                            // 文档按照 块 读取
                            sb.delete(0, sb.length());
                            sb.append(textRenderInfo.getText());
                        } else {
                            // 有些文档 单字一个块的情况
                            // 拼接字符串
                            sb.append(textRenderInfo.getText());
                            // 去除首部字符串，使长度等于关键字长度
                            if (sb.length() > maxLength) {
                                sb.delete(0, sb.length() - maxLength);
                            }
                        }

                        String text = textRenderInfo.getText();
                        if (null != text && text.contains(keyWord)){

                            com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
                                    .getBoundingRectange();
                            com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
                                    .getBoundingRectange();
                            System.out.println("boseFloat.x point = " + baseFloat.x);
                            System.out.println("boseFloat.y point = " + baseFloat.y);
                            System.out.println("boseFloat.width = " + baseFloat.width);

                            System.out.println("ascentFloat.x point = " + ascentFloat.x);
                            System.out.println("ascentFloat.y point = " + ascentFloat.y);
                            System.out.println("ascentFloat.width = " + ascentFloat.width);


                            List<Integer> indexs = lopAll(text, keyWord);
                            if(null == indexs) return;

                            float centreX;
                            float centreY;

                            for (int j = 0; j < indexs.size(); j++) {
                                int index = indexs.get(j);
                                System.out.println("索引位：" + index);
                                centreX = baseFloat.x  + ascentFloat.width *  (index + keyLen/2)/ text.length();
                                centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);

                                float[] resu = new float[4];
                                resu[0] = pageNum;
                                resu[1] = centreX;
                                resu[2] = centreY;
                                resu[3] = height;
                                arrays.add(resu);
                            }

                        }else if(keyWord.equals(sb.toString())) {

                            // 计算中心点坐标

                            com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
                                    .getBoundingRectange();
                            com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
                                    .getBoundingRectange();

                            float centreX;
                            float centreY;
                            if (isKeywordChunk) {
                                centreX = baseFloat.x + ascentFloat.width / 2;
                                centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
                            } else {
                                centreX = baseFloat.x + ascentFloat.width - (maxLength * ascentFloat.width / 2);
                                centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);
                            }

                            float[] resu = new float[4];
                            resu[0] = pageNum;
                            resu[1] = centreX;
                            resu[2] = centreY;
                            resu[3] = height;
                            arrays.add(resu);
                            // 匹配完后 清除
                            sb.delete(0, sb.length());
                        }


                    }


                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                        // nothing
                    }

                    @Override
                    public void endTextBlock() {
                        // nothing
                    }

                    @Override
                    public void beginTextBlock() {
                        // nothing
                    }
                });


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrays;
    }





    public byte[] testSignByEcc(float[] xyPoint, byte[] pdfData, String reason, String location, Image signImage) throws Exception {



        PdfReader pdfReader = new PdfReader(pdfData);
        AcroFields acroFields = pdfReader.getAcroFields();
        String sigName = "中文域名";
        AcroFields.Item sig1 = acroFields.getFields().get(sigName);
        PdfArray value = (PdfArray)sig1.getValue(0).get(PdfName.RECT);
        long[] rect = value.asLongArray();
        System.out.println(Arrays.toString(rect));

        Rectangle tmpRectangle = new Rectangle(rect[0], rect[1], rect[2], rect[3] );
        Integer pageNo = sig1.getPage(0);


        //


        ByteArrayOutputStream out= null;
        PdfReader reader=null;
        PdfStamper pdfStamper1=null;
        try {
            out = new ByteArrayOutputStream();
            reader=new PdfReader(pdfData);
            pdfStamper1=new PdfStamper(reader,out);
            AcroFields s = pdfStamper1.getAcroFields();
            s.removeField(sigName);
            pdfStamper1.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        pdfData = out.toByteArray();
        pdfReader = new PdfReader(pdfData);


        Security.addProvider(new BouncyCastleProvider());

        // itext pdf
//        PdfReader pdfReader = new PdfReader(pdfData);
        // 字节流
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        // 创建签章工具
        PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);
        // 获取数字签章属性对象，设定数字签章的属性
        PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();

/*

        // 获取操作的页面
        PdfContentByte under = pdfStamper.getOverContent(1);
        // 根据域的大小缩放图片
        signImage.scaleToFit(tmpRectangle.getWidth(), tmpRectangle.getHeight());
        System.out.printf("域的宽高\t%f %f\n",tmpRectangle.getWidth(), tmpRectangle.getHeight());
        // 添加图片
        signImage.setAbsolutePosition(rect[0], rect[1]);
        under.addImage(signImage);
        under.stroke();
*/

        // 设置签章原因
        appearance.setReason(reason);
        // 设置签章地点
        appearance.setLocation(location);



        // 设置签章级别
        appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
        // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
        // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
        appearance.setVisibleSignature(tmpRectangle, pageNo,  sigName);
//                getRectangle( xyPoint[1], xyPoint[2], 100), (int)xyPoint[0], null);

        // 设置签章图片
        appearance.setSignatureGraphic(signImage);

        // 签章算法 可以自己实现
        // 摘要算法
//        ExternalDigest digest = new BouncyCastleDigest();
        // 签章算法
//        ExternalSignature signature = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA1, null);
        // 进行盖章操作 CMS高级电子签名(CAdES)的长效签名规范
//        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
//        digest.getMessageDigest("SM3withSM2");


        PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, MakeSignature.CryptoStandard.CMS == MakeSignature.CryptoStandard.CADES ? PdfName.ETSI_CADES_DETACHED : PdfName.ADBE_PKCS7_DETACHED);
        dic.setReason(appearance.getReason());
        dic.setLocation(appearance.getLocation());
        dic.setSignatureCreator(appearance.getSignatureCreator());
        dic.setContact(appearance.getContact());
        dic.setDate(new PdfDate(appearance.getSignDate())); // time-stamp will over-rule this
        dic.put(PdfName.ID, new PdfString("1234567890abcdef"));
        dic.put(new PdfName("sealCode"), new PdfString("sealCode-11010100000001"));

        appearance.setCryptoDictionary(dic);


        String oP7bString = "MIIGpQYJKoZIhvcNAQcCoIIGljCCBpICAQExDDAKBggqgRzPVQGDETCCAk4GCSqGSIb3DQEHAaCCAj8EggI7eyJidXNpbmVzc0NlbnRlcklkIjoxLCJjZXJ0Ijp7ImNlcnRTTiI6IjIwMGZiODNmZmU3ODBjZDA3ZGI3N2Y0MmU0ZDJjYWExZjE5MWM4NWEiLCJpc3N1ZXIiOiIzMDYyMzEwQjMwMDkwNjAzNTUwNDA2MTMwMjQzNEUzMTI0MzAyMjA2MDM1NTA0MEEwQzFCNEU0NTU0NDM0MTIwNDM2NTcyNzQ2OTY2Njk2MzYxNzQ2NTIwNDE3NTc0Njg2RjcyNjk3NDc5MzEyRDMwMkIwNjAzNTUwNDAzMEMyNDRFNDU1NDQzNDEyMDUzNEQzMjIwNTQ0NTUzNTQzMTMxMjA2MTZFNjQyMDQ1NzY2MTZDNzU2MTc0Njk2RjZFMjA0MzQxMzAzMiJ9LCJsaW5rbWFuIjp7ImFkZHJlc3MiOiLlub/kuJznnIEiLCJlbWFpbCI6IjEzNTEyMzQ1Njc4QGNuY2EubmV0IiwiaWRlbnRpdHkiOiI0NDAxMDMxOTkwMDMwNzIxOTgiLCJpZGVudGl0eVR5cGUiOjIsIm5hbWUiOiLlvKDkuIkiLCJwaG9uZSI6IjEzNTEyMzQ1Njc4In0sIm91dFJlcUlkIjoiMjAxODA3MjUwMDEiLCJwcm9qZWN0SWQiOiJQMDAwMTIwMTYwODExMDAxIiwic3lzdGVtSWQiOiJuYXRpb25hbFNlYWxTZXJ2aWNlIiwidGVtcGxhdGVJZCI6IlQwNTAxMDEyMDE5MDMwNzAwMDIifaCCA08wggNLMIIC8KADAgECAgsQzGafH7D6m/A93zAKBggqgRzPVQGDdTBiMQswCQYDVQQGEwJDTjEkMCIGA1UECgwbTkVUQ0EgQ2VydGlmaWNhdGUgQXV0aG9yaXR5MS0wKwYDVQQDDCRORVRDQSBTTTIgVEVTVDAxIGFuZCBFdmFsdWF0aW9uIENBMDEwHhcNMTkwMzEzMDIyMzM0WhcNMjIwMzEzMDIyMzM0WjCBhzELMAkGA1UEBhMCQ04xEjAQBgNVBAgMCUd1YW5nZG9uZzEPMA0GA1UEBwwG5bm/5beeMSowKAYDVQQKDCFHT01BSU4gU00yIFRFU1QgYW5kIEV2YWx1YXRpb24gcDcxJzAlBgNVBAMMHkdPTUFJTiBTTTIgVEVTVCBhbmQgRXZhbHVhdGlvbjBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABE1PWIt2iI2ddHhduHoY/TRnQ2AgcN0h2CS45AJ0UtaNkLYznPhuSCeKvXsvwkkJT/Mc1FxZ7c4LIRaa9QX6DtijggFlMIIBYTAfBgNVHSMEGDAWgBQMe+ticwN1+oxKJAz2jzshZX4X6TAdBgNVHQ4EFgQUstyGAmk5/Y6F59mUwvqpDft4nUwwawYDVR0gBGQwYjBgBgorBgEEAYGSSA0KMFIwUAYIKwYBBQUHAgEWRGh0dHA6Ly93d3cuY25jYS5uZXQvY3Mva25vd2xlZGdlL3doaXRlcGFwZXIvY3BzL25ldENBdGVzdGNlcnRjcHMucGRmMDMGA1UdHwQsMCowKKAmoCSGImh0dHA6Ly90ZXN0LmNuY2EubmV0L2NybC9TTTJDQS5jcmwwDAYDVR0TAQH/BAIwADAOBgNVHQ8BAf8EBAMCBLAwNAYKKwYBBAGBkkgBDgQmDCQ1NWZjMGFjODM1MTBmZGUwNjUzNmFkZDM5ZTU2ZTQ5MUBTMDIwKQYDVR0RBCIwIIIeR09NQUlOIFNNMiBURVNUIGFuZCBFdmFsdWF0aW9uMAoGCCqBHM9VAYN1A0kAMEYCIQDD+GWWvJURtPsuHeMRqzfUtWqtGnglWGhYl1AdldJyAgIhAKGoyZBghCfhlF01/AzmoFr4T9fXJ4/pxl30QMtVUrj1MYHZMIHWAgEBMHEwYjELMAkGA1UEBhMCQ04xJDAiBgNVBAoMG05FVENBIENlcnRpZmljYXRlIEF1dGhvcml0eTEtMCsGA1UEAwwkTkVUQ0EgU00yIFRFU1QwMSBhbmQgRXZhbHVhdGlvbiBDQTAxAgsQzGafH7D6m/A93zAKBggqgRzPVQGDETAKBggqgRzPVQGDdQRGMEQCIFbEEAuMK0PWdsXErikPrwIb0TODlbEhzmL5HYVdxPj8AiAVF+iAkrpNhk0SRI1XsoPgepDtEKbO++5BPHKWdBMDoQ==";

        byte[] sigData = Base64.getDecoder().decode(oP7bString);

        sigData = FileUtil.fromDATfile("C:\\Users\\49762\\Desktop\\ORG-3da24b771baf4778a3b97f5aba1ce808.dat");

        int sigDataLen = sigData.length;
        sigDataLen = 64;
        HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
        exc.put(PdfName.CONTENTS, new Integer(sigDataLen*2  + 2));
        appearance.preClose(exc);


//        String hashAlgorithm = "SHA-256";
//        PdfPKCS7 sgn = new PdfPKCS7(null, chain, hashAlgorithm, null, digest, false);
        InputStream data = appearance.getRangeStream();

        System.out.println("data.ava = " + data.available());
//        byte hash[] = DigestAlgorithms.digest(data, digest.getMessageDigest(hashAlgorithm));

        byte[] hash = digest(data);

        System.out.println("hash value = " + Util.byteToHex(hash));

        // 签名
        String hpk = "04B869DBB74ACDF04925F711148B553F1392B36786628B3F05BEBEF04E0F37DFBED3BEB89128445B00B3CA3B5A4FF6CDBF3703FA74F1810547F5D567F7538E8B43";
        String hsk = "675160FFEAF0CAB760BD12C39F79CA6499238D76D2C845DF6A77AD69F3D1A4F1";
        GMTSM2 sm2 = GMTSM2.getInstance();

        sigData = sm2.sm2Sign(hash, Util.hexToByte(hsk));



        PdfDictionary dic2 = new PdfDictionary();
        dic2.put(PdfName.CONTENTS, new PdfString(sigData).setHexWriting(true));
        appearance.close(dic2);
        // 写入输出流中
        return result.toByteArray();
    }


    public static byte[] digest(InputStream data ) throws IOException {
        byte buf[] = new byte[8192];
        int n;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (-1 != (n = data.read(buf))) {
            outputStream.write(buf, 0, n);
        }
        byte[] originData = outputStream.toByteArray();
        System.out.println("data.total.length = " + originData.length);

        return SM3Util.sm3Digest(originData);
    }



    private static ByteArrayOutputStream getImage(String content) throws IOException {
        /*
         * Because font metrics is based on a graphics context, we need to create a
         * small, temporary image so we can ascertain the width and height of the final
         * image
         */
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("宋体", Font.PLAIN, 50);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(content);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.RED);
        g2d.drawString(content, 0, fm.getAscent());
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        return baos;
    }

    Rectangle getRectangle(float x, float y, float height) {
        //自己可以调整图片的大小
        float halfWith = height/ 2;
        float halfHeight = height/ 2;

        return new Rectangle(x - halfWith, y - halfHeight, x + halfWith, y + halfHeight);

    }

}
