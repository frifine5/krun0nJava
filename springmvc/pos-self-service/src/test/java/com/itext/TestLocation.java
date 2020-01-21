package com.itext;

import com.common.FileUtil;
import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLocation {



    @Test
    public void testFindStampsLocation() throws Exception {
        String pdfDir = "/home/dtmp/itextPdfs/";
        String pdfName = "src1.pdf";
        pdfName = "dest-5-w.pdf";

        String pdf = pdfDir + pdfName;
        List<float[]> stampLocList = getStampsXyp(FileUtil.fromDATfile(pdf)  );

        System.out.println("\n\n");
        for(float[] e: stampLocList){

            String fmt = String.format("%sf, %sf, %s, %s, %s,", e[1], e[2], (int)e[3], (int)e[4], (int)e[0]);
            System.out.println(fmt);

        }

    }

    private List<float[]> getStampsXyp(byte[] fileData) {
        List<float[]> arrays = Lists.newArrayList();
        try {
            PdfReader pdfReader = new PdfReader(fileData);
            AcroFields acroFields = pdfReader.getAcroFields();
            ArrayList<String> sigNames = acroFields.getSignatureNames();
            for(String sigName: sigNames){
                PdfDictionary eaDict = acroFields.getSignatureDictionary(sigName);
                if(null == eaDict) continue;
                AcroFields.Item item = acroFields.getFields().get(sigName);
                if(item.size()>1){
                    System.out.println("签名域"+sigName+"数量大于1，不符合规范");
                    continue;
                }
                PdfDictionary itmVlu = item.getValue(0);

                PdfArray sigNameRect = (PdfArray) itmVlu.get(PdfName.RECT);
                PdfNumber px = sigNameRect.getAsNumber(0);
                PdfNumber py = sigNameRect.getAsNumber(1);
                PdfNumber pllx = sigNameRect.getAsNumber(2);
                PdfNumber plly = sigNameRect.getAsNumber(3);
                float x = px.floatValue();
                float y = py.floatValue();
                float urx = pllx.floatValue();
                float ury = plly.floatValue();
                float width = Math.abs(x - urx);
                float height = Math.abs(y - ury);

                int pageNo = item.getPage(0);
                float[] rect = new float[5];
                rect[0] = pageNo;
                rect[1] = x;
                rect[2] = y;
                rect[3] = width;
                rect[4] = height;
                System.out.printf("签名域=%s | 页码：%s, 坐标xy( %s, %s), 对角坐标llxy(%s, %s) | 宽=%s, 高=%s\n",
                        sigName, x, y, urx, ury, width, height);
                arrays.add(rect);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return arrays;
    }


}
