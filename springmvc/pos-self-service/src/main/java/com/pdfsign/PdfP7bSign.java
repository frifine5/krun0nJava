package com.pdfsign;

import com.common.FileUtil;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.List;

public class PdfP7bSign {

    public static void main(String[] args) throws Exception {
        /*
        byte[] data = FileUtil.fromDATfile("D:\\home\\rsa1.jks");
        Image signImage = Image.getInstance("D:\\home\\orgStamp2.png");
        String pdf = "C:\\Users\\49762\\Desktop\\授权书.pdf";
        byte[] oriData = FileUtil.fromDATfile(pdf);
        float[] xy = {150, 200, 230, 300, 1};

        byte[] outData = new PdfP7bSign().p7bSign(oriData, signImage, data, "签名原因", "签名地点", xy);
        FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\授权书p7b签名.pdf", outData);

*/
        // 验签
        byte[] outData = FileUtil.fromDATfile("C:\\Users\\49762\\Desktop\\授权书p7b签名.pdf");
        new PdfP7bSign().verifyRsaP7b(outData);

    }


    /**
     * rsa p7b sign of pdf
     */
    public byte[] p7bSign(byte[] pdfData, Image signImage, byte[] keyStoreData,
                          String reason, String location, float[] xyPoint) throws Exception {
        int page = (int) xyPoint[4];
        Rectangle rect = new Rectangle(xyPoint[0], xyPoint[1], xyPoint[2], xyPoint[3]);
        PdfReader pdfReader = new PdfReader(pdfData);

        ByteArrayInputStream byteKeyStorm = new ByteArrayInputStream(keyStoreData);
        Security.addProvider(new BouncyCastleProvider());
        // 读取keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = "123456".toCharArray();
        keyStore.load(byteKeyStorm, password);
        // 读取别名
        String alias = keyStore.aliases().nextElement();
        // 获得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
        // 获得证书链
        Certificate[] chain = keyStore.getCertificateChain(alias);

        // 字节流
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        // 创建签章工具
        PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);
        // 获取数字签章属性对象，设定数字签章的属性
        PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();
        // 设置签章原因
        appearance.setReason(reason);
        // 设置签章地点
        appearance.setLocation(location);
        // 设置签章图片
        signImage.scaleToFit(rect.getWidth(), rect.getHeight());
        appearance.setSignatureGraphic(signImage);
        // 设置签章级别
        appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
        // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
        // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
        appearance.setVisibleSignature(rect, page, null);

        // 签章算法 可以自己实现
        // 摘要算法
        ExternalDigest digest = new BouncyCastleDigest();
        // 签章算法
        ExternalSignature signature = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA1, null);
        // 进行盖章操作 CMS高级电子签名(CAdES)的长效签名规范
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

        // 写入输出流中
        return result.toByteArray();
    }


    public boolean verifyRsaP7b(byte[] pdfData) throws Exception {
        BouncyCastleProvider bcp = new BouncyCastleProvider();
        Security.insertProviderAt(bcp, 1);

        PdfReader reader = new PdfReader(pdfData);
        AcroFields acroFields = reader.getAcroFields();
        List<String> names = acroFields.getSignatureNames();
        for (String name : names) {
            System.out.println("Signature name: " + name);
            System.out.println("Signature covers whole document: " + acroFields.signatureCoversWholeDocument(name));
            PdfPKCS7 pk = acroFields.verifySignature(name);
            System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
            System.out.println("Document verifies: " + pk.verify());
            System.out.println();

            PdfDictionary v = acroFields.getSignatureDictionary(name);

            if (v == null) {
            } else {
                PdfString contents = v.getAsString(PdfName.CONTENTS);// 读取p7b数据，但又大量的空值尾巴
                FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\"+ name + ".p7b", contents.getBytes());
            }


        }

        return false;
    }

}
