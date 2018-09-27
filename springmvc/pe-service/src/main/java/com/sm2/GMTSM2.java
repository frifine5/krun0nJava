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
     * 取点（生成0-n）的随机数
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

    public boolean sm2Verify(byte[] md, BigInteger r, BigInteger s, byte[] pk, BigInteger prik) {
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
        System.out.println("pk.len= "+pk.length);
        System.arraycopy(pk, 1, bx, 0, 32);
        System.arraycopy(pk, 33, by, 0, 32);

        BigInteger x = new BigInteger(StrUtil.byteToHex(bx), 16);
        BigInteger y = new BigInteger(StrUtil.byteToHex(by), 16);

        Pa = new ECPoint.Fp(this.ecc_curve, new ECFieldElement.Fp(ecc_p, x), new ECFieldElement.Fp(ecc_p, y));
        System.out.println("input pk:"+ StrUtil.byteToHex(pk));
        System.out.println("input x:"+ x.toString(16));
        System.out.println("check pa.x:"+ Pa.getX().toBigInteger().toString(16));

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
        testSign();


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

        byte[] x = StrUtil.hexToByte(StrUtil.bigIntegerToHex(sgig[0]));
        byte[] y = StrUtil.hexToByte(StrUtil.bigIntegerToHex(sgig[1]));
        byte[] sv = new byte[64];
        System.arraycopy(x, 0, sv, 0, 32);
        System.arraycopy(y, 0, sv, 32, 32);
        System.out.println(String.format("sv:<hex>\t%s", StrUtil.byteToHex(sv)));

        boolean vs = sm2Instance.sm2Verify(plain.getBytes(), sgig[0], sgig[1], new BigInteger(pubk, 16).toByteArray(), new BigInteger(prik, 16));
        System.out.println(vs);
    }


}
