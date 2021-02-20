package com.itext;

import com.common.FileUtil;
import com.common.ParamsUtil;
import com.common.PsaImageUtil;
import com.common.SignSealUtil;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.ImgTemplate;
import com.itextpdf.text.pdf.*;
import com.pdfsign.PdfItextSmSign;
import com.smalg.sm2.GMTSM2;
import com.smalg.sm3.SM3Util;
import com.smalg.sm3.Util;
import org.apache.batik.bridge.*;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.w3c.dom.svg.SVGDocument;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.*;

public class TestSign {


    @Test
    public void testSign() throws Exception{
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        String certStr = "MIIDSzCCAvCgAwIBAgILEMxmnx+w+pvwPd8wCgYIKoEcz1UBg3UwYjELMAkGA1UEBhMCQ04xJDAiBgNVBAoMG05FVENBIENlcnRpZmljYXRlIEF1dGhvcml0eTEtMCsGA1UEAwwkTkVUQ0EgU00yIFRFU1QwMSBhbmQgRXZhbHVhdGlvbiBDQTAxMB4XDTE5MDMxMzAyMjMzNFoXDTIyMDMxMzAyMjMzNFowgYcxCzAJBgNVBAYTAkNOMRIwEAYDVQQIDAlHdWFuZ2RvbmcxDzANBgNVBAcMBuW5v+W3njEqMCgGA1UECgwhR09NQUlOIFNNMiBURVNUIGFuZCBFdmFsdWF0aW9uIHA3MScwJQYDVQQDDB5HT01BSU4gU00yIFRFU1QgYW5kIEV2YWx1YXRpb24wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARNT1iLdoiNnXR4Xbh6GP00Z0NgIHDdIdgkuOQCdFLWjZC2M5z4bkgnir17L8JJCU/zHNRcWe3OCyEWmvUF+g7Yo4IBZTCCAWEwHwYDVR0jBBgwFoAUDHvrYnMDdfqMSiQM9o87IWV+F+kwHQYDVR0OBBYEFLLchgJpOf2OhefZlML6qQ37eJ1MMGsGA1UdIARkMGIwYAYKKwYBBAGBkkgNCjBSMFAGCCsGAQUFBwIBFkRodHRwOi8vd3d3LmNuY2EubmV0L2NzL2tub3dsZWRnZS93aGl0ZXBhcGVyL2Nwcy9uZXRDQXRlc3RjZXJ0Y3BzLnBkZjAzBgNVHR8ELDAqMCigJqAkhiJodHRwOi8vdGVzdC5jbmNhLm5ldC9jcmwvU00yQ0EuY3JsMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgSwMDQGCisGAQQBgZJIAQ4EJgwkNTVmYzBhYzgzNTEwZmRlMDY1MzZhZGQzOWU1NmU0OTFAUzAyMCkGA1UdEQQiMCCCHkdPTUFJTiBTTTIgVEVTVCBhbmQgRXZhbHVhdGlvbjAKBggqgRzPVQGDdQNJADBGAiEAw/hllryVEbT7Lh3jEas31LVqrRp4JVhoWJdQHZXScgICIQChqMmQYIQn4ZRdNfwM5qBa+E/X1yeP6cZd9EDLVVK49Q==";
        String sealDataStr = "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";

        String pdfDir = "/home/dtmp/itextPdfs/";
        String pdfName = "src2.pdf";
        String destName = "dest-2-f-03.pdf";

//        pdfName = "dest-2-f-22.pdf";

        pdfDir = "/home/dtmp/itextPdfs/addfieldtest/";
        pdfName = "file1.pdf";
        destName = "signed20200514-01.pdf";



        String pdf = pdfDir + pdfName;

        String psImg = "person.png";
        String orgImg = "orgStamp.png";

        String[] fields =
                {"srfsqrqm", "crfsqrqm", "srfdlrqm", "crfdlrqm" };
//                {"xm1" };
//                {"gdqm1", "gdqm2", "gdqm3", "gdqm4", "gdqm5", "gdqm6", "gdqm7"};
//                {"fieldsig1", "fieldsig2", "中文签名域1"};


        int[] pages = new int[1];

        byte[] pdfData = FileUtil.fromDATfile(pdf);

        byte[] imageData = FileUtil.fromDATfile("/home/dtmp/itextPdfs/orgStamp.png");//pdfDir + orgImg);

        byte[] sealData = Base64.getDecoder().decode(sealDataStr);

        PdfItextSmSign sign = new PdfItextSmSign();

/*

        byte[] destData = sign.ecPositionSignPdf(pdfData, imageData, 270.58f, 358.76428f, 80, 60, 1,
                sealData, "123", certStr, new Date(), pdfName);
*/

        String field = "s1";
//        pdfData = sign.writeFieldEmpty(pdfData, field, true, 2, 100, 600, 80, 70);

        byte[] destData = sign.ecFieldSignPdf(pdfData, imageData, field, true, sealData, "123", certStr, new Date(), pages, pdfName);


        FileUtil.writeInFiles(pdfDir + destName, destData);


    }


}
