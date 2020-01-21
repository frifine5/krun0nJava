package com.itext;

import com.common.FileUtil;
import com.google.common.collect.Lists;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestKwords {


    @Test
    public void testFindKeywords() throws Exception {
        String pdfDir = "/home/dtmp/itextPdfs/";
        String pdfName = "src1.pdf";

        String pdf = pdfDir + pdfName;
        List<float[]> kwLocList = getKeyWord(FileUtil.fromDATfile(pdf), "过户", 60, 40);

        for (float[] e : kwLocList) {

            String fmt = String.format("%sf, %sf, %s, %s, %s,", e[1], e[2], (int) e[3], (int) e[4], (int) e[0]);
            System.out.println(fmt);

        }


    }


    private List<float[]> getKeyWord(byte[] fileData, final String keyWord, float stampWidth, float stampHeight) {
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

                    List<Integer> lopAll(String text, String key) {
                        List<Integer> list = new ArrayList<>();
                        String tmp = text;
                        int klen = key.length();
                        int itLen = 0;
                        while (tmp.contains(key)) {
                            int index = tmp.indexOf(key);
                            System.out.println("---> " + index);
                            System.out.println(tmp);
                            if (index < 0) break;
                            list.add(index + itLen);// 真实索引
                            tmp = tmp.substring(index + klen);
                            itLen += klen;
                        }
                        if (list.size() > 0) return list;
                        return null;
                    }

                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {

                        String text = textRenderInfo.getText();
                        boolean isKeywordChunk = textRenderInfo.getText().length() == maxLength;
                        if (text.length() >= maxLength) {
                            if (text.contains(keyWord)) {
                                com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
                                        .getBoundingRectange();
                                com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
                                        .getBoundingRectange();

                                float centreX;
                                float centreY;

                                List<Integer> indexs = lopAll(text, keyWord);
                                if (null == indexs) return;
                                for (int j = 0; j < indexs.size(); j++) {
                                    int index = indexs.get(j);
                                    System.out.println("查询到数据块，关键字在数据块中的位置索引：" + index);
                                    centreX = baseFloat.x + ascentFloat.width * (index + keyLen / 2) / text.length();
                                    centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);

                                    float[] resu = new float[5];
                                    resu[0] = pageNum;
                                    resu[1] = centreX - stampWidth / 2;
                                    resu[2] = centreY - stampHeight / 2;
                                    resu[3] = stampWidth;
                                    resu[4] = stampHeight;
                                    arrays.add(resu);
                                }
                            }
                        } else if (textRenderInfo.getText().length() < maxLength) {
                            // 有些文档 单字一个块的情况 拼接字符串
                            sb.append(textRenderInfo.getText());
                            // 去除首部字符串，使长度等于关键字长度
                            if (sb.length() > maxLength) {
                                sb.delete(0, sb.length() - maxLength);
                            }
                        }

                        if (keyWord.equals(sb.toString())) {

                            // 计算中心点坐标
                            com.itextpdf.awt.geom.Rectangle2D.Float baseFloat = textRenderInfo.getBaseline()
                                    .getBoundingRectange();
                            com.itextpdf.awt.geom.Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine()
                                    .getBoundingRectange();

                            float centreX = baseFloat.x + ascentFloat.width / 2;
                            float centreY = baseFloat.y + ((ascentFloat.y - baseFloat.y) / 2);

                            float[] resu = new float[5];
                            resu[0] = pageNum;
                            resu[1] = centreX - stampWidth / 2;
                            resu[2] = centreY - stampHeight / 2;
                            resu[3] = stampWidth;
                            resu[4] = stampHeight;
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

}
