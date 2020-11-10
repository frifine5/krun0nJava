package com.adb.itext.t;

import com.common.utils.FileUtil;
import com.common.utils.ParamsUtil;
import com.itextp.sig.PdfSigAnnot;
import com.itextp.sig.PdfSigContent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import com.sm3.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.List;

public class TestPdfAddForm01 {


    @Test
    public void testAddTextField() throws Exception {

        String dir = "C:\\Users\\49762\\Desktop\\itext-测试\\";
        String a = dir + "file0.pdf";
        String b = dir + "form-finish1.pdf";
        PdfReader reader = new PdfReader(a);

        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(new File(b)));

        PdfWriter writer = pdfStamper.getWriter();
        PdfFormField form = PdfFormField.createEmpty(writer);
        //普通文本框
        TextField field = new TextField(writer, new Rectangle(200, 200, 400, 300), "text");
        field.setOptions(TextField.MULTILINE);
        //防止读取pdf文档时，就是有旋转角度的
        field.setRotation(reader.getPageRotation(1));
        //有些情况下，页数问题可以在这里设置，优先级最高
        field.getTextField().setPlaceInPage(1);
        form.addKid(field.getTextField());
        // file.setOptions(TextField.VISIBLE);//文本域可见(相对于文本域是否高亮)
        // file.setOptions(TextField.HIDDEN);//文本域不可见
        // file.setOptions(TextField.VISIBLE_BUT_DOES_NOT_PRINT);//该字段可见，但不打印。
        // file.setOptions(TextField.HIDDEN_BUT_PRINTABLE);//该字段不可见，但不打印。
        // file.setOptions(TextField.HIDDEN_BUT_PRINTABLE);//该字段不可见，但不打印。
        // file.setOptions(TextField.READ_ONLY);//只读
        // file.setOptions(TextField.REQUIRED);//该字段在通过提交表单操作导出时必须具有值。
        // file.setOptions(TextField.MULTILINE);//规定区域内可以换行显示
        // file.setOptions(TextField.DO_NOT_SCROLL);//文本域不会有滚动条,对于单行字段为水平，对于多行字段为垂直,一旦区域满了，将不会再接受任何文字。
        // file.setOptions(TextField.PASSWORD);//该字段用于输入安全密码，该密码不应该被可视地显示在屏幕上。
        // file.setOptions(TextField.FILE_SELECTION);//个人理解好像是上传文件，不是很理解
        // file.setOptions(TextField.DO_NOT_SPELL_CHECK);//无拼写检查
        // file.setOptions(TextField.EDIT);//如果设置组合框包括一个可编辑的文本框以及一个下拉列表;如果清楚，它只包括一个下拉列表。这个标志只对组合字段有意义。
        // file.setOptions(TextField.MULTISELECT);//不管列表是否可以有多个选择。仅适用于/ ch列表字段，而不适用于组合框。
        // file.setOptions(TextField.COMB);//组合框标志。
        pdfStamper.addAnnotation(form, 1);

        pdfStamper.close();


    }


    /*
    // 创建数组签名域
        int x = 300, y = 400, width = 200, height = 200; // 坐标系远点位于页面左下角，左下角到右下角为  x 轴，左下角到左上角为 y 轴
        Rectangle areaSignatureRect = new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
        		x, // 点1 x坐标
        		y, // 点1 y坐标
        		x + width, // 点2 x坐标
        		y + height // 点2 y坐标
        );
        int pageNo = 1; // PDF 文件的页码从 1 开始，而不是 0
        PdfFormField pdfFormField = PdfFormField.createSignature(ps.getWriter());
        pdfFormField.setFieldName("AREA_SIGNATURE"); // 签名域标识
        pdfFormField.setPage(pageNo);
        pdfFormField.setWidget(areaSignatureRect, PdfAnnotation.HIGHLIGHT_OUTLINE); // 高亮显示
     */


    @Test
    public void testAddFormField() throws Exception {

        String dir = "/home/itext/";
        String a = dir + "1.pdf";               // 源文件
        String b = dir + "Signature0.pdf";      // 生成的新文件
        PdfReader reader = new PdfReader(a);

        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(new File(b)));

        PdfWriter writer = pdfStamper.getWriter();

        // 创建签名域区域的矩形 坐标数组
        int x = 300, y = 400, width = 80, height = 80; // 坐标系远点位于页面左下角，左下角到右下角为  x 轴，左下角到左上角为 y 轴
        Rectangle areaSignatureRect = new Rectangle(// 签名域区域，由两个对角点构成的矩形区域
                x, // 点1 x坐标
                y, // 点1 y坐标
                x + width, // 点2 x坐标
                y + height // 点2 y坐标
        );
        int pageNo = 1; // PDF 文件的页码从 1 开始，而不是 0
        PdfFormField pdfFormField = PdfFormField.createSignature(writer);
        pdfFormField.setFieldName("Signature0"); // 签名域标识 -- 签名域域名 如果已经存在同名的会报错
        pdfFormField.setPage(pageNo);
        pdfFormField.setWidget(areaSignatureRect, PdfAnnotation.HIGHLIGHT_OUTLINE); // 高亮显示

//        // 设置区域宽高和边框厚度，以及边框颜色，填充颜色
//        PdfAppearance pdfAppearance = PdfAppearance.createAppearance(
//                pdfStamper.getWriter(),
//                width,
//                height
//        );
//
//        pdfAppearance.setColorStroke(BaseColor.LIGHT_GRAY); // 边框颜色
//        pdfAppearance.setColorFill(BaseColor.YELLOW); // 填充颜色
//
//        // 填充矩形区域-开始
//        pdfAppearance.rectangle(
//                0, // x 轴偏移
//                0, // y 轴偏移
//                width, // 宽
//                height // 高
//        );
//        pdfAppearance.fillStroke();
//        // 填充矩形区域-结束
//
//        // 添加文字-开始
//        pdfAppearance.setColorFill(BaseColor.BLACK); // 填充颜色重置为黑色，显示文字
//        ColumnText.showTextAligned(
//                pdfAppearance,
//                Element.ALIGN_CENTER,
//                new Phrase("签名区域", new Font()),
//                width / 2, // x
//                height / 2, // y
//                0 // rotation
//        );
//        // 添加文字-结束
//
//
//
//        // 将外观应用到签名域对象之上
//        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pdfAppearance);


        pdfStamper.addAnnotation(pdfFormField, 1);

        pdfStamper.close();


    }


    @Test
    public void testGetFormField() throws Exception {

        String dir = "C:\\Users\\49762\\Desktop\\";  // "itext-测试\\";
        dir = "src/test/java/";
        String a = dir + "file0.pdf";
        String b = dir + "form-finish1.pdf";
        String c = dir + "form-read.tmp.pdf";
        String d = dir + "国脉签署的文件.pdf";
        String e = dir + "5dca0e50ae5541d685e3113af2ed91f0.pdf";

        PdfReader reader = new PdfReader(e);


        int xobjSize = reader.getXrefSize();
        System.out.println("xObjSize = " + xobjSize);
        for (int i = 0; i < xobjSize; i++) {
            PdfObject xObj = reader.getPdfObject(i);
            if (null == xObj || !xObj.isDictionary()) {
                continue;
            }

//            xObj.canBeInObjStm();

            PdfDictionary dict = (PdfDictionary) xObj;

            PdfObject pdfType = dict.get(PdfName.TYPE);
            PdfObject pdfSubType = dict.get(PdfName.SUBTYPE);

            System.out.println(String.format("pdfType = %s,\t pdfSubType = %s,\t idx = %s", dict.get(PdfName.TYPE), pdfType, i));

            if (null == pdfSubType || "null".equalsIgnoreCase(pdfType.toString())) continue;
            if ("/Annot".equalsIgnoreCase(pdfType.toString()) && "/Widget".equalsIgnoreCase(pdfSubType.toString())) {
                System.out.println(true);
                Set<PdfName> keys307 = dict.getKeys();
                for (PdfName name : keys307) {
                    System.out.println(name + ">>>");
                    if ("/Rect".equalsIgnoreCase(name.toString())) {
                        PdfObject pdfObject = dict.get(name);
                        if (pdfObject.isArray()) {
                            System.out.println(pdfObject);
                            System.out.println(pdfObject.type());
                            int ssize = ((PdfArray) pdfObject).size();
                            System.out.println(ssize);
                            for (int j = 0; j < ssize; j++) {
                                PdfNumber asNumber = ((PdfArray) pdfObject).getAsNumber(j);
                                System.out.println(asNumber.floatValue());
                            }
                        }
                        System.out.println();
                    }

                    if ("/T".equalsIgnoreCase(name.toString())) {
                        System.out.println("签名域名称 = " + dict.get(name));
                    }
                    if ("/FT".equalsIgnoreCase(name.toString())) {
                        System.out.println("is Sig = " + dict.get(name));
                    }

                    System.out.println(" >> " + dict.get(name));
                }

            }


        }


        /*

         PdfObject pdf307 = reader.getPdfObject(307);
        System.out.println(pdf307);
        if(pdf307.isDictionary()){
            Set<PdfName> keys307 = ((PdfDictionary)pdf307).getKeys();
            for (PdfName name: keys307){
                System.out.println(name + ">>>");
                if("/Rect".equalsIgnoreCase(name.toString())) {
                    PdfObject pdfObject = ((PdfDictionary) pdf307).get(name);
                    if(pdfObject.isArray()){
                        System.out.println(pdfObject);
                        System.out.println(pdfObject.type());
                        int ssize = ((PdfArray) pdfObject).size();
                        System.out.println(ssize);
                        for (int i = 0; i <ssize ; i++) {
                            PdfNumber asNumber = ((PdfArray) pdfObject).getAsNumber(i);
                            System.out.println(asNumber.floatValue());
                        }
                    }
                    System.out.println();
                }

            }
        }else{
            System.out.println("307 not a dictionary");
        }



         */


        AcroFields acroFields = reader.getAcroFields();


        System.out.println("--------------------------");


        System.out.println("--------------------------");

        ArrayList<String> signatureNames = acroFields.getSignatureNames();

        System.out.println(signatureNames.size());


        for (String s : signatureNames) {
            System.out.println(s);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>..");


            PdfDictionary signatureDictionary = acroFields.getSignatureDictionary(s);
            if (null == signatureDictionary) {
                System.out.println("未获得");
            } else {
                System.out.println(signatureDictionary);
            }

            Set<PdfName> keys = signatureDictionary.getKeys();
            for (PdfName name : keys) {
                System.out.println(name + ">>");
                String tmp = name.toString();
                PdfObject pdfObject = signatureDictionary.get(name);
                if ("/Contents".equalsIgnoreCase(tmp.trim())) {
                    System.out.println("/Contents >>>>>>>>>>>>>>>>");
                    byte[] objBytes = pdfObject.getBytes();
                    int objLen = objBytes.length;
                    byte[] fobjBytes;
                    if (objBytes[objLen - 1] == 0x00) {
                        fobjBytes = new byte[objLen - 16];
                        System.arraycopy(objBytes, 0, fobjBytes, 0, objLen - 16);
                    } else {
                        fobjBytes = objBytes;
                    }
                    System.out.println(Base64.getEncoder().encodeToString(fobjBytes));
//                    System.out.println(pdfObject);
                    FileUtil.writeInFiles(dir + "gSign.bin", fobjBytes);
                }


            }


        }



/*
        System.out.println("单独解析");

        PdfDictionary signatureDictionary = acroFields.getSignatureDictionary("S1");
        if(null == signatureDictionary){
            System.out.println("未获得");
        }else{
            System.out.println(signatureDictionary);
        }
        Set<PdfName> keys = signatureDictionary.getKeys();
        for (PdfName name: keys){
            System.out.println(name);
            String tmp = name.toString();
            PdfObject pdfObject = signatureDictionary.get(name);
//                if("/pri_key".equalsIgnoreCase(tmp.trim()))

            System.out.println(pdfObject);


        }

        */



/*
        // 解析水印图片位置的方法

        final List<float[]> arrays = new ArrayList<>();
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(reader);

        int pages = reader.getNumberOfPages();
        System.out.println("pages = " + pages);
        for (int i = 1; i <= pages; i++) {
            pdfReaderContentParser.processContent(i, new RenderListener() {
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
                        System.out.println("imageInPdf");
                        System.out.println(Base64.getEncoder().encodeToString(imageByte));
                        Image imageInPDF = Image.getInstance(imageByte);
                        if(image0!=null ){
                            float[] resu = new float[3];
                            // 0 => x;  1 => y;  2 => z
                            //z的值始终为1
                            resu[0] = renderInfo.getStartPoint().get(0);
                            resu[1] = renderInfo.getStartPoint().get(1);
                            arrays.add(resu);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BadElementException e) {
                        e.printStackTrace();
                    }
                }
            });

        }



        System.out.println("maybe Position");
        System.out.println(arrays.size());
        System.out.println(JSONArray.fromObject(arrays).toString());

*/


    }


    @Test
    public void testGetImage() throws Exception {

        String dir = "src/test/java/";
        String a = dir + "5dca0e50ae5541d685e3113af2ed91f0.pdf";

        PdfReader reader = new PdfReader(a);

        int xobjSize = reader.getXrefSize();
        System.out.println("xObjSize = " + xobjSize);
        for (int i = 0; i < xobjSize; i++) {
            PdfObject xObj = reader.getPdfObject(i);
            if (null == xObj || !xObj.isStream()) {
                continue;
            }
            PdfStream stream = (PdfStream) xObj;


            PdfObject type = stream.get(PdfName.TYPE);
            PdfObject subType = stream.get(PdfName.SUBTYPE);
            if (null == type || null == subType) continue;

            Set<PdfName> keys = stream.getKeys();
            Object[] objKeys = keys.toArray();

            String pngFilePath = "C:\\Users\\49762\\Desktop\\itext-测试\\xObj_" + i + ".png";

            String pt = String.format("index=%s, type=%s, subType=%s", i, type, subType);
            System.out.println(pt);
            System.out.println("\t" + Arrays.toString(objKeys));

            for (Iterator it = stream.getKeys().iterator(); it.hasNext(); ) {
                PdfObject obj = stream.get((PdfName) it.next());
                if (obj.isIndirect()) {

                    System.out.println("index = " + i);
                    System.out.println(obj);
                    // 定位 xObj 是需要的那个图片

                    System.out.println(xObj.length());

                    PdfNumber tStreamLen = (PdfNumber)stream.get(PdfName.LENGTH);
                    System.out.println("数据长度 = " + tStreamLen);
                    String otFile = "C:\\Users\\49762\\Desktop\\itext-测试\\xObj_data.txt";

                    stream.flateCompress();
                    stream.writeContent(new FileOutputStream(otFile));

                    PRStream prStream = (PRStream) xObj;

                    byte[] b = PdfReader.getStreamBytes(prStream);

                    if(null != b){
                        System.out.println(b.length);
                        FileUtil.writeInFiles(pngFilePath, b);
                        System.out.println(Base64.getEncoder().encodeToString(b));
                    }

//                    System.out.println(Base64.getEncoder().encodeToString(byt1));



                }

            }

            if ("/XObject".equalsIgnoreCase(type.toString()) && "/Image".equalsIgnoreCase(subType.toString())) {


            }


        }



    }


    @Test
    public void testSignOnFormField() throws Exception {

        String dir = "C:\\Users\\49762\\Desktop\\itext-测试\\";
        String a = dir + "file0.pdf";
        String b = dir + "form-finish1.pdf";
        String c = dir + "form-sign.pdf";
        PdfReader reader = new PdfReader(b);


        Document pdf = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream(b));
        pdf.open();


    }





    @Test
    public void testGetCtxs() throws Exception{
        String outputPath =  "C:\\Users\\49762\\Desktop\\itext-测试\\Ctx.txt";
        PrintWriter writer = new PrintWriter(new FileOutputStream(outputPath));
        String fileName = "src/test/java/5dca0e50ae5541d685e3113af2ed91f0.pdf";

        PdfContentReaderTool.listContentStream(new File(fileName), writer);
    }




    @Test
    public void testParseEcPageSeal() throws Exception{

        String filePath = "src/test/java/5dca0e50ae5541d685e3113af2ed91f0.pdf";
//        filePath = "src/test/java/page3.pdf";
//        filePath = "C:\\Users\\49762\\Desktop\\2015839566-26328696749542401.pdf";

//        byte[] fbyts = FileUtil.fromDATfile(filePath);
//        PdfReader reader = new PdfReader(fbyts);

        PdfReader reader = new PdfReader(filePath);
        int pageSize = reader.getNumberOfPages();

        Rectangle pageSize1 = reader.getPageSize(1);
        float height = pageSize1.getHeight();
        System.out.println("页面1的高度:\t" + height);
        if(height > 10) return;

        System.out.println( filePath + "文档共"+pageSize+"页 >>>>");

        List<PdfSigAnnot> sigAnnots = new ArrayList<>();

        for (int p = 1; p <= pageSize; p++) {// 按页分解
            System.out.println("Page: " + p );
            // 当页对象
            PdfDictionary pageResources = reader.getPageNRelease(p);
            Set<PdfName> keys = pageResources.getKeys();

            System.out.println(keys.size());

            for (PdfName eName : keys) {
                if(eName != null && "/Annots".equalsIgnoreCase(eName.toString())){

                    System.out.println(pageResources.get(eName));
                    String destIndex = pageResources.get(eName).toString();
                    if(destIndex.length()<2) {
                        System.out.println( " 不符合。。");
                        continue;
                    }
                    destIndex = destIndex.substring(1, destIndex.length() -1);

                    String[] arr = destIndex.split(",");
                    System.out.println(Arrays.toString(arr));

                    System.out.println(" ---- index = " + destIndex);
                    for (String atKey :  arr) {
                        String eacStr = atKey.trim();

                        int idx = Integer.parseInt(eacStr.substring(0, eacStr.indexOf(" ")));

                        PdfObject xObj = reader.getPdfObject(idx);
                        System.out.println("index=" + idx + ",\txObj= " + xObj);

                        PdfDictionary dict = (PdfDictionary) xObj;

                        PdfObject pdfType = dict.get(PdfName.TYPE);
                        PdfObject pdfSubType = dict.get(PdfName.SUBTYPE);

                        System.out.println(String.format("pdfType = %s,\t pdfSubType = %s,\t idx = %s", pdfType, pdfSubType,  p));

                        if (null == pdfSubType || null == pdfType) continue;
                        if ("/Annot".equalsIgnoreCase(pdfType.toString()) && "/Widget".equalsIgnoreCase(pdfSubType.toString())) {
                            PdfSigAnnot sigAnnot = new PdfSigAnnot();
                            sigAnnot.setPageNo(p + "");
                            Set<PdfName> annotKeys = dict.getKeys();
                            for (PdfName name : annotKeys) {
                                String nameStr = "";
                                if(null != name){
                                    nameStr = name.toString();
                                }
                                switch (nameStr){
                                    case "/Type":
                                        sigAnnot.setType(dict.get(name).toString());
                                        break;
                                    case "/Subtype":
                                        sigAnnot.setSubType( dict.get(name).toString());
                                        break;
                                    case "/T":
                                        sigAnnot.setT(dict.get(name).toString());
                                        break;
                                    case "/Rect":
                                        PdfObject pdfObject = dict.get(name);
                                        if (pdfObject.isArray()) {
                                            int ssize = ((PdfArray) pdfObject).size();
                                            float[] xyPoint = new float[ssize];
                                            for (int xy = 0; xy < ssize; xy++) {
                                                PdfNumber asNumber = ((PdfArray) pdfObject).getAsNumber(xy);
                                                xyPoint[xy] = asNumber.floatValue();
                                            }
                                            sigAnnot.setRect(xyPoint);
                                        }
                                        break;
                                    case "/F":
                                        sigAnnot.setF(dict.get(name).toString());
                                        break;
                                    case "/V":
                                        sigAnnot.setV(dict.get(name).toString());
                                        break;
                                    case "/FT":
                                        sigAnnot.setFt(dict.get(name).toString());
                                        break;
                                    case "/P":
                                        sigAnnot.setP(dict.get(name).toString());
                                        break;
                                    default:
                                        break;
                                }

                            }

                            System.out.println("签名域名 = " + sigAnnot.getT());
                            System.out.println("坐标： " + Arrays.toString(sigAnnot.getRect()));
                            sigAnnots.add(sigAnnot);














                        }

                    }
                }

            }

        }
        System.out.println("-------------------------------------");
        AcroFields acroFields = reader.getAcroFields();

        for (PdfSigAnnot eaSigAnnot : sigAnnots) {

            String fn = eaSigAnnot.getT();
            if(ParamsUtil.checkNull(fn)) continue;
            PdfDictionary signDict = acroFields.getSignatureDictionary(fn);
            Set<PdfName> keys = signDict.getKeys();
            PdfSigContent sigContent = new PdfSigContent();
            for(PdfName sname: keys){
                if(null == sname) continue;

                String nStr = sname.toString();
                switch (nStr){
                    case "/Contents":
                        byte[] objBytes = signDict.get(sname).getBytes();
                        int objLen = objBytes.length;
                        byte[] fobjBytes;
                        if (objBytes[objLen - 1] == 0x00) {
                            fobjBytes = new byte[objLen - 16];
                            System.arraycopy(objBytes, 0, fobjBytes, 0, objLen - 16);
                        } else {
                            fobjBytes = objBytes;
                        }
                        sigContent.setContent(fobjBytes);
                        break;
                    case "/FiV":
                        sigContent.setFiv(signDict.get(sname).toString());
                        break;
                    case "/Filter":
                        sigContent.setFilter(signDict.get(sname).toString());
                        break;
                    case "/SubFilter":
                        sigContent.setSubFilter(signDict.get(sname).toString());
                        break;
                    case "/ByteRange":
                        PdfObject pdfObject = signDict.get(sname);
                        if (pdfObject.isArray()) {
                            int ssize = ((PdfArray) pdfObject).size();
                            int[] range = new int[ssize];
                            for (int r = 0; r < ssize; r++) {
                                PdfNumber asNumber = ((PdfArray) pdfObject).getAsNumber(r);
                                range[r] = asNumber.intValue();
                            }
                            sigContent.setByteRange(range);
                        }
                        break;
                    case "/Type":
                        sigContent.setType(signDict.get(sname).toString());
                        break;
                    case "/PrI":
                        sigContent.setPri(signDict.get(sname).toString());
                        break;
                    default:
                        String v;
                        PdfObject elseObj = signDict.get(sname);
                        if(elseObj.isArray()){
                            v = ((PdfArray)elseObj).toString();
                        }else{
                            v = elseObj.toString();
                        }
                        sigContent.getElseMap().put( nStr , v);
                        break;
                }

                System.out.printf("\t" + nStr);

            }
            System.out.println();
            System.out.println(Base64.getEncoder().encodeToString(sigContent.getContent()));
            System.out.println(sigContent.getElseMap());

        }







    }








    @Test
    public void testXiaoxin() throws Exception{

        String fp = "D:\\work documents\\统一电子印章\\广东数广-公众侧\\文档查验itext对比\\小信\\小信的01.pdf";

        PdfReader  reader = new PdfReader(fp);

        int xobjSize = reader.getXrefSize();
        System.out.println(xobjSize);

        for (int i = 0; i < xobjSize; i++) {
            PdfObject xObj = reader.getPdfObject(i);
            if(null == xObj || !xObj.isDictionary()){
                continue;
            }
            PdfDictionary xDict = (PdfDictionary)xObj;

            PdfObject type = xDict.get(PdfName.TYPE);
            if(type == null || !"/Btn".equalsIgnoreCase(type.toString()))
                continue;
            Set<PdfName> keys = xDict.getKeys();
            for (PdfName key : keys) {


                if("/Contents".equalsIgnoreCase(key.toString())){
                    PdfObject pdfObject = xDict.get(key);
                    byte[] ctxByt = pdfObject.getBytes();
                    System.out.println(">>> Contents.len = " + ctxByt.length);

                    byte[] vlen = new byte[]{ctxByt[3], ctxByt[2], 0, 0};
                    int len = Util.byteToInt(vlen) + 4;
                    byte[] p7bCtx = new byte[len];
                    System.arraycopy(ctxByt, 0, p7bCtx, 0, len);
                    System.out.println(p7bCtx.length);
                    System.out.println(Base64.getEncoder().encodeToString(p7bCtx));



                }else{
                    System.out.printf("index = %s,\tkey = %s, \t\tvalue = %s", i, key, xDict.get(key));
                    System.out.println();
                }


            }



        }

        reader.close();


    }













    // --------------------------- 添加图片 --------------------
    @Test
    public void testIn1Image() throws Exception{

        String dir = "C:\\Users\\49762\\Desktop\\itext-测试\\";

        // 模板文件路径
        String templatePath = dir + "1.pdf";
        // 生成的文件路径
        String targetPath = dir + "dest\\" + System.currentTimeMillis() + "_dest.pdf";
        // 关键字名
        String fieldName = "S1";
        // 图片路径
        String imagePath = dir + "picture.png";


        FileOutputStream fos = new FileOutputStream(targetPath);
        // 读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, fos);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));


        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        //x = 20f;
        //y = 40f;
        // 读图片
        Image image = Image.getInstance(imagePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        fos.flush();
        fos.close();
        /*stamper.close();*/
        reader.close();

    }


    @Test
    public void testInImage2() throws Exception {
        Document document;
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 设置中文字体
        Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小

        document = new Document();
        PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\49762\\Desktop\\itext-测试\\dest\\1.pdf"));
        //设定文档的作者
        document.addAuthor("张小宁"); //测试无效
        document.open();
        document.add(new Paragraph("你好，Img！", headFont));
        //读取一个图片
        Image image = Image.getInstance("C:\\Users\\49762\\Desktop\\itext-测试\\picture.png");
        //插入一个图片
        document.add(image);


        document.close();
        w.close();

    }


}


