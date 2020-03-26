package com.p7v;

import com.cer.SM2CaCert;
import com.common.utils.FileUtil;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.bouncycastle.asn1.*;
import org.junit.Test;
import java.io.IOException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;



/**
 * 对pkcs#7签名结构的验证：结构和签名值
 *  包括rsa 和 sm2
 * @author WangChengyu
 * 2020/3/25 16:10
 */
public class P7sv {


    @Test   // 验证SM2的p7b签名
    public void test2()throws Exception{
        String p = "D:\\home\\dtmp\\tmp-郑\\";
        String n = "p7SignValue-GDCA.dat";

        String p7bStr = "";
//        p7bStr = new String(FileUtil.fromDATfile(p+n));

//        byte[] src = Base64.getDecoder().decode(p7bStr);
        byte[] src = FileUtil.fromDATfile(p+n);
        System.out.println("p7b-base64:");
        System.out.println(Base64.getEncoder().encodeToString(src));

        ASN1Sequence root = (ASN1Sequence)ASN1Sequence.fromByteArray(src); // 1级
        ASN1ObjectIdentifier ctnType = (ASN1ObjectIdentifier)root.getObjectAt(0);
        System.out.println(ctnType);

        ASN1TaggedObject contentTag = (ASN1TaggedObject)root.getObjectAt(1);
//        System.out.println(contentTag);

        System.out.println("二级：");
        ASN1Sequence content = (ASN1Sequence)contentTag.getObjectParser(0, true);// 2级
        System.out.println(content);

        // 原文
//        ASN1Sequence signedDataSeq = (ASN1Sequence)content.getObjectAt(2);
//        ASN1TaggedObject ctnTag1 = (ASN1TaggedObject)signedDataSeq.getObjectAt(1);
//        ASN1OctetString ctnOct = (ASN1OctetString)ctnTag1.getObjectParser(0, true);
//        System.out.println(ctnOct);
//        byte[] data = ctnOct.getOctets();// 原文的byte[]
////        System.out.println(new String(data));


        byte[] data = null;

        // 读取证书和公钥
        ASN1TaggedObject cerTag0 = (ASN1TaggedObject)content.getObjectAt(3);
        ASN1Sequence cerSeq = (ASN1Sequence)cerTag0.getObjectParser(0, true);
        int certSize = cerSeq.size();
        ASN1Sequence certNeed = cerSeq;
        if(cerSeq.getObjectAt(certSize-1) instanceof ASN1Sequence){
            certNeed = (ASN1Sequence)cerSeq.getObjectAt(certSize - 1);
        }


        // 解析签名者信息
        int lastIndex = content.size()-1;
        ASN1Set signerInfos =(ASN1Set)content.getObjectAt(lastIndex);
        ASN1Sequence signerInfo0 = (ASN1Sequence)signerInfos.getObjectAt(0);

        // 按pkcs#7对签名者要认证属性进行摘要和组包后作为签名入参
        // 获取签名待摘要数据  authenticatedAttributes
        ASN1Sequence digAlgOid = (ASN1Sequence)signerInfo0.getObjectAt(2);
        ASN1TaggedObject authAttrsTag = (ASN1TaggedObject)signerInfo0.getObjectAt(3);
        ASN1Sequence digAlgOid2 = (ASN1Sequence)signerInfo0.getObjectAt(4); // 签名算法

        // 获取attribute集合-->获得消息摘要过程的最终摘要,同时也是签名的原文
        ASN1Sequence attrs = (ASN1Sequence)authAttrsTag.getObjectParser(0, true);
        ASN1EncodableVector attVect = new ASN1EncodableVector();
        for (int j= 0; j<attrs.size(); j++){
            attVect.add((ASN1Sequence)attrs.getObjectAt(j));
        }
        DERSet attSets = new DERSet(attVect);
        byte[] originData = attSets.getEncoded(ASN1Encoding.DER);


        // 验签过程
        data = originData;
        // 获得p7中的签名值
        byte[] signed = null;
        for(int i = 0; i< signerInfo0.size(); i++){
            ASN1Encodable objAti = signerInfo0.getObjectAt(i);
            if(objAti instanceof ASN1OctetString){
                ASN1OctetString encryptedDigest = (ASN1OctetString)objAti;
                signed = encryptedDigest.getOctets();
                System.out.println(Base64.getEncoder().encodeToString(signed));
            }
        }
        System.out.println("验签>>>");

        byte[] pk = SM2CaCert.getSM2PublicKey(certNeed.getEncoded());
        ASN1Sequence svSeq = (ASN1Sequence)ASN1Sequence.fromByteArray(signed);
        ASN1Integer ar = (ASN1Integer)svSeq.getObjectAt(0);
        ASN1Integer as = (ASN1Integer)svSeq.getObjectAt(1);

        GMTSM2 sm2 = GMTSM2.getInstance();
        byte[] md = SM3Util.sm3Digest(data, pk);
        String vsInfoTotal = String.format("解析得到的验签的各入参数据 >>>\n签名摘要算法：\t%s\n" +
                        "消息摘要结果（HEX）：\t%s\n公钥（编码结构的HEX）：\t%s\n签名值（HEX）：\t%s\n",
                "SM3WithSM2", Util.byteToHex(data), Util.byteToHex(pk), Util.byteToHex(signed));
        System.out.println(vsInfoTotal);
        String logPath = "C:\\Users\\49762\\Desktop\\vsLog.txt";
        FileUtil.appendCtx2File1(logPath, n + "\n");
        FileUtil.appendCtx2File1(logPath, vsInfoTotal);


        boolean isSuc = sm2.sm2Verify(md, ar.getValue(), as.getValue(), pk);
        System.out.println("验签结果:\t" + isSuc);

        String logRtn = ">>> 验证结果:\t" + isSuc;
        System.out.println(logRtn);
        FileUtil.appendCtx2File1(logPath, logRtn + "\n");

    }



    @Test   // 验rsa的p7b签名
    public void test1()throws Exception{
        String p = "D:\\home\\dtmp\\tmp-郑\\";
        String n = "p7SignValue-bjca.dat";
//        n = "p7SignValue-liaoning.dat";

        String p7bStr = "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";
//        p7bStr = "MIIFQQYJKoZIhvcNAQcCoIIFMjCCBS4CAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCAzMwggMvMIICF6ADAgECAgR/WTsBMA0GCSqGSIb3DQEBCwUAMEgxCjAIBgNVBAYTATExCjAIBgNVBAgTATExCjAIBgNVBAcTATExCjAIBgNVBAoTATExCjAIBgNVBAsTATExCjAIBgNVBAMTATEwHhcNMTkxMTE0MDgzOTA1WhcNMjAwMjEyMDgzOTA1WjBIMQowCAYDVQQGEwExMQowCAYDVQQIEwExMQowCAYDVQQHEwExMQowCAYDVQQKEwExMQowCAYDVQQLEwExMQowCAYDVQQDEwExMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8L0JA0tLS84FXDRNcmQNlgb3yuxgOgMjkn0DBpQMMvXq22hOxgc6RXYNIuO+u4An4K+ln7yAuo1DfTM5eusO1/o+TOhaoyDOAYXi2L8P5ER1x6TGpcBfWlla4j1p4S/B/nbgAgHT7/lbKvt+YIaeeS2znSKANzspPyeLvk4LQusMrhmfB0yZ2Ox7nc2wqjCkgJ6BeItLShe9HeDmSrF5OR7uYgnB3ts/qJOWbEXD1bPJgWtmT6PG/QqZym7h/xQVBBxfGwX3sSrZahCGlWodbEkApBzmsQtm6y59QdlIv4vljGxB7LBR0BqFK2FU4ZCsCx1AQ5Mab65EKJ3lWrdi7QIDAQABoyEwHzAdBgNVHQ4EFgQUhOaSei/m0lpEk9lpJ4CcsTeC1K0wDQYJKoZIhvcNAQELBQADggEBAJeJdhfcKAIc+6TgxpKD+mabl/yWxI7Eq5eGNJfPDExa/rP2Ajyyvdg3ik4Yo5ikF/2QLRg9pk7X7qh+KVCDPrVBN4oC0QL7M3KuWKN7C1NUztTVdVIIYLjYAHv/TSKfzJL93yAj5uLiO05lSXxQXSq1nIRfljP+082bDVsiYkJIIfem6nUlcOTRvIzoNrm2z65SRX5YUtvG1aFfVdqn8NKIp821AQdH0flGDYDOhMBB0BxlthEvoVyexm9+KqWEJno3mQ5UFZPnBi7o6O1HEvl4I+eSZOTyaTIhY5lg77YZRLxqkE2lMYUKcsmlKYWPLNT0P6moGhnWIl0roDlYGJwxggHWMIIB0gIBATBQMEgxCjAIBgNVBAYTATExCjAIBgNVBAgTATExCjAIBgNVBAcTATExCjAIBgNVBAoTATExCjAIBgNVBAsTATExCjAIBgNVBAMTATECBH9ZOwEwCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTIwMDExNzA5MDczOFowIwYJKoZIhvcNAQkEMRYEFHqRUynAFMZnSd2hrgJr6+wRWNH+MA0GCSqGSIb3DQEBAQUABIIBABQhSJZckMeIUV12xmo5EnWyo7K3QzB7SpZP0NqcDkGPV1AJdkUF2XAqQ4tOxlOtSNSRl4hzuNvOGIcsITUxRJsVXxbSnbDYasv/j8O/Uc6dFthW2zPmKjFVfLhn+s5oVVNukHW8AQHSUbswSxT6BkJ7hcj8RlbO+2EWRpheQoPaZdLDFf8AvWJZUl20M35Z4I57I4URnPDcnAJKjrni1bI0h31H/o8AjZIJPnXNt58CnR01RrGNI7BblwxNHu0JMB+VmBxkgt/s912MU9rUtskRNYNO4tqZJ4ua9oObQ02rkges/FDSkqfpiwKvFTE5K6mDyj9pfTLWrBodLYdLMNE=";

        p7bStr = "MIIFqgYJKoZIhvcNAQcCoIIFmzCCBZcCAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGgggOJMIIDhTCCAm2gAwIBAgIJIBA1AAAAQJUJMA0GCSqGSIb3DQEBBQUAMEIxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ8wDQYDVQQLDAZQRVJTT04xEzARBgNVBAMMCk1TU1BDQSBSU0EwHhcNMTcxMjA4MDcyNTAwWhcNMjIxMjA4MDgyNTAwWjBxMQswCQYDVQQGDAJDTjEwMC4GA1UECgwn5YyX5Lqs5biC5L2P5oi/5ZKM5Z+O5Lmh5bu66K6+5aeU5ZGY5LyaMTAwLgYDVQQDDCfljJfkuqzluILkvY/miL/lkozln47kuaHlu7rorr7lp5TlkZjkvJowggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCUHO6qtauQ1Ld/+43YTyg2S1+YoOAlzF+AmUYyTmxCKuS1qSnVSqRk+6EXW9v87H+UJvxd24Kzun3JGcumV2cuhZSb4NbVwzZG3GIqqh9I7WKCl9U5Epe6T2wieJZZsR72/z2vm0fDdlD3FsATgZi0WSrSUcT5QO/cwjwYvud4CSXQd6ZkLs7ole20A4Aa554M2/GtaPJNiOQUbGls976Wt+hotLA88TgnoBAKjnuwUqpgiXvNsdy9IjEf+pOxCzPAeHOCSrEU843IWn5j79/HeBvKsaOJ76hX4+UqRtrDNnOYqWZoLAvwys+uY2nU4XKBhbMrU/OzAy9u0F3u2HSxAgMBAAGjTzBNMB8GA1UdIwQYMBaAFDdJ2+awfg5xisXbeZ5jlvi8XFaTMB0GA1UdDgQWBBTCqUbtunOumfz1clZqLSpNCI5AuDALBgNVHQ8EBAMCB4AwDQYJKoZIhvcNAQEFBQADggEBALgmcJFj2EBIZXExJRL+VwHy9r5T84EOVlIjGc0KF1X4eHLVQ9fT2Ha0ROyt7JMFEnv3+wISPfRL7GleDnqSIbpQM8jrsS4HLqntxberzACGPJTiK+6jSgTDwPb5ZavAJZ8p/PunWhmfzFC95Mw4dW2raVoAL59BN4D94U+7nNL17zfvfds5ZKvstG6Jy+YEyILE1JRm2WGpzjKldouCJw88jw2SyUuOgZGF7NaAhKr3z9EJI8PLowpz4mACIyJCGFKvccThxyzibY3TsTJqb4UBydPl8MZBA8Y3tD0oh26j146FIHMzUEM50i8kgvwqkTWvCR4c0FFBap0uGx1lkFoxggHlMIIB4QIBATBPMEIxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ8wDQYDVQQLDAZQRVJTT04xEzARBgNVBAMMCk1TU1BDQSBSU0ECCSAQNQAAAECVCTANBglghkgBZQMEAgEFAKBpMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTE5MDkwOTE1MzE0MFowLwYJKoZIhvcNAQkEMSIEIF93ydQocScYyQzmg3SW5auU4OpHQfvE97D5tGUzouZ0MA0GCSqGSIb3DQEBAQUABIIBAGytTptICSfiRmM3RjOUrKjNhRcylRPKsXpQ9u4migAWO6E5XV3pYbE1crJcoSl+IhudYSZ+G/J703TjuoMMA9f/IiPANRirD2riw36f21BBVMQVCK8UUk59VSVBA8FgpwIqL1T1Vi5bRU6zLOjCZQ338ybJQ/zngV3EoUmeLGXQfn4RADFI7kVBlDV8Sl1BMcFbJn41q3QuTn0WXZeXp0QUFwu4U1h732YlSVMf20dwwNJKcOnz4XItykYaOivEgisPMNeiYGY6hBMBYAdzC9rjMCzbqVmBCAPnl0JEhYUQycP8YhDpTklP/UEhT7u3kAbAA9Ro576xkTJT4OPlKHQ=";
        p7bStr = "MIIFhAYJKoZIhvcNAQcCoIIFdTCCBXECAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA7swggO3MIIDIKADAgECAhBz40YRauXVBMjieT9ILRXkMA0GCSqGSIb3DQEBBQUAMIGUMQswCQYDVQQGEwJDTjERMA8GA1UECBMITGlhb25pbmcxETAPBgNVBAcTCFNoZW55YW5nMUEwPwYDVQQKEzhMaWFvbmluZyBEaWdpdGFsIENlcnRpZmljYXRlIEF1dGhvcml0eSBNYW5hZ2VtZW50IENvLkx0ZDENMAsGA1UECxMEbG5jYTENMAsGA1UEAxMETE5DQTAeFw0xNzEyMjAwNjUxNDhaFw0xODEyMjAwNjUxNDhaMGIxCzAJBgNVBAYTAkNOMQ0wCwYDVQQIHgSPvVuBMSEwHwYDVQQKHhiPvVuBdwGY31TBg29UwXbRd2N7oXQGXEAxITAfBgNVBAMeGI+9W4F3AZjfVMGDb1TBdtF3Y3uhdAZcQDCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAs+FUisdbTOiW0cuAutvvpsJnkfbSruyVZIqiNu7FsRbWrsB6K4alUNMScKhYbE6Q23vxVahYx6ncrSkmoc7eqzJehXGWCUnjpQOTFomQRQ7SavA6eYhBnAtVpTKTWicylEewESkHAh1WwuFtZn/ONGB15STLbt2bD41s7Y+8vUsCAwEAAaOCATkwggE1MB8GA1UdIwQYMBaAFKMQpgJGx9iTOCrArrgFfq7pHcXzMB0GA1UdDgQWBBSBU782qSBfdfHvAqnT1o5VOla1AzAMBgNVHRMEBTADAQEAMIHkBgNVHR8EgdwwgdkwgbOggbCgga2kgaowgacxCzAJBgNVBAYTAkNOMREwDwYDVQQIEwhMaWFvbmluZzERMA8GA1UEBxMIU2hlbnlhbmcxQTA/BgNVBAoTOExpYW9uaW5nIERpZ2l0YWwgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IE1hbmFnZW1lbnQgQ28uTHRkMQ0wCwYDVQQLEwRsbmNhMQ0wCwYDVQQDEwRMTkNBMREwDwYDVQQDEwhjcmwxMDhfMDAhoB+gHYYbaHR0cC8vd3d3LmxuLWNhLmNvbS9jcmwuY3JsMA0GCSqGSIb3DQEBBQUAA4GBANIXM08BvCmY5NS2AfGpuPAZ7oGgu8OWxZxhNIHUPOxKkgX9CYkmfR+6i+eq1ICqvbKZPhIzH9+OSp3rUOxUxQKgp7YTLjyVZ56VzbKv9BXqoiLwfM3G3y6yvtiFYyqhRMdJGaCGY0/SrUsu4e6Fd2TRCBhnNVgnEM1A+eII6Gx+MYIBkTCCAY0CAQEwgakwgZQxCzAJBgNVBAYTAkNOMREwDwYDVQQIEwhMaWFvbmluZzERMA8GA1UEBxMIU2hlbnlhbmcxQTA/BgNVBAoTOExpYW9uaW5nIERpZ2l0YWwgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IE1hbmFnZW1lbnQgQ28uTHRkMQ0wCwYDVQQLEwRsbmNhMQ0wCwYDVQQDEwRMTkNBAhBz40YRauXVBMjieT9ILRXkMAkGBSsOAwIaBQCgPzAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMCMGCSqGSIb3DQEJBDEWBBTHnZQAw2OVUl3Qvd3gLApUCivnGDANBgkqhkiG9w0BAQEFAASBgHIwbqy+qzgkx9mHKLw/EIb6GkGTcqwhO/0wIeSr7ZlAvHMxO0I+BfbcYfutFq2PWMQfwDbVPfffBwwGAYCNdUHoE0vlLPBsoY5EX1nNGWefKGQA4JnIGlG0r9vFh87Ly/DSFqJIoISHDUcx4wO+7AFs0ahCZbKSkiFzwhVfTzTW";

        byte[] src = Base64.getDecoder().decode(p7bStr);
        src = FileUtil.fromDATfile(p+n);
        System.out.println("p7b-base64:");
        System.out.println(Base64.getEncoder().encodeToString(src));

        ASN1Sequence root = (ASN1Sequence)ASN1Sequence.fromByteArray(src); // 1级
        ASN1ObjectIdentifier ctnType = (ASN1ObjectIdentifier)root.getObjectAt(0);
        System.out.println(ctnType);

        ASN1TaggedObject contentTag = (ASN1TaggedObject)root.getObjectAt(1);
//        System.out.println(contentTag);

        System.out.println("二级：");
        ASN1Sequence content = (ASN1Sequence)contentTag.getObjectParser(0, true);// 2级
        System.out.println(content);

        // 原文
//        ASN1Sequence signedDataSeq = (ASN1Sequence)content.getObjectAt(2);
//        ASN1TaggedObject ctnTag1 = (ASN1TaggedObject)signedDataSeq.getObjectAt(1);
//        ASN1OctetString ctnOct = (ASN1OctetString)ctnTag1.getObjectParser(0, true);
//        System.out.println(ctnOct);
//        byte[] data = ctnOct.getOctets();// 原文的byte[]
////        System.out.println(new String(data));


        byte[] data = null;

        // 读取证书和公钥
        ASN1TaggedObject cerTag0 = (ASN1TaggedObject)content.getObjectAt(3);
        ASN1Sequence cerSeq = (ASN1Sequence)cerTag0.getObjectParser(0, true);
        int certSize = cerSeq.size();
        ASN1Sequence certNeed = cerSeq;
        if(cerSeq.getObjectAt(certSize-1) instanceof ASN1Sequence){
            certNeed = (ASN1Sequence)cerSeq.getObjectAt(certSize - 1);
        }

        byte[] pk = getRsaPublicKey(certNeed.getEncoded());
        PublicKey publicKey =
                KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pk));
        System.out.println(pk.length);
        System.out.println(Util.byteToHex(pk));


        // 解析签名者信息
        int lastIndex = content.size()-1;
        ASN1Set signerInfos =(ASN1Set)content.getObjectAt(lastIndex);
        ASN1Sequence signerInfo0 = (ASN1Sequence)signerInfos.getObjectAt(0);

        // 按pkcs#7对签名者要认证属性进行摘要和组包后作为签名入参
        // 获取签名待摘要数据  authenticatedAttributes
        ASN1Sequence digAlgOid = (ASN1Sequence)signerInfo0.getObjectAt(2);
        ASN1TaggedObject authAttrsTag = (ASN1TaggedObject)signerInfo0.getObjectAt(3);
        ASN1Sequence digAlgOid2 = (ASN1Sequence)signerInfo0.getObjectAt(4); // 签名算法

        // 获取attribute集合-->获得消息摘要过程的最终摘要,同时也是签名的原文
        ASN1Sequence attrs = (ASN1Sequence)authAttrsTag.getObjectParser(0, true);
        ASN1EncodableVector attVect = new ASN1EncodableVector();
        for (int j= 0; j<attrs.size(); j++){
            attVect.add((ASN1Sequence)attrs.getObjectAt(j));
        }
        DERSet attSets = new DERSet(attVect);
        byte[] originData = attSets.getEncoded(ASN1Encoding.DER);


        String rsaAlgSign = "SHA256withRSA";
        if("1.3.14.3.2.26".equalsIgnoreCase(digAlgOid.getObjectAt(0).toString())){
            rsaAlgSign = "SHA1withRSA";
        }

        // 验签过程
        data = originData;
            // 获得p7中的签名值
        byte[] signed = null;
        for(int i = 0; i< signerInfo0.size(); i++){
            ASN1Encodable objAti = signerInfo0.getObjectAt(i);
            if(objAti instanceof ASN1OctetString){
                ASN1OctetString encryptedDigest = (ASN1OctetString)objAti;
                signed = encryptedDigest.getOctets();
                System.out.println(Base64.getEncoder().encodeToString(signed));
            }
        }
        System.out.println("验签>>>");
        String vsInfoTotal = String.format("解析得到的验签的各入参数据 >>>\n签名摘要算法：\t%s\n" +
                        "消息摘要结果（HEX）：\t%s\n公钥（编码结构的HEX）：\t%s\n签名值（HEX）：\t%s\n",
                rsaAlgSign, Util.byteToHex(data), Util.byteToHex(publicKey.getEncoded()), Util.byteToHex(signed));
        System.out.println(vsInfoTotal);
        String logPath = "C:\\Users\\49762\\Desktop\\vsLog.txt";
        FileUtil.appendCtx2File1(logPath, n + "\n");
        FileUtil.appendCtx2File1(logPath, vsInfoTotal);



            // 验签
        try {
            Signature verifySign = Signature.getInstance(rsaAlgSign);  // SHA256withRSA
            verifySign.initVerify(publicKey);
            verifySign.update(data);
            boolean isSuc = verifySign.verify(signed);
            String logRtn = ">>> 验证结果:\t" + isSuc;
            System.out.println(logRtn);
            FileUtil.appendCtx2File1(logPath, logRtn + "\n");
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("验签异常", e);
        }


    }


    public static byte[] getRsaPublicKey(byte[] src) throws IOException {
        try {
            ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
            ASN1Sequence at06 = (ASN1Sequence)ASN1Sequence.getInstance(at0).getObjectAt(6);
            return at06.getEncoded();
        } catch (IOException e) {
            throw new IOException("不符合rsa证书的格式，获取主体公钥信息失败", e);
        }
    }


    @Test // 验证rsa带明文无认证属性的p7
    public void test3()throws  Exception{

        String n = "含原文不含认证属性";
        String p7Base64 = "MIID6AYJKoZIhvcNAQcCoIID2TCCA9UCAQExDzANBglghkgBZQMEAgEFADA7BgkqhkiG9w0BBwGgLgQs6L+Z5bCx5piv5LiA5q615piO5paH5pWw5o2uOmFiY2RlZjEyMzQ1Njc4OTCgggJbMIICVzCCAcCgAwIBAgIEV6LRLjANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdkb25nMRIwEAYDVQQHEwlHdWFuZ3pob3UxDjAMBgNVBAoTBU5FVENBMQ4wDAYDVQQLEwVORVRDQTEYMBYGA1UEAwwP55yB6LSo55uR5rWL6K+VMCAXDTE2MDgwNDA1MjI1NFoYDzIxMTYwNzExMDUyMjU0WjBvMQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdkb25nMRIwEAYDVQQHEwlHdWFuZ3pob3UxDjAMBgNVBAoTBU5FVENBMQ4wDAYDVQQLEwVORVRDQTEYMBYGA1UEAwwP55yB6LSo55uR5rWL6K+VMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMx4UDmOgeV5ZUcSNf6qHiIdaqGqFMMpZ2vk36RM/KIJXUSZWDwu52iPf0ETTu7BQjVFUrGHQOofnJQx5nWDh76gMrPFbPfxemTW2qElxgNes3QQxYluBF/n0tatz8WhvCrQApbDOtAAtJUqKG2Ay9hWM/4E7I+M7jHoagCuhoIwIDAQABMA0GCSqGSIb3DQEBBQUAA4GBAJcWmiSLr/jLLyuKSW9eyjpHeAvo/8+haKDaGr09ebb1jUE5sJOwuKRpbV0boEnwltaPBAEFcPeKytMhtPj1Aq2gjqiersX4HATJRdQ2rmVvDqPXKZIZFyfT7zmaVEFIgmyoVRxuHWSvu2v3f1fGi1f2zwrTMfwOBQQe3GgECZIaMYIBITCCAR0CAQEwdzBvMQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdkb25nMRIwEAYDVQQHEwlHdWFuZ3pob3UxDjAMBgNVBAoTBU5FVENBMQ4wDAYDVQQLEwVORVRDQTEYMBYGA1UEAwwP55yB6LSo55uR5rWL6K+VAgRXotEuMA0GCWCGSAFlAwQCAQUAMA0GCSqGSIb3DQEBAQUABIGAFzLugGehkj8AoBXVhOrQo+SuE2DLhCMdN0riUKwio+9vohCd0T2ZjXHNiYM0G28OPM+mvaNVYcqpmxS3RcVRBB+aEs1Rtb9qaL074LimKqsgRCbjScESGtl/BspzLd73EBz1R4+iEBzFRZc1nh/WpTRUTrDwpH+wQIQJCLfP1w8=";
        byte[] src = Base64.getDecoder().decode(p7Base64);

        ASN1Sequence root = (ASN1Sequence)ASN1Sequence.fromByteArray(src); // 1级
        ASN1ObjectIdentifier ctnType = (ASN1ObjectIdentifier)root.getObjectAt(0);
        System.out.println(ctnType);

        ASN1TaggedObject contentTag = (ASN1TaggedObject)root.getObjectAt(1);
//        System.out.println(contentTag);

        System.out.println("二级：");
        ASN1Sequence content = (ASN1Sequence)contentTag.getObjectParser(0, true);// 2级
        System.out.println(content);

        // 原文
        ASN1Sequence signedDataSeq = (ASN1Sequence)content.getObjectAt(2);
        ASN1TaggedObject ctnTag1 = (ASN1TaggedObject)signedDataSeq.getObjectAt(1);
        ASN1OctetString ctnOct = (ASN1OctetString)ctnTag1.getObjectParser(0, true);
        System.out.println(ctnOct);
        byte[] data = ctnOct.getOctets();// 原文的byte[]
//        System.out.println(new String(data));


        // 读取证书和公钥
        ASN1TaggedObject cerTag0 = (ASN1TaggedObject)content.getObjectAt(3);
        ASN1Sequence cerSeq = (ASN1Sequence)cerTag0.getObjectParser(0, true);
        int certSize = cerSeq.size();
        ASN1Sequence certNeed = cerSeq;
        if(cerSeq.getObjectAt(certSize-1) instanceof ASN1Sequence){
            certNeed = (ASN1Sequence)cerSeq.getObjectAt(certSize - 1);
        }

        byte[] pk = getRsaPublicKey(certNeed.getEncoded());
        PublicKey publicKey =
                KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pk));
        System.out.println(pk.length);
        System.out.println(Util.byteToHex(pk));


        // 解析签名者信息
        int lastIndex = content.size()-1;
        ASN1Set signerInfos =(ASN1Set)content.getObjectAt(lastIndex);
        ASN1Sequence signerInfo0 = (ASN1Sequence)signerInfos.getObjectAt(0);

        // 按pkcs#7对签名者要认证属性进行摘要和组包后作为签名入参
        // 获取签名待摘要数据  authenticatedAttributes
        ASN1Sequence digAlgOid = (ASN1Sequence)signerInfo0.getObjectAt(2);
       /*
        ASN1TaggedObject authAttrsTag = (ASN1TaggedObject)signerInfo0.getObjectAt(3);
        ASN1Sequence digAlgOid2 = (ASN1Sequence)signerInfo0.getObjectAt(4); // 签名算法

        // 获取attribute集合-->获得消息摘要过程的最终摘要,同时也是签名的原文
        ASN1Sequence attrs = (ASN1Sequence)authAttrsTag.getObjectParser(0, true);
        ASN1EncodableVector attVect = new ASN1EncodableVector();
        for (int j= 0; j<attrs.size(); j++){
            attVect.add((ASN1Sequence)attrs.getObjectAt(j));
        }
        DERSet attSets = new DERSet(attVect);
        byte[] originData = attSets.getEncoded(ASN1Encoding.DER);
        // 验签过程
        data = originData;

        */

        String rsaAlgSign = "SHA256withRSA";
        if("1.3.14.3.2.26".equalsIgnoreCase(digAlgOid.getObjectAt(0).toString())){
            rsaAlgSign = "SHA1withRSA";
        }





        // 获得p7中的签名值
        byte[] signed = null;
        for(int i = 0; i< signerInfo0.size(); i++){
            ASN1Encodable objAti = signerInfo0.getObjectAt(i);
            if(objAti instanceof ASN1OctetString){
                ASN1OctetString encryptedDigest = (ASN1OctetString)objAti;
                signed = encryptedDigest.getOctets();
                System.out.println(Base64.getEncoder().encodeToString(signed));
            }
        }
        System.out.println("验签>>>");
        String vsInfoTotal = String.format("解析得到的验签的各入参数据 >>>\n签名摘要算法：\t%s\n" +
                        "消息摘要结果（HEX）：\t%s\n公钥（编码结构的HEX）：\t%s\n签名值（HEX）：\t%s\n",
                rsaAlgSign, Util.byteToHex(data), Util.byteToHex(publicKey.getEncoded()), Util.byteToHex(signed));
        System.out.println(vsInfoTotal);
        String logPath = "C:\\Users\\49762\\Desktop\\vsLog.txt";
        FileUtil.appendCtx2File1(logPath, n + "\n");
        FileUtil.appendCtx2File1(logPath, vsInfoTotal);



        // 验签
        try {
            Signature verifySign = Signature.getInstance(rsaAlgSign);  // SHA256withRSA
            verifySign.initVerify(publicKey);
            verifySign.update(data);
            boolean isSuc = verifySign.verify(signed);
            String logRtn = ">>> 验证结果:\t" + isSuc;
            System.out.println(logRtn);
            FileUtil.appendCtx2File1(logPath, logRtn + "\n");
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("验签异常", e);
        }







    }


}
