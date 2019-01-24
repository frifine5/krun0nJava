package com.sm2;

import com.cer.SM2CaCert;
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
import java.util.Base64;


/**
 * java 实现的GMT 0003.1 2 的SM2签名验签
 *
 * @author WangChengyu
 * 2018/9/28 10:45
 */
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
     * @return {puk, pvk}
     */
    public String[] genPairOnString() {
        // 产生随机数
        BigInteger da = genKinN();
        byte[] skarr = da.toByteArray();
        byte[] bsk = new byte[32];
        int lsk = skarr.length;
        if (lsk < 32) {
            System.arraycopy(skarr, 0, bsk, 32 - lsk, lsk);
        } else {
            System.arraycopy(skarr, skarr.length - 32, bsk, 0, 32);
        }
        String hexPrivateKey = StrUtil.byteToHex(bsk);
//        System.out.println("bit len:sk= "+da.bitLength());

        ECPoint p = ecc_bc_spec.getG().multiply(da);
        byte[] bpx = p.getX().toBigInteger().toByteArray();
        byte[] bpy = p.getY().toBigInteger().toByteArray();
        byte[] bpk = new byte[64];
//        System.out.println(String.format("bit len:x = %s, y = %s",
//                p.getX().toBigInteger().bitLength(),
//                p.getY().toBigInteger().bitLength()));
//        System.out.println(String.format("len:x = %s, y = %s", bpx.length, bpy.length));
//        System.out.println(String.format("gen pk:\n%s\n%s", StrUtil.byteToHex(bpx), StrUtil.byteToHex(bpy)));
        int lx = bpx.length;
        if (lx < 32) {
            System.arraycopy(bpx, 0, bpk, 32 - lx, lx);
        } else {
            System.arraycopy(bpx, lx - 32, bpk, 0, 32);
        }
        int ly = bpy.length;
        if (ly < 32) {
            System.arraycopy(bpy, 0, bpk, 32 - ly, ly);
        } else {
            System.arraycopy(bpy, ly - 32, bpk, 32, 32);
        }
        String hexPublicKey = "04" + StrUtil.byteToHex(bpk);
        return new String[]{hexPublicKey, hexPrivateKey};
    }

    /**
     * 根据私钥生成公钥
     */
    public String calcPkfrSk(byte[] sk){
        BigInteger da = new BigInteger(1, sk);
        ECPoint p = ecc_bc_spec.getG().multiply(da);
        byte[] bpx = p.getX().toBigInteger().toByteArray();
        byte[] bpy = p.getY().toBigInteger().toByteArray();
        byte[] bpk = new byte[64];
        int lx = bpx.length;
        if (lx < 32) {
            System.arraycopy(bpx, 0, bpk, 32 - lx, lx);
        } else {
            System.arraycopy(bpx, lx - 32, bpk, 0, 32);
        }
        int ly = bpy.length;
        if (ly < 32) {
            System.arraycopy(bpy, 0, bpk, 32 - ly, ly);
        } else {
            System.arraycopy(bpy, ly - 32, bpk, 32, 32);
        }
        return "04" + StrUtil.byteToHex(bpk);
    }


    /**
     * from P = k * G 得出曲线上的点（x, y）
     *
     * @param pvk
     * @return
     */
    private ECPoint calcPa(String pvk) {
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
            } while (da.compareTo(ZERO) <= 0);//da.equals(ZERO));
        } while (da.compareTo(ecc_n) >= 0);
        return da;
    }

    // ++++=====================================================++++

    /**
     * 带公钥的摘要 sm3(pk||m)
     */
    public byte[] sm3Degest(byte[] data, byte[] puk) {
        String hentl = "0080";
        String hid = "31323334353637383132333435363738";
        String ha = "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC";
        String hb = "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93";
        String hgx = "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7";
        String hgy = "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0";
        String hpk = StrUtil.byteToHex(puk);

        // 1
        String pre1 = hentl+hid+ha+hb+hgx+hgy+hpk.substring(2);
//        System.out.println(pre1);
        byte[] z = sm3Degest(StrUtil.hexToByte(pre1));

        // 2
        byte[] zm = StrUtil.hexToByte(StrUtil.byteToHex(z)+StrUtil.byteToHex(data));
        byte[] md = sm3Degest(zm);
        return md;
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

    //    public byte[] sm2Sign(byte[] hashData, byte[] pvk) {
    public BigInteger[] sm2Sign2(byte[] hashData, byte[] pvk) {
        // check before calc
        if (hashData.length != 32) {
            throw new IllegalArgumentException("input digest length error: export 32, actual " + hashData.length);
        }
        if (pvk.length != 32) {
            throw new IllegalArgumentException("input key length error: export 32, actual " + pvk.length);
        }
        // e
        BigInteger e = new BigInteger(hashData);
        // k
        BigInteger k = null;
        ECPoint kp = null;
        BigInteger r = null;// 签名出参 r
        BigInteger s = null;// 签名出参 s
        BigInteger userD = new BigInteger(pvk);// 私钥d
        BigInteger ecc_n = this.ecc_n;
        do {
            do {
                // 随机数取点k in 0-n
                k = genKinN();
                kp = calcPa(k.toString(16)); // 得到kp(x,y)
                // 计算: r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());
                r = r.mod(ecc_n);
                // 结果r等于0或（r+k）等于n则重算
            } while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));

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
        int lx = x.length;
        int ly = y.length;
        if (lx < 32) {
            System.arraycopy(x, 0, sv, 32 - lx, lx);
        } else {
            System.arraycopy(x, lx - 32, sv, 0, 32);
        }
        if (ly < 32) {
            System.arraycopy(y, 0, sv, 32 - ly, ly);
        } else {
            System.arraycopy(y, ly - 32, sv, 32, 32);
        }
//        System.out.println(String.format("is\nr:%s\ns:%s",StrUtil.bigIntegerToHex(r), StrUtil.bigIntegerToHex(s)));
//        System.out.println(String.format("is:: r.length:%s\ts.length:%s",StrUtil.bigIntegerToHex(r).length(), StrUtil.bigIntegerToHex(s).length()));
//        return sv;
        return new BigInteger[]{r, s};
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
        BigInteger e = new BigInteger(1, hashData);
        // k
        BigInteger k = null;
        ECPoint kp = null;
        BigInteger r = null;// 签名出参 r
        BigInteger s = null;// 签名出参 s
        BigInteger userD = new BigInteger(1, pvk);// 私钥d
        BigInteger ecc_n = this.ecc_n;
        do {
            do {
                // 随机数取点k in 0-n
                k = genKinN();
                kp = calcPa(k.toString(16)); // 得到kp(x,y)
                // 计算: r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());
                r = r.mod(ecc_n);
                // 结果r等于0或（r+k）等于n则重算
            } while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));

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
        int lx = x.length;
        int ly = y.length;
        if (lx < 32) {
            System.arraycopy(x, 0, sv, 32 - lx, lx);
        } else {
            System.arraycopy(x, lx - 32, sv, 0, 32);
        }
        if (ly < 32) {
            System.arraycopy(y, 0, sv, 32 - ly, ly);
        } else {
            System.arraycopy(y, ly - 32, sv, 32, 32);
        }
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
        BigInteger e = new BigInteger(1, hashData);

        // r s
        byte[] svx = new byte[32];
        byte[] svy = new byte[32];
        System.arraycopy(sv, 0, svx, 0, 32);
        System.arraycopy(sv, 32, svy, 0, 32);
        BigInteger r = new BigInteger(1, svx);
        BigInteger s = new BigInteger(1, svy);
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
        BigInteger x = new BigInteger(1, bx);
        BigInteger y = new BigInteger(1, by);


        Pa = new ECPoint.Fp(this.ecc_curve, new ECFieldElement.Fp(ecc_p, x), new ECFieldElement.Fp(ecc_p, y));
//        Pa = ecc_curve.decodePoint(pk);


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

        //R = (e+k.x) mod n
        R = e.add(k.getX().toBigInteger()).mod(ecc_n);
        //R == r  true
        if (R.equals(r)) return true;
        return false;
    }


    // ----=====================================================----
    public BigInteger[] sm2Sign(byte[] hashData, ECPrivateKeyParameters ecpriv) {
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
                k = genKinN();
                kp = calcPa(k.toString(16)); // 得到kp(x,y)
                // 计算  r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());
                r = r.mod(ecc_n);
                // 结果r等于0或（r+k）等于n则重算
            } while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));
            userD = ecpriv.getD();
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

    public boolean sm2Verify(byte[] hashData, BigInteger[] sv, ECPublicKeyParameters ecpub) {
        // e
        BigInteger e = new BigInteger(1, hashData);
        // k
        ECPoint k;
        ECPoint G = null;
        ECPoint Pa = null;
        BigInteger t = null;
        BigInteger R = null;
        BigInteger ecc_n = null;
//        Pa = calcPa(prik.toString(16)); // 私钥生成公钥的方式

        Pa = ecpub.getQ();
        G = this.ecc_bc_spec.getG();
        ecc_n = this.ecc_n;

        BigInteger r = sv[0];
        BigInteger s = sv[1];
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

        //R = (e+k.x) mod n
        R = e.add(k.getX().toBigInteger()).mod(ecc_n);
        //R == r  true
        if (R.equals(r)) return true;
        return false;
    }

    public static void main(String[] args) {

//        testSign2();
//        testSign3();
        testvs();
//        testpkget();
//        System.out.println(testSign4());

//        fooTest();

//        test4();

//        try {
//            chcert1();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        testSign5();

    }

    private static boolean testSign5() {
        GMTSM2 sm2Instance = GMTSM2.getInstance();
        AsymmetricCipherKeyPair key = sm2Instance.genPairOnBouncycastle();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();

//        System.out.println(StrUtil.byteToHex(sm2Instance.sm3Degest("1234567890ffffff".getBytes())));
        // 测试用摘要
        String hxDm = "B6C95B4228A338ACCDEF23D2CF7F548C06974479DB4BB0630479FE3A43DD2386";
        BigInteger[] svs = sm2Instance.sm2Sign(StrUtil.hexToByte(hxDm), ecpriv);
        boolean right1 = sm2Instance.sm2Verify(StrUtil.hexToByte(hxDm), svs, ecpub);

//        for (int i = 0; i < 10; i++) {
//            System.out.printf("%s ",sm2Instance.sm2Verify(StrUtil.hexToByte(hxDm), svs, ecpub));
//        }
//        System.out.println();


//        System.out.println(String.format("vs:: 1-%s", right1));
        // sv and pk change;
        String bsvr = StrUtil.byteToHex(svs[0].toByteArray());
        String bsvs = StrUtil.byteToHex(svs[1].toByteArray());

        String hexsv = bsvr.substring(bsvr.length()-64, bsvr.length()) + bsvs.substring(bsvs.length()-64, bsvs.length());
        System.out.println("hexsv:\t  "+hexsv);

        String bpkx = StrUtil.byteToHex(ecpub.getQ().getX().toBigInteger().toByteArray());
        String bpky = StrUtil.byteToHex(ecpub.getQ().getY().toBigInteger().toByteArray());

        String hexpk = "04"+bpkx.substring(bpkx.length()-64, bpkx.length()) + bpky.substring(bpky.length()-64, bpky.length());
        System.out.println("hexpk:\t"+hexpk);

        boolean right2 = sm2Instance.sm2Verify(StrUtil.hexToByte(hxDm), StrUtil.hexToByte(hexsv), StrUtil.hexToByte(hexpk));



        System.out.println(String.format("vs:: 1-%s, 2-%s", right1, right2));




        return right1 && right2;

    }


    private static void chcert1() throws Exception {
//        String p = "C:\\Users\\49762\\Desktop\\a.cer"; // C:\Users\49762\Desktop\
//        byte[] dcert = FileUtil.fromDATfile(p);


        String subCert = "MIICADCCAaSgAwIBAgIIEQArR8Mi0b0wDAYIKoEcz1UBg3UFADB2MQ8wDQYDVQQDDAZHb21haW4xCzAJBgNVBAYMAkNOMRAwDgYDVQQHDAdCZWlKaW5nMRAwDgYDVQQIDAdCZWlKaW5nMREwDwYDVQQKDAhTZWN1cml0eTEfMB0GA1UECwwWRGV2ZWxvcG1lbnQgRGVwYXJ0bWVudDAeFw0xODA5MjgxMDQ0MzJaFw0xOTA5MjgxMDQ0MzJaMIGRMSQwIgYDVQQDDBvnu7Xnq7nluILmrovnlr7kurrogZTlkIjkvJoxFTATBgNVBAcMDOWbm+W3nea1i+ivlTEVMBMGA1UECAwM5Zub5bed5rWL6K+VMSQwIgYDVQQKDBvnu7Xnq7nluILmrovnlr7kurrogZTlkIjkvJoxFTATBgNVBAsMDOWbm+W3nea1i+ivlTBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABGn5Jlt0R0811aOjLvT/+RIJGUOxAdrobn/A2OPF4lEBEcutjjsD5hdZwVHqemnl3M/yOrGxrOfBNR691o4Uj7swDAYIKoEcz1UBg3UFAANIADBFAiAIsIxcvnTkQBixnqDsoEBmFNmmFZl1x5Pe6Wt9LAYT7gIhANaFQsuvVYPqwYEtmKPCd4vyvd78pnzRoxXUN9+gltkD";
        String rootCert = "MIICRjCCAeqgAwIBAgIIESIzRFVmd4gwDAYIKoEcz1UBg3UFADB2MR8wHQYDVQQLDBZEZXZlbG9wbWVudCBEZXBhcnRtZW50MREwDwYDVQQKDAhTZWN1cml0eTEQMA4GA1UECAwHQmVpSmluZzEQMA4GA1UEBwwHQmVpSmluZzEPMA0GA1UEAwwGR29tYWluMQswCQYDVQQGDAJDTjAeFw0xODA5MjgwMzUwMjNaFw0yODA5MjUwMzUwMjNaMHYxHzAdBgNVBAsMFkRldmVsb3BtZW50IERlcGFydG1lbnQxETAPBgNVBAoMCFNlY3VyaXR5MRAwDgYDVQQIDAdCZWlKaW5nMRAwDgYDVQQHDAdCZWlKaW5nMQ8wDQYDVQQDDAZHb21haW4xCzAJBgNVBAYMAkNOMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEOs4LoKmyRfZA0peDoAZdiaZa7mCx6mznbRjm9SN17EZHA/TDw/X0zjpFk9ce64kdLERcy1abTqwMRQOI3z5/OqNgMF4wDwYDVR0TAQH/BAUwAwEB/zAdBgNVHQ4EFgQU2jmj7l5rSw0yVb/vlWAYkK/YBwkwHwYDVR0jBBgwFoAU2jmj7l5rSw0yVb/vlWAYkK/YBwkwCwYDVR0PBAQDAgXgMAwGCCqBHM9VAYN1BQADSAAwRQIgC6IBUqiKLf6skiNgcxFqi1qnWHKjluEs6lPhHqPwsl0CIQCYdWd8mO4Lkcrg4Lt4mDgzuselqD/1es99kNfW7Gd6rA==";
        byte[] asnBtSubCert = Base64.getDecoder().decode(subCert);
        byte[] asnBtRootCert = Base64.getDecoder().decode(rootCert);

        GMTSM2 sm2 = GMTSM2.getInstance();
        // root cert verify sign
        byte[] data0 = SM2CaCert.getSM2TBSCertificateDate(asnBtRootCert);
        byte[] dpk0 = SM2CaCert.getSM2PublicKey(asnBtRootCert);
        byte[] bsv0 = SM2CaCert.getSM2signatureValue(asnBtRootCert);
        byte[] md = sm2.sm3Degest(data0);
        boolean rootRight = sm2.sm2Verify(md, bsv0, dpk0);
        System.out.println("根证书验证："+ rootRight);

        // sub cert verify sign
        byte[] data1  = SM2CaCert.getSM2TBSCertificateDate(asnBtSubCert);
        byte[] dpk1 = SM2CaCert.getSM2PublicKey(asnBtRootCert);// 公钥取根证书的
        byte[] bsv1 = SM2CaCert.getSM2signatureValue(asnBtSubCert);
        byte[] md1 = sm2.sm3Degest(data1);
        boolean subRight = sm2.sm2Verify(md1, bsv1, dpk1);
        System.out.println("子证书验证："+ subRight);


    }


    private static boolean testSign4() {
        GMTSM2 sm2Instance = GMTSM2.getInstance();
        AsymmetricCipherKeyPair key = sm2Instance.genPairOnBouncycastle();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();

//        System.out.println(StrUtil.byteToHex(sm2Instance.sm3Degest("1234567890ffffff".getBytes())));
        // 测试用摘要
        String hxDm = "B6C95B4228A338ACCDEF23D2CF7F548C06974479DB4BB0630479FE3A43DD2386";
        BigInteger[] svs = sm2Instance.sm2Sign(StrUtil.hexToByte(hxDm), ecpriv);
        boolean right = sm2Instance.sm2Verify(StrUtil.hexToByte(hxDm), svs, ecpub);
        return right;

    }


    private static void test4() {
        GMTSM2 sm2 = GMTSM2.getInstance();
        BigInteger bi = sm2.genKinN();
        System.out.println(String.format("bit len:%s, v:%s", bi.bitLength(), bi));
        System.out.println(String.format("len:%s, v:%s", bi.toString().length(), bi));
        System.out.println(new BigInteger(bi.toString(), 10).compareTo(bi) == 0);
        System.out.println(new BigInteger(bi.toString(), 10).compareTo(new BigInteger(bi.toByteArray())) == 0);
        System.out.println(new BigInteger(bi.toString(), 10).compareTo(new BigInteger(bi.toString(16), 16)) == 0);
        System.out.println();
        byte[] arr = bi.toByteArray();
        byte[] newArr = new byte[arr.length + 2];
        System.arraycopy(arr, 0, newArr, 2, arr.length);
        System.out.println(bi.compareTo(new BigInteger(newArr)) == 0);

    }

    private static void fooTest() {
        long st = System.currentTimeMillis();
        int foo = 10;
        int suc = 0;
        int ex = 0;
        for (int i = 0; i < foo; i++) {
            try {
                boolean r = testSign3();
                if (r) suc++;
            } catch (Exception e) {
                e.printStackTrace();
                ex++;
                continue;
            }
        }
        long et = System.currentTimeMillis();
        System.out.println(String.format("execute:%s, success:%s, exception:%s", foo, suc, ex));
        System.out.println(String.format("execute time = %s(ms)", (et - st)));
    }

    private static void testpkget() {
        String pk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";
        //00F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE20
        //5EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A
        GMTSM2 sm2Instance = GMTSM2.getInstance();
        ECPoint p = sm2Instance.ecc_curve.decodePoint(StrUtil.hexToByte(pk));
        System.out.println(StrUtil.byteToHex(p.getX().toBigInteger().toByteArray()));
        System.out.println(StrUtil.byteToHex(p.getY().toBigInteger().toByteArray()));
    }

    private static boolean testSign3() {
        GMTSM2 sm2Instance = GMTSM2.getInstance();
        AsymmetricCipherKeyPair key = sm2Instance.genPairOnBouncycastle();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();

        byte[] esk = ecpriv.getD().toByteArray();
        int lsk = esk.length + 32;
        byte[] newPrik = new byte[lsk];
        System.arraycopy(esk, 0, newPrik, lsk - esk.length, esk.length);

        byte[] epkx = ecpub.getQ().getX().toBigInteger().toByteArray();
        byte[] epky = ecpub.getQ().getY().toBigInteger().toByteArray();
        int lpkx = epkx.length + 32;
        int lpky = epky.length + 32;
        byte[] nepkx = new byte[lpkx];
        byte[] nepky = new byte[lpky];
        System.arraycopy(epkx, 0, nepkx, lpkx - epkx.length, epkx.length);
        System.arraycopy(epky, 0, nepky, lpky - epky.length, epky.length);
        String prik = StrUtil.byteToHex(newPrik).substring(lsk * 2 - 64, lsk * 2);

        String pubk = "04" + StrUtil.byteToHex(nepkx).substring(lpkx * 2 - 64, lpkx * 2)
                + StrUtil.byteToHex(nepky).substring(lpky * 2 - 64, lpky * 2);

        String plain = "234567890xxxxxxxxxx";

        // hashData
        byte[] hashData = sm2Instance.sm3Degest(plain.getBytes());
        // sign
        byte[] sv = sm2Instance.sm2Sign(hashData, StrUtil.hexToByte(prik));
//        System.out.println("signValue:\t" + StrUtil.byteToHex(sv));
//        System.out.println("signValue length:\t" + sv.length);
//        System.out.println("signValue input hex length:\t" + StrUtil.byteToHex(sv).length());

        boolean right = sm2Instance.sm2Verify(hashData, sv, StrUtil.hexToByte(pubk));
//        System.out.println("verifySign:\t" + right);
        return right;

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

        String bpk = Base64.getEncoder().encodeToString(StrUtil.hexToByte(hpk.replaceAll(" ", "")));
        String bsv = Base64.getEncoder().encodeToString(StrUtil.hexToByte(hsv.replaceAll(" ", "")));
        String bdg = Base64.getEncoder().encodeToString(StrUtil.hexToByte(hmd.replaceAll(" ", "")));

        System.out.println(String.format("公钥:\t%s\n摘要:\t%s\n签名值:\t%s", bpk, bdg, bsv));


    }

    private static boolean testSign2() {
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


        return right;
    }





}
