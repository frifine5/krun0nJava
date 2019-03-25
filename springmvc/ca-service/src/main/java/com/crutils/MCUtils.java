package com.crutils;

import java.util.Arrays;

/**
 * 消息秘密序列转换工具
 * @author WangChengyu
 * 2018/10/22 9:34
 */
public class MCUtils {


    /**
     * 补码
     */
    byte[] supple2Group(byte[] in, int blockLen) {
        if(blockLen<=0){
            throw new RuntimeException("分组长度不得小于1");
        }
        if(in.length<=0){
            throw new NullPointerException("输入参数in为空");
        }
        int len = in.length;
        int rest = len%blockLen;
        int suppleNum = blockLen - rest;
        byte[] out = new byte[len + suppleNum];
        System.arraycopy(in, 0, out, 0, len);
        for (int i = len; i < len + suppleNum; i++) {
            out[i] = (byte)suppleNum;
        }
        return out;
    }

    /**
     * 去除指定补码
     */
    byte[] unsuppleOnGroup(byte[] in, int blockLen){
        if(blockLen<=0){
            throw new RuntimeException("分组长度不得小于1");
        }
        if(in.length<=blockLen){
            throw new NullPointerException("输入数据in小于补码数，无法去码");
        }
        int len = in.length;
        int cutNum = in[len-1];
        if(cutNum>blockLen || cutNum<0){
            throw new RuntimeException("输入数据不是正确的补码数据格式");
        }
        byte[] out = new byte[len - cutNum];
        System.arraycopy(in, 0, out, 0, len - cutNum);
        return out;
    }




    public static void main(String[] args) {
        // 补码前原文
        byte[] in = "123中文英文en*2141）（@！#￥%《》、|\\/\"".getBytes();
        System.out.println(Arrays.toString(in));
        MCUtils mcUtils = new MCUtils();
        byte[] out = mcUtils.supple2Group(in, 8);
        System.out.println(Arrays.toString(out));
        // 去补码
        byte[] outM = mcUtils.unsuppleOnGroup(out, 8);
        System.out.println(Arrays.toString(outM));
        System.out.println(new String(outM));

    }



}
