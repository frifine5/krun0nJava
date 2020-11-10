package com.adb.itext.t;

import com.common.utils.FileUtil;
import com.common.utils.ParamsUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.io.RASInputStream;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.*;

public class TestPdfSIgnDoc {





    @Test
    public void testSignPocOnFields()throws Exception {
        long st = System.currentTimeMillis();
        String pdfDir = "/home/dtmp/itextPdfs/";
        String pdfName = "src1.pdf";
        pdfName = "src2.pdf";
        pdfName = "src3.pdf";

        String pdf = pdfDir + pdfName;
        String destName = "dest3-3.pdf";

        String psImg = "person.png";
        String orgImg = "orgStamp.png";

        String[] fields =
//                {"srfsqrqm", "crfsqrqm", "srfdlrqm", "crfdlrqm" };
                { "fieldsig1", "fieldSig2"};

        byte[] imageData = FileUtil.fromDATfile(pdfDir + orgImg);

        byte[] data = findFieldAndSig2(pdf, fields[1], imageData);

        System.out.println("耗时 ： " + (System.currentTimeMillis() - st));

        FileUtil.writeInFiles(pdfDir + destName, data);


    }

    byte[]  findFieldAndSig(String pdf, String field, byte[] imgData) throws Exception {
        PdfReader pdfReader = new PdfReader(pdf);
        AcroFields acreFileds = pdfReader.getAcroFields();
        Map<String, AcroFields.Item> fields = acreFileds.getFields();
        AcroFields.Item item = fields.get(field);
        if (null == item) {
            throw new RuntimeException("文件中未找到指定签名域[" + field + "]");
        }
        if (item.size() != 1) {
            throw new RuntimeException("文件中指定签名域[" + field + "]超过1，不符合一般PDF签名域的规范");
        }
        // -------------- 读域 ----------
        PdfDictionary itmVlu = item.getValue(0);
        PdfArray sigNameRect = (PdfArray) itmVlu.get(PdfName.RECT);
        long[] sigRect = sigNameRect.asLongArray();
        int pageNo = item.getPage(0);
        System.out.println(Arrays.toString(sigRect));
        System.out.println("坐标：" + sigNameRect);
        System.out.println("页码：" + item.getPage(0));

        System.out.println(itmVlu);
        System.out.println(itmVlu.get(PdfName.FT));
        System.out.println(itmVlu.get(PdfName.T));
        System.out.println("page = " + item.getPage(0));
        System.out.println();


        // start to sig on it

        byte[] pdfData = null;

        ByteArrayOutputStream out= null;
        PdfReader reader=null;
        PdfStamper pdfStamper1=null;
        try {
            out = new ByteArrayOutputStream();
            reader=new PdfReader(pdf);

            PdfDictionary sig0 = reader.getAcroFields().getSignatureDictionary(field);
            System.out.println(sig0);
            if(null != sig0){
                System.out.println("签名域["+ field+"]已签名");
                boolean isLastSign = reader.getAcroFields().signatureCoversWholeDocument(field);
                System.out.println("签名域["+ field+"]是否覆盖全文: " + isLastSign);
            }

            pdfStamper1=new PdfStamper(reader,out);
            AcroFields s = pdfStamper1.getAcroFields();
            s.removeField(field);
            pdfStamper1.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        pdfData = out.toByteArray();

        PdfReader pdfReader2 = new PdfReader(pdfData);

        Security.addProvider(new BouncyCastleProvider());


        // 字节流
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        // 创建签章工具
        PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader2, result, '\0', null, true);
        // 获取数字签章属性对象，设定数字签章的属性
        PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();


//        // 设置签章原因
//        appearance.setReason(reason);
//        // 设置签章地点
//        appearance.setLocation(location);


        // 设置签章级别
        appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
        // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
        // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
        appearance.setVisibleSignature(
//                tmpRectangle, pageNo,  field);
        new Rectangle(sigRect[0], sigRect[1], sigRect[2], sigRect[3] ), pageNo,  field);



        Image signImage = Image.getInstance(imgData);

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


        return result.toByteArray();
    }


    byte[]  findFieldAndSig2(String pdf, String field, byte[] imgData) throws Exception {
        PdfReader pdfReader = new PdfReader(pdf);
        AcroFields acreFileds = pdfReader.getAcroFields();
        Map<String, AcroFields.Item> fields = acreFileds.getFields();
        AcroFields.Item item = fields.get(field);
        if (null == item) {
            throw new RuntimeException("文件中未找到指定签名域[" + field + "]");
        }
        if (item.size() != 1) {
            throw new RuntimeException("文件中指定签名域[" + field + "]超过1，不符合一般PDF签名域的规范");
        }
        PdfDictionary isSigned = acreFileds.getSignatureDictionary(field);
        if(null != isSigned){
            throw new RuntimeException("文件中指定签名域[" + field + "]已经签过电子数据，不得在同一域再次签署");
        }

        // -------------- 读域 ----------
        PdfDictionary itmVlu = item.getValue(0);
        PdfArray sigNameRect = (PdfArray) itmVlu.get(PdfName.RECT);
        System.out.println("---- sigRect ----");
        System.out.println(sigNameRect);


        long[] sigRect = sigNameRect.asLongArray();
        int pageNo = item.getPage(0);
        long width = Math.abs(sigRect[0] - sigRect[2]);
        long height = Math.abs(sigRect[1] - sigRect[3]);

        System.out.println(Arrays.toString(sigRect));
        System.out.println("坐标：" + sigNameRect);
        System.out.println("页码：" + pageNo);
        // >>> 是否转换图片形状



        Security.addProvider(new BouncyCastleProvider());

        // 字节流
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        // 创建签章工具
        PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);
        // 获取数字签章属性对象，设定数字签章的属性
        PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();


//        // 设置签章原因
//        appearance.setReason(reason);
//        // 设置签章地点
//        appearance.setLocation(location);


        // 设置签章级别
        appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
        // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
        // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
        appearance.setVisibleSignature( field); // 加入到已有的签名域中




        // 调整图片大小
        InputStream ss = new ByteArrayInputStream(imgData);
        BufferedImage bi = ImageIO.read(ss);
        BufferedImage nbi = resizeImage(bi, (float)width/(float)height);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
            ImageIO.write(nbi, "png", os);
            imgData =  os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("图片形状调整失败", e);
        }
        Image signImage = Image.getInstance(imgData);

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




    @Test
    public void testGetDigest()throws Exception{

        String pdf = "/home/dtmp/itextPdfs/dest-2-4.pdf";

        PdfReader pdfReader = new PdfReader(pdf);
        AcroFields acreFileds = pdfReader.getAcroFields();
        ArrayList<String> sigNames = acreFileds.getSignatureNames();
        for(String sigName : sigNames){

            PdfDictionary sigDict = acreFileds.getSignatureDictionary(sigName);
            PdfArray bytRag = (PdfArray) sigDict.get(PdfName.BYTERANGE);
            try {
                RandomAccessFileOrArray randomAccessFileOrArray = pdfReader.getSafeFile();
                InputStream rg = new RASInputStream(new RandomAccessSourceFactory()
                        .createRanged(randomAccessFileOrArray.createSourceView(), bytRag.asLongArray() ));

                byte buf[] = new byte[8192];
                int n;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while (-1 != (n = rg.read(buf))) {
                    outputStream.write(buf, 0, n);
                }
                byte[] originData = outputStream.toByteArray();

                byte[] md = SM3Util.sm3Digest(originData);
                System.out.printf("域名: %s,\t摘要值:\n%s\n", sigName, Util.byteToHex(md));


            }catch (Exception e){
                e.printStackTrace();
            }




        }


        PdfDictionary pageCont = pdfReader.getPageNRelease(1);
        PdfArray pageMBox = (PdfArray)pageCont.get(PdfName.MEDIABOX);

        long[] rect = pageMBox.asLongArray();
        long width = Math.abs(rect[0] - rect[2]);
        long height = Math.abs(rect[1] - rect[3]);
        System.out.printf("\n宽：%s, 高：%s\n", width, height);

        System.out.println();




    }




    public static BufferedImage resizeImage(BufferedImage bi, float wh)  {

        float width = bi.getWidth(); // 像素
        float height = bi.getHeight(); // 像素
        float nHeight = height / wh;
        BufferedImage buffImg = null;
        System.out.println("wh = " + wh);
        System.out.println("高:" + nHeight);
        System.out.println("宽:" + width);
        buffImg = new BufferedImage((int)width, (int)nHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //使用TYPE_INT_RGB修改的图片会变色
        buffImg.getGraphics().drawImage(
                bi.getScaledInstance((int)width, (int)nHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return buffImg;
    }


    @Test
    public void testGetSigContent()throws Exception{

        String pdf = "/home/dtmp/itextPdfs/dest-2-4.pdf";

        PdfReader pdfReader = new PdfReader(pdf);
        AcroFields acreFileds = pdfReader.getAcroFields();
        ArrayList<String> sigNames = acreFileds.getSignatureNames();
        for(String sigName : sigNames){
            PdfDictionary sigDict = acreFileds.getSignatureDictionary(sigName);

            PdfObject sigCont = sigDict.get(PdfName.CONTENTS);
            byte[] ctx = sigCont.getBytes();

            System.out.println(sigName + ": contents length = " + ctx.length);

            System.out.println(Util.byteToHex(ctx));

            System.out.println(Base64.getEncoder().encodeToString(ctx));

            byte[] lb1 = new byte[4];
            lb1[0] = ctx[3];
            lb1[1] = ctx[2];
            int i1 = Util.byteToInt(lb1);

            int len = i1 + 4;

            System.out.println("asn 解析得长度 = " + len);
            byte[] asn = new byte[len];
            System.arraycopy(ctx, 0, asn, 0, len);
            System.out.println(Base64.getEncoder().encodeToString(asn));


            System.out.println(sigDict.get(new PdfName("sealName")));


            System.out.println();
            System.out.println();
        }


        System.out.println();


    }






    @Test
    public void testAddTimeIsBroken() throws Exception{

        String dir = "C:\\Users\\49762\\Desktop\\";
        String pdfName = "sign-1.pdf";


        String pdf = dir + pdfName;
        PdfReader  pdfReader = new PdfReader(pdf);

        Security.addProvider(new BouncyCastleProvider());
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);

        String signTime = ParamsUtil.formatTime19(new Date());
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        PdfContentByte over = pdfStamper.getOverContent(1);
        over.beginText();
        over.setFontAndSize(bfChinese, 10);
        over.setColorFill(BaseColor.RED);
        over.setTextMatrix(100, 400);
        over.showText(signTime);
        over.endText();

        pdfStamper.close();

        byte[] finData = result.toByteArray();

        FileUtil.writeInFiles(dir + "dest-1.pdf", finData);


    }

}
