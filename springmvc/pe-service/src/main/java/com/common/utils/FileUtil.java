package com.common.utils;

import java.io.*;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Sequence;

/**
 * @author kingcrab
 * @USEFOR COMMON TOOL UNITS
 */
public class FileUtil {


    /**
     * READ DATA FORM FILE
     * 从文件读取数据
     *
     * @param p 文件路径
     * @return 文件的byte[]数据
     */
    public static byte[] fromDATfile(String p) throws IOException {
        byte[] data;
        int dataLength = 0;

        FileInputStream fis = new FileInputStream(p);
        dataLength = fis.available();// 获取长度
        data = new byte[dataLength];
        fis.read(data, 0, dataLength);
        fis.close();

        return data;
    }

    /**
     * 获得asn1的序列长度size
     */
    public static int getSequenceLength(ASN1Encodable arg0) {
        return ASN1Sequence.getInstance(arg0).size();
    }

    /**
     * 将asn1sequence进行编码，数据写入文件，并返回该编码数据
     *
     * @param path 文件全路径
     * @param obj  代写入的asn序列对象
     * @return
     * @throws IOException
     */
    public static byte[] writeInFiles(String path, ASN1Sequence obj) throws IOException {
        ASN1OutputStream out = new ASN1OutputStream(new FileOutputStream(path));
        out.writeObject(obj);
        out.flush();
        out.close();
        byte[] data = obj.getEncoded();
        return data;
    }

    /**
     * 从文件读取得到byte[] 通过asn1sequence
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFromFiles(String path) throws IOException {

        ASN1InputStream ins = new ASN1InputStream(new FileInputStream(path));
        System.out.println("读取文件::");
        int fl = ins.available();
        byte[] data = new byte[fl];
        System.out.println("数据长度::" + fl);
        ins.read(data, 0, fl);
        ins.close();
        return data;
    }


    /**
     * 向指定路径的文件追加写某些内容
     *
     * @param path 文件路径
     * @param ctx  追加内容
     * @throws IOException
     */
    public static void appendCtx2File1(String path, String ctx) throws IOException {
        FileWriter fw = new FileWriter(new File(path), true);//如果文件存在，则追加内容；如果文件不存在，则创建文件
        PrintWriter pw = new PrintWriter(new FileWriter(new File(path), true));
        pw.println(ctx);
        pw.flush();
        pw.close();
    }

    public static void appendCtx2File2(String path, String ctx) throws IOException {
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(path, true)));
        out.write(ctx+"\r\n");// 内容后面不自动加换行， 如需写完换行则在内容后面加上"\r\n"字符
        out.close();
    }

    public static void appendCtx2File3(String path, String ctx) throws IOException {
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = new RandomAccessFile(path, "rw");
        // 文件长度，字节数
        long fileLength = randomFile.length();
        // 将写文件指针移到文件尾。
        randomFile.seek(fileLength);
        randomFile.writeBytes(ctx+"\r\n");// 内容后面不自动加换行， 如需写完换行则在内容后面加上"\r\n"字符 (+"\r\n")
        randomFile.close();
    }


}
