package com.sm2;

import com.sm3.SM3Digest;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GMTSM2 {
    private static GMTSM2 instance;

    public static GMTSM2 getInstance() {
        if (instance == null) {
            synchronized (GMTSM2.class) {
                if (instance == null) {
                    instance = new GMTSM2();
                }
            }
        }
        return instance;
    }

    final static BigInteger ZERO = BigInteger.valueOf(0L);
    final static BigInteger ONE = BigInteger.valueOf(1L);
    final static BigInteger TWO = BigInteger.valueOf(2L);
    final static BigInteger THREE = BigInteger.valueOf(3L);
    final static BigInteger FOUR = BigInteger.valueOf(4L);

    //正式参数(GMT 0003.5)
    public static String[] ecc_param = {
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
            "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
    };

    BigInteger ecc_p;
    BigInteger ecc_a;
    BigInteger ecc_b;
    BigInteger ecc_n;
    BigInteger ecc_gx;
    BigInteger ecc_gy;
    ECCurve.Fp ecc_curve;
    ECPoint.Fp ecc_point_g;
    ECDomainParameters ecc_bc_spec;
    ECFieldElement ecc_gx_fieldelement;
    ECFieldElement ecc_gy_fieldelement;


    /**
     * 初始化 ECC 常量
     */
    private GMTSM2() {
        // 初始化参数
        this.ecc_p = new BigInteger(ecc_param[0], 16);
        this.ecc_a = new BigInteger(ecc_param[1], 16);
        this.ecc_b = new BigInteger(ecc_param[2], 16);
        this.ecc_n = new BigInteger(ecc_param[3], 16);
        this.ecc_gx = new BigInteger(ecc_param[4], 16);
        this.ecc_gy = new BigInteger(ecc_param[5], 16);

        // 初始化运算结构
        ecc_gx_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gx);
        ecc_gy_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gy);
        ecc_curve = new ECCurve.Fp(this.ecc_p, this.ecc_a, this.ecc_b);
        ecc_point_g = new ECPoint.Fp(this.ecc_curve, this.ecc_gx_fieldelement, this.ecc_gy_fieldelement);

        ecc_bc_spec = new ECDomainParameters(this.ecc_curve, this.ecc_point_g, this.ecc_n);

    }

    /**
     * org.bouncycastle.crypto.generators;
     * 生成基于boucycastle的ecc密钥结果
     *
     * @return
     */
    public AsymmetricCipherKeyPair genPairOnBouncycastle() {
        BigInteger da = genKinN();
        ECPoint p = ecc_bc_spec.getG().multiply(da);
        return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(p, this.ecc_bc_spec), new ECPrivateKeyParameters(da, this.ecc_bc_spec));
    }

    /**
     * 生成密钥对 （Hex字符串的结果）
     *
     * @return
     */
    public String[] genPairOnString() {
        // 产生随机数
        BigInteger da = genKinN();
        String hexPrivateKey = da.toString(16);
        ECPoint p = ecc_bc_spec.getG().multiply(da);
        String hexPublicKey = "04" + p.getX().toBigInteger().toString(16) + p.getY().toBigInteger().toString(16);
        return new String[]{hexPublicKey, hexPrivateKey};
    }


    /**
     * from P = k * G 得出曲线上的点（x, y）
     *
     * @param pvk
     * @return
     */
    private ECPoint peFromPvk(String pvk) {
        return ecc_bc_spec.getG().multiply(new BigInteger(pvk, 16));
    }

    /**
     * 取点（取0-n区间内）的随机数
     *
     * @return
     */
    private BigInteger genKinN() {
        // 随机数产生器
        SecureRandom random = new SecureRandom();
        // 产生0 - n之间的随机数
        BigInteger da;
        do {// generate k in 0 ~ n, mean da for privateKey
            do {
                da = new BigInteger(256, random);
            } while (da.equals(ZERO));
        } while (da.compareTo(ecc_n) >= 0);
        return da;
    }

    // ++++=====================================================++++

    /**
     * 带公钥的摘要 sm3(pk||m)
     */
    public byte[] sm3Degest(byte[] data, byte[] puk) {
        SM3Digest sm3 = new SM3Digest();
        byte[] hashData = new byte[32];
        byte[] inData = new byte[data.length + puk.length];
        System.arraycopy(puk, 0, inData, 0, puk.length);
        System.arraycopy(data, 0, inData, puk.length, data.length);
        sm3.update(inData, 0, data.length);
        sm3.doFinal(hashData, 0);
        return hashData;
    }

    /**
     * SM3裸摘
     */
    public byte[] sm3Degest(byte[] data) {
        SM3Digest sm3 = new SM3Digest();
        byte[] hashData = new byte[32];
        sm3.update(data, 0, data.length);
        sm3.doFinal(hashData, 0);
        return hashData;
    }

    /**
     * 签名（摘要，私钥）
     */
    public byte[] sm2Sign(byte[] hashData, byte[] pvk) {
        // check before calc
        if (hashData.length != 32) {
            throw new IllegalArgumentException("input digest length error: export 32, actual " + hashData.length);
        }
        if (pvk.length != 32) {
            throw new IllegalArgumentException("input key length error: export 32, actual " + pvk.length);
        }
        // e
        BigInteger e = new BigInteger(StrUtil.byteToHex(hashData), 16);
        // k
        BigInteger k = null;
        ECPoint kp = null;
        BigInteger r = null;
        BigInteger s = null;
        BigInteger userD = null;
        BigInteger ecc_n = this.ecc_n;
        do {
            do {
                // 随机数取点k in 0-n
                k = new BigInteger(256, new SecureRandom());
                kp = peFromPvk(k.toString(16)); // 得到kp(x,y)
                // 计算: r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());
                r = r.mod(ecc_n);
                // 结果r等于0或（r+k）等于n则重算
            } while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));

            userD = new BigInteger(pvk);
            // s=  (((1 + dA)~-1)  *  (k - r*da )) mod n
            BigInteger da_1 = userD.add(BigInteger.ONE);
            da_1 = da_1.modInverse(ecc_n);
            s = r.multiply(userD);
            s = k.subtract(s).mod(ecc_n);
            s = da_1.multiply(s).mod(ecc_n);
            // 结果s等于0则重算
        } while (s.equals(BigInteger.ZERO));
        byte[] x = r.toByteArray();
        byte[] y = s.toByteArray();
        byte[] sv = new byte[64];
        System.arraycopy(x, x.length - 32, sv, 0, 32);
        System.arraycopy(y, y.length - 32, sv, 32, 32);
//        System.out.println(String.format("is\nr:%s\ns:%s",StrUtil.bigIntegerToHex(r), StrUtil.bigIntegerToHex(s)));
//        System.out.println(String.format("is:: r.length:%s\ts.length:%s",StrUtil.bigIntegerToHex(r).length(), StrUtil.bigIntegerToHex(s).length()));
        return sv;
    }

    /**
     * 验证签名（摘要， 签名值， 公钥）
     */
    public boolean sm2Verify(byte[] hashData, byte[] sv, byte[] pk) {
        // check before verifySign
        if (hashData.length != 32) {
            throw new IllegalArgumentException("input digest length error: export 32, actual " + hashData.length);
        }
        if (sv.length != 64) {
            throw new IllegalArgumentException("input signValue length error: export 64, actual " + sv.length);
        }
        if (pk.length != 65) {
            throw new IllegalArgumentException("input publicKey length error: export 65, actual " + pk.length);
        }
        // e
        BigInteger e = new BigInteger(StrUtil.byteToHex(hashData), 16);

        // r s
        byte[] svx = new byte[32];
        byte[] svy = new byte[32];
        System.arraycopy(sv, 0, svx, 0, 32);
        System.arraycopy(sv, 32, svy, 0, 32);
        BigInteger r = new BigInteger(StrUtil.byteToHex(svx), 16);
        BigInteger s = new BigInteger(StrUtil.byteToHex(svy), 16);
//        System.out.println(String.format("vs\nr:%s\ns:%s",StrUtil.bigIntegerToHex(r), StrUtil.bigIntegerToHex(s)));
        // k
        ECPoint k;
        ECPoint G = null;
        ECPoint Pa = null;
        BigInteger t = null;
        BigInteger R = null;
        BigInteger ecc_n = null;

        byte[] bx = new byte[32];
        byte[] by = new byte[32];
        System.arraycopy(pk, 1, bx, 0, 32);
        System.arraycopy(pk, 33, by, 0, 32);
        BigInteger x = new BigInteger(StrUtil.byteToHex(bx), 16);
        BigInteger y = new BigInteger(StrUtil.byteToHex(by), 16);
        Pa = new ECPoint.Fp(this.ecc_curve, new ECFieldElement.Fp(ecc_p, x), new ECFieldElement.Fp(ecc_p, y));

        G = this.ecc_bc_spec.getG();
        ecc_n = this.ecc_n;

        if (r.equals(BigInteger.ONE) || r.equals(ecc_n)) {
            return false;
        }
        if (s.equals(BigInteger.ONE) || s.equals(ecc_n)) {
            return false;
        }

        t = r.add(s).mod(ecc_n);
        if (t.equals(BigInteger.ZERO)) {
            return false;
        }

        //计算: k(x,y) = s*G + t*Pa
        k = G.multiply(s).add(Pa.multiply(t));

        //计算: R = (e+k.x) mod n
        R = e.add(k.getX().toBigInteger()).mod(ecc_n);
        //验证: R == r  true
        if (R.equals(r)) return true;
        return false;
    }


    // ----=====================================================----
    public BigInteger[] sm2Sign(byte[] md, BigInteger pvk) {
        SM3Digest sm3 = new SM3Digest();
        byte[] hashData = new byte[32];
        sm3.update(md, 0, md.length);
        sm3.doFinal(hashData, 0);
// e
        BigInteger e = new BigInteger(1, hashData);
// k
        BigInteger k = null;
        ECPoint kp = null;
        BigInteger r = null;
        BigInteger s = null;
        BigInteger userD = null;
        BigInteger ecc_n = this.ecc_n;
        do {
            do {
                // 随机数取点
                k = new BigInteger(256, new SecureRandom());
                kp = peFromPvk(k.toString(16)); // 得到kp(x,y)
                System.out.println("kp.x:\t" + StrUtil.bigIntegerToHex(kp.getX().toBigInteger()));
                // 计算
                // r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());
                r = r.mod(ecc_n);
                // 结果r等于0或（r+k）等于n则重算
            } while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));

            userD = pvk;
            // s=  (((1 + dA)~-1)  *  (k - r*da )) mod n
            BigInteger da_1 = userD.add(BigInteger.ONE);
            da_1 = da_1.modInverse(ecc_n);
            s = r.multiply(userD);
            s = k.subtract(s).mod(ecc_n);
            s = da_1.multiply(s).mod(ecc_n);
            // 结果s等于0则重算
        } while (s.equals(BigInteger.ZERO));
        return new BigInteger[]{r, s};
    }

    public boolean sm2Verify(byte[] md, BigInteger r, BigInteger s, byte[] pk) {
        SM3Digest sm3 = new SM3Digest();
        byte[] hashData = new byte[32];
        sm3.update(md, 0, md.length);
        sm3.doFinal(hashData, 0);
        // e
        BigInteger e = new BigInteger(1, hashData);
        // k
        ECPoint k;
        ECPoint G = null;
        ECPoint Pa = null;
        BigInteger t = null;
        BigInteger R = null;
        BigInteger ecc_n = null;
//        Pa = peFromPvk(prik.toString(16)); // 私钥生成公钥的方式

        byte[] bx = new byte[32];
        byte[] by = new byte[32];
        System.out.println("pk.len= " + pk.length);
        System.arraycopy(pk, 1, bx, 0, 32);
        System.arraycopy(pk, 33, by, 0, 32);

        BigInteger x = new BigInteger(StrUtil.byteToHex(bx), 16);
        BigInteger y = new BigInteger(StrUtil.byteToHex(by), 16);

        Pa = new ECPoint.Fp(this.ecc_curve, new ECFieldElement.Fp(ecc_p, x), new ECFieldElement.Fp(ecc_p, y));
        System.out.println("input pk:" + StrUtil.byteToHex(pk));
        System.out.println("input x:" + x.toString(16));
        System.out.println("check pa.x:" + Pa.getX().toBigInteger().toString(16));

        G = this.ecc_bc_spec.getG();

        ecc_n = this.ecc_n;

        if (r.equals(BigInteger.ONE) || r.equals(ecc_n)) {
            return false;
        }
        if (s.equals(BigInteger.ONE) || s.equals(ecc_n)) {
            return false;
        }

        t = r.add(s).mod(ecc_n);
        if (t.equals(BigInteger.ZERO)) {
            return false;
        }

        //k(x,y) = s*G + t*Pa
        k = G.multiply(s).add(Pa.multiply(t));

        System.out.println("k.x:\t" + StrUtil.bigIntegerToHex(k.getX().toBigInteger()));

        //R = (e+k.x) mod n
        R = e.add(k.getX().toBigInteger()).mod(ecc_n);
        //R == r  true
        if (R.equals(r)) return true;
        return false;
    }

    public static void main(String[] args) {
//        testSign();
//        testSign2();
//        testvs();

    }

    private static void testvs() {
        /*
        GMT 0003.5 标准数据验证 验签函数正确性验证
         */
        String hsv = "F5A03B06 48D2C463 0EEAC513 E1BB81A1 5944DA38 27D5B741 43AC7EAC EEE720B3" +
                "B1B6AA29 DF212FD8 763182BC 0D421CA1 BB9038FD 1F7F42D4 840B69C4 85BBC1AA";
        String hmd = "F0B43E94 BA45ACCA ACE692ED 534382EB 17E6AB5A 19CE7B31 F4486FDF C0D28640";
        String hpk = "04" +
                "09F9DF31 1E5421A1 50DD7D16 1E4BC5C6 72179FAD 1833FC07 6BB08FF3 56F35020" +
                "CCEA490C E26775A5 2DC6EA71 8CC1AA60 0AED05FB F35E084A 6632F607 2DA9AD13";
        GMTSM2 sm2Instance = GMTSM2.getInstance();
        boolean right = sm2Instance.sm2Verify(StrUtil.hexToByte(hmd.replaceAll(" ", "")),
                StrUtil.hexToByte(hsv.replaceAll(" ", "")),
                StrUtil.hexToByte(hpk.replaceAll(" ", "")));
        System.out.println(right);


    }

    private static void testSign2() {
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

        GMTSM2 sm2Instance = GMTSM2.getInstance();
        String plain = "234567890xxxxxxxxxx";

        // hashData
        byte[] hashData = sm2Instance.sm3Degest(plain.getBytes());
        // sign
        byte[] sv = sm2Instance.sm2Sign(hashData, StrUtil.hexToByte(prik));
        System.out.println("signValue:\t" + StrUtil.byteToHex(sv));
        System.out.println("signValue length:\t" + sv.length);
        System.out.println("signValue input hex length:\t" + StrUtil.byteToHex(sv).length());

        boolean right = sm2Instance.sm2Verify(hashData, sv, StrUtil.hexToByte(pubk));
        System.out.println("verifySign:\t" + right);


    }

    private static void testSign() {
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";


        GMTSM2 sm2Instance = GMTSM2.getInstance();
        String plain = "234567890xxxxxxxxxx";
        BigInteger[] sgig = sm2Instance.sm2Sign(plain.getBytes(), new BigInteger(prik, 16));

        System.out.println(String.format("r:len[%s]\t%s\ns:len[%s]\t%s", sgig[0].bitLength(), sgig[0], sgig[1].bitLength(), sgig[1]));
        System.out.println(String.format("r:<hex>\t%s\ns:<hex>\t%s", StrUtil.bigIntegerToHex(sgig[0]), StrUtil.bigIntegerToHex(sgig[1])));

        byte[] x = sgig[0].toByteArray();
        byte[] y = sgig[1].toByteArray();
        byte[] sv = new byte[64];
        System.out.println(String.format("r:\t%s\ns:\t%s", StrUtil.byteToHex(x), StrUtil.byteToHex(y)));
        System.arraycopy(x, 0, sv, 0, 32);
        System.arraycopy(y, 0, sv, 32, 32);
        System.out.println(String.format("sv:<hex>\t%s", StrUtil.byteToHex(sv)));

        boolean vs = sm2Instance.sm2Verify(plain.getBytes(), sgig[0], sgig[1], StrUtil.hexToByte(pubk));
        System.out.println(vs);
    }


}
