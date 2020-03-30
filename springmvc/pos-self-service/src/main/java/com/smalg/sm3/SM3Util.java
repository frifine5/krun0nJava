package com.smalg.sm3;

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

    public static byte[] sm3Digest(byte[] in, byte[] pk){
//        byte[] entl = {0x0,(byte)0x80};// 这个表示128bit的两位byte[]
//        String hentl = Util.byteToHex(entl);
        String hentl = "0080";
        String hid = "31323334353637383132333435363738";
        String ha = "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC";
        String hb = "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93";
        String hgx = "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7";
        String hgy = "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0";
        String hpk = Util.byteToHex(pk);

        String pre1 = hentl+hid+ha+hb+hgx+hgy+hpk.substring(2);
//        System.out.println(pre1);
        byte[] z = sm3Digest(Util.hexToByte(pre1));

        byte[] zm = Util.hexToByte(Util.byteToHex(z)+Util.byteToHex(in));
        byte[] md = sm3Digest(zm);

        return md;
    }



    public static byte[] pureDigest(byte[] data){
        byte[] digest;
        org.bouncycastle.crypto.Digest md2 = new org.bouncycastle.crypto.digests.SM3Digest();
        md2.update(data,0, data.length);
        digest = new byte[md2.getDigestSize()];
        md2.doFinal(digest, 0);
        return digest;
    }


    /**
     * 带公钥预处理的摘要
     */
    public static byte[] sm3DegestByBc(byte[] data, byte[] puk) {
        if(null == puk || puk.length != 65){
            throw new RuntimeException("公钥的长度不符: expect 65");
        }
        // 1 公钥预处理
        String pre1 = "008031323334353637383132333435363738FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E9332C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
                + Util.byteToHex(puk).substring(2);
        byte[] z = pureDigest(Util.hexToByte(pre1));
        // 2 最终摘要
        byte[] zm = Util.hexToByte(Util.byteToHex(z) + Util.byteToHex(data));
        return pureDigest(zm);
    }

}
