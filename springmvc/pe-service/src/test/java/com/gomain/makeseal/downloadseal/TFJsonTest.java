package com.gomain.makeseal.downloadseal;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cer.CertGenUtil;
import com.common.utils.FileUtil;
import com.common.utils.ParamsUtil;
import com.common.utils.RegularConst;
import com.pe.entity.ApySealObj;
import com.pe.entity.Enterprice;
import com.pe.entity.SealItem;
import com.sm2.StrUtil;
import com.sm3.SM3Util;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Struct;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TFJsonTest {

    @Test
    public void test0() {
        String sj1 = "{\"a\":\"b\"}";
        JSONObject jsonObject = JSON.parseObject(sj1);
        System.out.println(jsonObject.getString("a"));

        jsonObject.put("b", "c");
        System.out.println(jsonObject.getString("b"));
        System.out.println(jsonObject.get("a"));


        JSONObject map = new JSONObject();
        map.put("sealCode", "sealCode");
        map.put("parentSealCode", "-1");
        map.put("sealName", "sealName");
        map.put("validStartTime", "validStartTime");
        map.put("validEndTime", "validEndTime");
        map.put("sealZone", "sealZone");
        map.put("signature", "demo");    //签名值稍后生成
        map.put("sealNo", "111111");     //印章序列号
        map.put("useUnitCode", "useUnitCode");
        System.out.println(map.toJSONString());


        JSONObject taskData = new JSONObject();
        taskData.put("sealName", "111");
        taskData.put("cancelReason", "");
        taskData.put("signature", "2222");
        if (!ParamsUtil.checkNull("3333")) {
            taskData.put("cancelDescribe", "3333");
        }
        JSONObject text = new JSONObject();
        text.put("taskCode", "106");
        text.put("taskData", taskData);
        System.out.println(text.toJSONString());
    }
    @Test
    public void test1() {
        String f = "{\"keyState\":0,\"mspUniqueCode\":\"aaa\",\"pushState\":0,\"mspState\":0,\"mspUnitNameCn\":\"测试1532762624491\"}";
        JSONObject jsonObject = JSON.parseObject(f);
        if(jsonObject.containsKey("password")){
            String pwd = jsonObject.get("password").toString();
            System.out.println(pwd);
        }else{
            System.out.println("not define 'password'");
        }
    }
    @Test
    public void test2() {
        String uri = "a";
        System.out.println(uri.matches("[a-zA-Z0-9]"));

    }
    @Test
    public void test3() {
        String vds = "2018-7-30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition ppn = new ParsePosition(0);
        Date date = sdf.parse(vds, ppn);
        System.out.println(date);
        int cd = date.compareTo(new Date(System.currentTimeMillis() - (24 * 3600 * 1000)));
        System.out.println(cd);
    }
    @Test
    public void test4() {
        String src = "/////apply/addSealApply.json";
        System.out.println("去除/结果\t" + cutRootFlag(src));
    }
    String cutRootFlag(String src) {
        if (null == src || "".equals(src)) {
            return src;
        }
        if (!src.startsWith("/")) {
            return src;
        } else {
            src = src.substring(1);
            return cutRootFlag(src);
        }

    }

    @Test
    public void test5() {

        System.out.println(Math.ceil(1.0 * 10 / 3));
        System.out.println(Math.floor(10 / 3));
        System.out.println(Math.round(10 / 3));

    }


    @Test
    public void test6() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(sdf.format(new Date()));

        String cellPhoneRegex = "^((13[0-9])|(15[^4,\\\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$";
        Pattern cellP = Pattern.compile(cellPhoneRegex);
        Matcher cellM = cellP.matcher("18810970400");
        System.out.println("-----------");
        System.out.println(cellM.find());
        System.out.println("手机号是否正确：\t" + (cellM.find()));
        System.out.println("18810970400".matches(cellPhoneRegex));

    }
// AAEAAEBZQyLSyfBzXUtPYPUZ8h+JYTGLAuy/xcOLQq26O0iHqqJEfI5egKQRx9MfCCodZLQNyU2o/xX6TZdjaJfLEjtg0s8kNkVBT7oW2W6vD4ujUk7wY0tfHdmcjvbBu1G9aa9t4FkdT/49rN2wRykxbltGLxtvmZeo0/a+qKp652Os

    @Test
    public void test7() {
        String[] ecc_param = {
                "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
                "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
                "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
                "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
                "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
                "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
        };

        BigInteger ecc_p = new BigInteger(ecc_param[0], 16);


    }


    @Test
    public void test8() {
        String bpk = "AAEAAEBZQyLSyfBzXUtPYPUZ8h+JYTGLAuy/xcOLQq26O0iHqqJEfI5egKQRx9MfCCodZLQNyU2o/xX6TZdjaJfLEjtg0s8kNkVBT7oW2W6vD4ujUk7wY0tfHdmcjvbBu1G9aa9t4FkdT/49rN2wRykxbltGLxtvmZeo0/a+qKp652Os";
        System.out.println(new String(Hex.encode(Base64.getDecoder().decode(bpk))));


    }

    @Test
    public void test9() {

        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dt1 = sbf.format(sbf.parse("2018-7-9"));
            System.out.println(dt1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test10() throws Exception {
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd");
        String applyDate = sbf.format(sbf.parse("2018-08-17"));
        ParsePosition ppn = new ParsePosition(0);
        Date date = sbf.parse(applyDate, ppn);
        System.out.println(date);
        int ivs = date.compareTo(new Date(System.currentTimeMillis() + (24 * 3600 * 1000)));
        if (ivs > 0) {
            System.out.println("按规定挂失时间不得晚于申请当天");
        } else {
            System.out.println("日期正常");
        }
    }


    @Test
    public void test11()throws Exception {
        String s = "{\\\"aaa\":0,\\\"aaa\":\\\"aaa\",\"pushState\":0,\"mspState\":0,\"aaa\":\"测试1532762624491\"}";
        System.out.println(s);
        s = s.replaceAll("\\\\", "");
        System.out.println(s);
        FileWriter SealFile =new FileWriter("D:\\seal.txt",true);

        BufferedWriter out = new BufferedWriter(SealFile);
        String outStr= s;

        out.write(outStr);
        out.close();
        SealFile.close();

    }

    @Test
    public void test12() {
        String flag = "a.json";
        flag = flag.indexOf(".") > 0 ? flag.substring(0, flag.indexOf(".")) : flag;
        System.out.println(flag);
    }


    @Test
    public void test13() {
        String f = "{\"aaa\":0,\"aaa\":\"aaa\",\"pushState\":0,\"mspState\":0,\"aaa\":\"测试1532762624491\"}";
        JSONObject jsonObject = JSON.parseObject(f);
        String pwd = jsonObject.get("aaa").toString();
        System.out.println(pwd);
    }


    @Test
    public void test14() {

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
                System.out.println(ParamsUtil.getUUIDStr());
            } catch (Exception e) {
                continue;
            }

        }
    }


    @Test
    public void test15() {
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject("{'code':1}");
        if (jsonObject.containsKey("cc")) {
            String cc = jsonObject.get("cc").toString();
            System.out.println(cc);
        } else {
            System.out.println("该json中没有cc属性");
        }

        if (jsonObject.containsKey("code")) {
            String cc = jsonObject.get("code").toString();
            System.out.println(cc);
        } else {
            System.out.println("该json中没有cc属性");
        }

    }


    @Test
    public void test16() {
        String[] c = {"101101", "110202", "100000"};
        System.out.println(checkDcode(c));
    }

    private boolean checkDcode(String... codes) {
        for (int i = 0; i < codes.length; i++) {
            if (!codes[i].matches("[0-9]{6}")) {
                return false;
            }
        }
        return true;
    }

    String cellPhoneRegex = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$";
    String telePhoneRegex = "(^(0\\d{2})-(\\d{8})$)|(^(0\\d{3})-(\\d{7})$)|(^(0\\d{3})-(\\d{8})$)|(^(0\\d{2})-(\\d{8})-(\\d+)$)|(^(0\\d{3})-(\\d{7})-(\\d+)$)";

    String wkCellPreg = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$";

    @Test
    public void test17() {

        System.out.println("15524534234".matches(cellPhoneRegex));
    }

    @Test
    public void test18() {
        // 打印sm4[asia11||HWm5o9uc7QDrGWZRfboPNdASvD+6SAvBokZW17twJPk=||8jb7vtoyvp37b4jprftvdasy0cnjqj0a]的sm3(username||pin||devno)Csv+nnhFD7YDBkGAvqa0h9WpjYrbqVA0QKX+CirtJhw=
        String s = "asia11||HWm5o9uc7QDrGWZRfboPNdASvD+6SAvBokZW17twJPk=||8jb7vtoyvp37b4jprftvdasy0cnjqj0a";
        String[] bs = s.split("\\|\\|");
        String userName = bs[0];
        byte[] pin = Base64.getDecoder().decode(bs[1]);
        byte[] imei = Base64.getDecoder().decode(bs[2]);
        byte[] bun = userName.getBytes();
        byte[] plin = new byte[bun.length + pin.length + imei.length];
        System.arraycopy(bun, 0, plin, 0, bun.length);
        System.arraycopy(pin, 0, plin, bun.length, pin.length);
        System.arraycopy(imei, 0, plin, bun.length + pin.length, imei.length);
        byte[] newRbcKeySM4 = SM3Util.sm3Digest(plin);
        System.out.println(Base64.getEncoder().encodeToString(newRbcKeySM4));
        // Csv+nnhFD7YDBkGAvqa0h9WpjYrbqVA0QKX+CirtJhw=
        // Csv+nnhFD7YDBkGAvqa0h9WpjYrbqVA0QKX+CirtJhw=

    }

    @Test
    public void test19() {
        String lpPhone = "15511922255";
        if (!(lpPhone.matches(RegularConst.cellPhoneRegex) || lpPhone.matches(RegularConst.telePhoneRegex))) {
            System.out.println("法定代表人的电话不是正确电话(区号-号码)和手机号格式");
        } else {
            System.out.println("是正确号码");
        }
    }


    @Test
    public void test20() {
        String s = "BGPp1jCwMbQM/nupTIF/DkvKYKW95mkY/mg4pQxMpLd5XEG5RgohUock4GxPip0ccvFKJTvnaJ+k624OUDdpcFU=";
        byte[] b = Base64.getDecoder().decode(s);
        System.out.println(Arrays.toString(b));
    }


    @Test
    public void test21(){
        String hexPK = "83BE371D2F06625FEA4ED1AD667C50F028795143EC6249C74B5C86244362512E4019AC1C382EF4BEA6F38D524B349F4B8636D9ED9B6FFF4036B82D04A32AA29F";

        DERSequence tbs = CertGenUtil.generateCertTBSCert(1, 2334323,
                "SM2", "机构测试", 2, StrUtil.hexToByte(hexPK));

        try {
            FileUtil.writeInFiles("C:\\Users\\49762\\Desktop\\test.bin", tbs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test22(){
        ApySealObj obj = new ApySealObj();
        Enterprice ent = new Enterprice();
        obj.setUseUnit(new Enterprice());
        List<SealItem> list = new ArrayList<>();
        SealItem st1 = new SealItem();
        st1.setAge(2);
        st1.setSealName("印章名称项");
        st1.setSealCoce("印章编码项");
        st1.setSealType("印章类型项");
        list.add(st1);
        obj.setSeaList(list);

        System.out.println(net.sf.json.JSONObject.fromObject(ent));
        System.out.println(net.sf.json.JSONObject.fromObject(obj));

    }



    @Test
    public void test23(){

        SecureRandom random = new SecureRandom();
        byte[] rnd = random.generateSeed(32);

        System.out.println(StrUtil.byteToHex(new BigInteger(rnd).toByteArray()));
        System.out.println(StrUtil.byteToHex(rnd));

        System.out.println(StrUtil.bigIntegerToHex(new BigInteger(256, random)));

    }


    @Test
    public void test24(){
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        BigInteger ti = new BigInteger(StrUtil.hexToByte(prik));
        System.out.println(ti);
        System.out.println(StrUtil.byteToHex(ti.toByteArray()));

    }

}
