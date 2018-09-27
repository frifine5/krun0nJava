package com.sm3;

public class SM3Util {

    /**
     * 输入明文的byte[]
     * @param in 明文入参
     * @return 摘要 byte[] 32位
     */
    public static byte[] sm3Digest(byte[] in ){
        byte[] md = new byte[32];
        if(null == in || in.length <= 0){
            throw new NullPointerException("sm3 digest: input in null");
        }
        SM3Digest sm3 = new SM3Digest();
        sm3.update(in, 0, in.length);
        sm3.doFinal(md, 0);
        return md;
    }
}
