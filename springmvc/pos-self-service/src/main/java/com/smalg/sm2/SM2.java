package com.smalg.sm2;

import com.smalg.sm3.SM3Digest;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SM2 {


    //正式参数
    public static String[] ecc_param = {
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
            "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
    };

    private static SM2 instance;


    BigInteger ecc_p;
    BigInteger ecc_a;
    BigInteger ecc_b;
    BigInteger ecc_n;
    BigInteger ecc_gx;
    BigInteger ecc_gy;
    ECCurve.Fp ecc_curve;
    ECPoint.Fp ecc_point_g;
    ECDomainParameters ecc_bc_spec;
    ECKeyPairGenerator ecc_key_pair_generator;
    ECFieldElement ecc_gx_fieldelement;
    ECFieldElement ecc_gy_fieldelement;


    BigInteger privateKey;
    ECPoint publicKey;
    private ECPrivateKeyParameters ecpriv;
    private ECPublicKeyParameters ecpub;

    private SM2() {
        this.ecc_p = new BigInteger(ecc_param[0], 16);
        this.ecc_a = new BigInteger(ecc_param[1], 16);
        this.ecc_b = new BigInteger(ecc_param[2], 16);
        this.ecc_n = new BigInteger(ecc_param[3], 16);
        this.ecc_gx = new BigInteger(ecc_param[4], 16);
        this.ecc_gy = new BigInteger(ecc_param[5], 16);

        ecc_gx_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gx);
        ecc_gy_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gy);

        ecc_curve = new ECCurve.Fp(this.ecc_p, this.ecc_a, this.ecc_b);
        ecc_point_g = new ECPoint.Fp(this.ecc_curve, this.ecc_gx_fieldelement, this.ecc_gy_fieldelement);
//        , false);

        ecc_bc_spec = new ECDomainParameters(this.ecc_curve, this.ecc_point_g, this.ecc_n);

        ECKeyGenerationParameters ecc_ecgenparam;
        ecc_ecgenparam = new ECKeyGenerationParameters(ecc_bc_spec, new SecureRandom());

        ecc_key_pair_generator = new ECKeyPairGenerator();
        ecc_key_pair_generator.init(ecc_ecgenparam);


        AsymmetricCipherKeyPair key = ecc_key_pair_generator.generateKeyPair();
        ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ecpub = (ECPublicKeyParameters) key.getPublic();
        privateKey = ecpriv.getD();
        publicKey = ecpub.getQ();

    }

    public static SM2 getInstance() {
        if (instance == null) {
            synchronized (SM2.class) {
                if (instance == null) {
                    instance = new SM2();
                }
            }
        }
        return instance;
    }

    public BigInteger[] sm2Sign(byte[] md) {
        SM3Digest sm3 = new SM3Digest();
//        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
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
        BigInteger ecc_n = null;
        do {
            do {
//                // 私钥 公钥
//                ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();

                k = ecpriv.getD();

                kp = ecpub.getQ();
//                ecc_n = ecpriv.getParameters().getN();
//                userD = ecpriv.getD();

                ecc_n = this.ecc_n;
                userD = k;
                System.out.println("kp.x:\t"+StrUtil.bigIntegerToHex(kp.getX().toBigInteger()));
                // r = (e+x) mod n
                r = e.add(kp.getX().toBigInteger());

                r = r.mod(ecc_n);
            }
            while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecc_n));

            //s=  (((1 + dA)~-1)  *  (k - r*da )) mod n
            BigInteger da_1 = userD.add(BigInteger.ONE);
            da_1 = da_1.modInverse(ecc_n);
            // s
            s = r.multiply(userD);
            s = k.subtract(s).mod(ecc_n);
            s = da_1.multiply(s).mod(ecc_n);
        }
        while (s.equals(BigInteger.ZERO));

        return new BigInteger[]{r, s};
    }

    public boolean sm2Verify(byte[] md, BigInteger r, BigInteger s) {
        SM3Digest sm3 = new SM3Digest();
//        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
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
        Pa = ecpub.getQ();
        G = ecpub.getParameters().getG();

        ecc_n = ecpub.getParameters().getN();

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

        System.out.println("k.x:\t"+StrUtil.bigIntegerToHex(k.getX().toBigInteger()));

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
        SM2 sm2Instance = SM2.getInstance();
        String plain = "234567890xxxxxxxxxx";
        BigInteger[] sgig = sm2Instance.sm2Sign(plain.getBytes());

        System.out.println(String.format("r:len[%s]\t%s\ns:len[%s]\t%s", sgig[0].bitLength(), sgig[0], sgig[1].bitLength(), sgig[1]));
        System.out.println(String.format("r:<hex>\t%s\ns:<hex>\t%s", StrUtil.bigIntegerToHex(sgig[0]), StrUtil.bigIntegerToHex(sgig[1])));

        byte[] x = StrUtil.hexToByte(StrUtil.bigIntegerToHex(sgig[0]));
        byte[] y = StrUtil.hexToByte(StrUtil.bigIntegerToHex(sgig[1]));
        byte[] sv = new byte[64];
        sv[0] = 4;
        System.arraycopy(x, 0, sv, 0, 32);
        System.arraycopy(y, 0, sv, 32, 32);
        System.out.println(String.format("sv:<hex>\t%s", StrUtil.byteToHex(sv)));

        boolean vs = sm2Instance.sm2Verify(plain.getBytes(), sgig[0], sgig[1]);
        System.out.println(vs);
    }


}
