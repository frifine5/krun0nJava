package com.pdfsign;


import com.common.ParamsUtil;
import com.common.PsaImageUtil;
import com.common.SignSealUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.smalg.sm2.GMTSM2;
import com.smalg.sm3.SM3Util;
import com.smalg.sm3.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.*;

@Service
public class PdfItextSmSign {


    public byte[] ecPositionSignPdf(byte[] pdfBytes, byte[] imgBytes, float px, float py, float width, float height, int pageNo,
                                    byte[] sealStructData, String busiNo, String certBase64, Date toSignDate,
                                    String fileCode){
        if(ParamsUtil.checkNull(busiNo, certBase64, fileCode)){
            throw new RuntimeException("参数错误[str]");
        }
        if(width< 0 || height< 0 || pageNo<1){
            throw new RuntimeException("参数错误[whp]");
        }
        int sealSigValuePreLength = (int)(sealStructData.length * 1.5 + 1024);
        PdfReader pdfReader = null;
        try{
            pdfReader = new PdfReader(pdfBytes);
            int totalPages = pdfReader.getNumberOfPages();
            if(pageNo > totalPages){
                throw new RuntimeException("参数错误:页码超出范围");
            }
            Security.addProvider(new BouncyCastleProvider());
            // 字节流
            ByteArrayOutputStream result = new ByteArrayOutputStream();

            // 创建签章工具
            PdfStamper pdfStamper = PdfStamper.createSignature(pdfReader, result, '\0', null, true);
            // 获取数字签章属性对象，设定数字签章的属性
            PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();

            appearance.setReason("document protected");
            appearance.setLocation("GoMain");

            // 设置签章级别
            appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            // 设置签章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            // 设置签章位置 图章左下角x，原点为pdf页面左下角，图章左下角y，图章右上角x，图章右上角y
            appearance.setVisibleSignature(new Rectangle(px, py, px + width, py + height), pageNo,  null);


            // 调整图片大小
            InputStream ss = new ByteArrayInputStream(imgBytes);
            BufferedImage bi = ImageIO.read(ss);
            BufferedImage nbi = PsaImageUtil.resizeImage(bi, width/height);

            try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
                ImageIO.write(nbi, "png", os);
                imgBytes =  os.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("图片形状调整失败", e);
            }

            Image signImage = Image.getInstance(imgBytes);
            // 设置签章图片
            Image zeroImage = Image.getInstance(Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNgAAIAAAUAAen63NgAAAAASUVORK5CYII="));
            appearance.setSignatureGraphic(zeroImage);
            appearance.setImage(signImage);

            // 签名属性预置
            PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
            dic.setReason(appearance.getReason());
            dic.setLocation(appearance.getLocation());
            dic.setSignatureCreator(appearance.getSignatureCreator());
            dic.setContact(appearance.getContact());
            dic.setDate(new PdfDate(appearance.getSignDate()));

            dic.put(PdfName.ID, new PdfString("1234567890abcdef"));
            dic.put(new PdfName("sealCode"), new PdfString("sealCode-11010100000001"));
            dic.put(new PdfName("sealName"), new PdfString("测试章").setHexWriting(true));

            appearance.setCryptoDictionary(dic);


            int sigDataLen = sealSigValuePreLength * 2 + 2;

            HashMap<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.CONTENTS, new Integer(sigDataLen));
            appearance.preClose(exc);

            InputStream data = appearance.getRangeStream();

            byte[] hash = SM3Util.sm3Digest(ParamsUtil.getRangeData(data));

            // 组装toSign结构体
            byte[] toSign;
            try {
                toSign = SignSealUtil.combineToSignStruct(sealStructData, toSignDate, hash, "foxit pdf doc" );
            } catch (IOException e) {
                throw new RuntimeException("签章失败：结构生成失败", e);
            }

            // 签名
            String hpk = "044D4F588B76888D9D74785DB87A18FD346743602070DD21D824B8E4027452D68D90B6339CF86E48278ABD7B2FC249094FF31CD45C59EDCE0B21169AF505FA0ED8";
            String hsk = "5A325ABF49F554EF1508F11D4CA8513288600B144C01D7B9B7F8EB3425C2B41B";

            GMTSM2 sm2 = GMTSM2.getInstance();

            byte[] sv = sm2.sm2Sign(SM3Util.sm3Digest(toSign, Util.hexToByte(hpk)), Util.hexToByte(hsk));
            byte[] sigData = null;
            try {
                sigData = SignSealUtil.makeSeal(sv, Base64.getDecoder().decode(certBase64), toSign);
            } catch (Exception e) {
                throw new RuntimeException("签章结构体生成失败", e);
            }


            PdfDictionary dic2 = new PdfDictionary();
            dic2.put(PdfName.CONTENTS, new PdfString(sigData).setHexWriting(true));
            appearance.close(dic2);

            return result.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("盖章失败", e);
        }finally {
            if(null != pdfReader){
                pdfReader.close();
            }
        }

    }



    public byte[] ecFieldSignPdf(byte[] pdfBytes, byte[] imgBytes, String field, boolean adapt,
                                    byte[] sealStructData, String busiNo, String certBase64, Date toSignDate, int[] page,
                                    String fileCode){
        if(ParamsUtil.checkNull(busiNo, certBase64, fileCode, field)){
            throw new RuntimeException("参数错误[str]");
        }

        int sealSigValuePreLength = (int)(sealStructData.length * 1.5 + 1024);
        PdfReader pdfReader = null;
        try{
            pdfReader = new PdfReader(pdfBytes);
            int totalPages = pdfReader.getNumberOfPages();

            AcroFields acreFileds = pdfReader.getAcroFields();
            Map<String, AcroFields.Item> fields = acreFileds.getFields();
            AcroFields.Item item = fields.get(field);
            if (null == item) {
                throw new RuntimeException("文件中未找到指定签名域[" + field + "]");
            }
            PdfDictionary merged = item.getMerged(0);
            if (!PdfName.SIG.equals(PdfReader.getPdfObject(merged.get(PdfName.FT)))){
                throw new RuntimeException("指定域名[" + field + "]不是签名域的域名");
            }
            if (item.size() != 1) {
                throw new RuntimeException("文件中指定签名域[" + field + "]超过1，不符合一般PDF签名域的规范");
            }
            PdfDictionary isSigned = acreFileds.getSignatureDictionary(field);
            if(null != isSigned){
                throw new RuntimeException("文件中指定签名域[" + field + "]已经签过电子数据，不得在同一域再次签署");
            }


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
            appearance.setVisibleSignature(field);



            // -------------- 读域 ----------
            PdfDictionary itmVlu = item.getValue(0);
            PdfArray sigNameRect = (PdfArray) itmVlu.get(PdfName.RECT);
            long[] sigRect = sigNameRect.asLongArray();
            int pageNo = item.getPage(0);
            page[0] = pageNo;
            long width = Math.abs(sigRect[0] - sigRect[2]);
            long height = Math.abs(sigRect[1] - sigRect[3]);

            // 设置签名时间
            Calendar signDate = appearance.getSignDate();
            String signTime = ParamsUtil.formatTime19(signDate.getTime());

            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            PdfContentByte over = pdfStamper.getOverContent(pageNo);
            over.beginText();
            over.setFontAndSize(bfChinese, 10);
            over.setColorFill(BaseColor.RED);
            over.setTextMatrix(sigRect[0], sigRect[1]-16);
            over.showText(signTime);
            over.endText();


            // 调整图片大小
            InputStream ss = new ByteArrayInputStream(imgBytes);
            BufferedImage bi = ImageIO.read(ss);
            BufferedImage nbi = PsaImageUtil.resizeImage(bi, (width/(float)height));

            try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ){
                ImageIO.write(nbi, "png", os);
                imgBytes =  os.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("图片形状调整失败", e);
            }

            Image signImage = Image.getInstance(imgBytes);
            // 设置签章图片


            Image zeroImage = Image.getInstance(Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVR42mNgAAIAAAUAAen63NgAAAAASUVORK5CYII="));


            appearance.setSignatureGraphic(zeroImage);
            appearance.setImage(signImage);
//            appearance.setImageScale(1.2f);

            // 签名属性预置
            PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
            dic.setReason(appearance.getReason());
            dic.setLocation(appearance.getLocation());
            dic.setSignatureCreator(appearance.getSignatureCreator());
            dic.setContact(appearance.getContact());
            dic.setDate(new PdfDate(signDate));

            dic.put(PdfName.ID, new PdfString("1234567890abcdef"));
            dic.put(new PdfName("sealCode"), new PdfString("sealCode-11010100000001"));
            dic.put(new PdfName("sealName"), new PdfString(Base64.getEncoder().encodeToString("测试章".getBytes())));

            appearance.setCryptoDictionary(dic);


            int sigDataLen = sealSigValuePreLength * 2 + 2;

            HashMap<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.CONTENTS, new Integer(sigDataLen));
            appearance.preClose(exc);

            InputStream data = appearance.getRangeStream();

            byte[] hash = SM3Util.sm3Digest(ParamsUtil.getRangeData(data));

            // 组装toSign结构体
            byte[] toSign;
            try {
                toSign = SignSealUtil.combineToSignStruct(sealStructData, toSignDate, hash, "foxit pdf doc" );
            } catch (IOException e) {
                throw new RuntimeException("签章失败：结构生成失败", e);
            }

            // 签名
            String hpk = "044D4F588B76888D9D74785DB87A18FD346743602070DD21D824B8E4027452D68D90B6339CF86E48278ABD7B2FC249094FF31CD45C59EDCE0B21169AF505FA0ED8";
            String hsk = "5A325ABF49F554EF1508F11D4CA8513288600B144C01D7B9B7F8EB3425C2B41B";

            GMTSM2 sm2 = GMTSM2.getInstance();

            byte[] sv = sm2.sm2Sign(SM3Util.sm3Digest(toSign, Util.hexToByte(hpk)), Util.hexToByte(hsk));
            byte[] sigData = null;
            try {
                sigData = SignSealUtil.makeSeal(sv, Base64.getDecoder().decode(certBase64), toSign);
            } catch (Exception e) {
                throw new RuntimeException("签章结构体生成失败", e);
            }
            byte[] finalData = new byte[sealSigValuePreLength];
            System.arraycopy(sigData, 0, finalData, 0, sigData.length);
            for(int i = sigData.length; i< sealSigValuePreLength; i++){
                finalData[i] = 0;
            }

            PdfDictionary dic2 = new PdfDictionary();
            dic2.put(PdfName.CONTENTS, new PdfString(finalData).setHexWriting(true));
            appearance.close(dic2);

            return result.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("盖章失败", e);
        }finally {
            if(null != pdfReader){
                pdfReader.close();
            }
        }

    }


    /**
     * 绘制空表单域
     * @param pdfBytes
     * @param field
     * @return
     */
    public byte[] writeFieldEmpty(byte[] pdfBytes, String field, boolean check,
                                  int pageNo, float x, float y, float w, float h){

        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(pdfBytes);

            AcroFields acreFields = pdfReader.getAcroFields();
            Map<String, AcroFields.Item> fields = acreFields.getFields();
            AcroFields.Item item = fields.get(field);
            if (null != item) {
                if(check)//true - > 检查存在则报错
                    throw new RuntimeException("文件中指定域[" + field + "]已经存在");

                PdfDictionary merged = item.getMerged(0);
                if (!PdfName.SIG.equals(PdfReader.getPdfObject(merged.get(PdfName.FT)))) {
                    throw new RuntimeException("指定域名[" + field + "]已存在，且不是签名域的域名");
                }
                if (item.size() != 1) {
                    throw new RuntimeException("文件中指定签名域[" + field + "]超过1，不符合一般PDF签名域的规范");
                }

                PdfDictionary isSigned = acreFields.getSignatureDictionary(field);
                if (null != isSigned) {
                    throw new RuntimeException("文件中指定签名域[" + field + "]已经签过电子数据，无法绘制该域名空域");
                }
                return pdfBytes;
            }else{// 绘制空域
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                PdfStamper ps = new PdfStamper(pdfReader, bos);

                Rectangle areaSignatureRect = new Rectangle(
                        x, y, x + w, y + h
                );

                PdfFormField pdfFormField = PdfFormField.createSignature(ps.getWriter());
                pdfFormField.setFieldName(field); // 签名域标识
                pdfFormField.setPage(pageNo);
                pdfFormField.setWidget(areaSignatureRect, PdfAnnotation.HIGHLIGHT_OUTLINE); // 高亮显示



                // *******************************************

                // 设置区域宽高和边框厚度，以及边框颜色，填充颜色
                PdfAppearance pdfAppearance = PdfAppearance.createAppearance(
                        ps.getWriter(), w, h );

                pdfAppearance.setColorStroke(BaseColor.LIGHT_GRAY); // 边框颜色
                pdfAppearance.setColorFill(BaseColor.YELLOW); // 填充颜色

                // 填充矩形区域-开始
                pdfAppearance.rectangle(0, 0, w, h );
                pdfAppearance.fillStroke();
                // 填充矩形区域-结束

                // 添加文字-开始
                pdfAppearance.setColorFill(BaseColor.BLACK); // 填充颜色重置为黑色，显示文字
                ColumnText.showTextAligned(
                        pdfAppearance,
                        Element.ALIGN_CENTER,
                        new Phrase("签名区域", new Font(BaseFont.createFont())),
                        w / 2, h / 2, 0
                );
                // 添加文字-结束

                // 将外观应用到签名域对象之上
                pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pdfAppearance);



            // *******************************************

                ps.addAnnotation(pdfFormField, pageNo);
                ps.close();

                return bos.toByteArray();
            }

        }catch (Exception e){
            throw new RuntimeException("绘制空签名域失败", e);
        }

    }


}
