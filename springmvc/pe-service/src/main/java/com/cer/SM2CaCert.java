package com.cer;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;


public class SM2CaCert {

	/**
	 * 输入证书字节，返回主体公钥中的公钥
	 * 
	 * @param src
	 *            证书数据
	 * @return byte[] SM2公钥
	 */
	public static byte[] getSM2PublicKey(byte[] src) throws IOException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at06 = ASN1Sequence.getInstance(at0).getObjectAt(6);
			ASN1Encodable at061 = ASN1Sequence.getInstance(at06).getObjectAt(1);
			byte[] bitString = DERBitString.getInstance(at061).getBytes();
			byte[] pk = new byte[65];
			pk[0] = 0x04;
			int len = bitString.length;
			System.arraycopy(bitString, len-64, pk, 1, 64);
			return pk;
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取主体公钥信息失败", e);
		}
	}
	
	
	/**
	 * 输入证书字节，返回主体的数据
	 * 
	 * @param src
	 *            证书数据
	 * @return byte[] 证书主体信息
	 */
	public static byte[] getSM2TBSCertificateDate(byte[] src) throws IOException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			return ASN1Sequence.getInstance(at0).getEncoded();
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书主体信息失败", e);
		}
	}
	
	/**
	 * 输入证书字节，返回签名值
	 * 
	 * @param src
	 *            证书数据
	 * @return byte[] 返回SM2证书签名值（64位: x(32) | y(32)）
	 */
	public static byte[] getSM2signatureValue(byte[] src) throws IOException {
		try {
			DERBitString bsv = DERBitString.getInstance(ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(2));
			byte[] ssv = bsv.getBytes();
			int len = ssv.length;
			byte[] sv = new byte[64];
			byte[] flag = new byte[1];
			flag[0] = ssv[len-33];
			if(0 == flag[0]){
				System.arraycopy(ssv, len-67, sv, 0, 32);
			}else{
				System.arraycopy(ssv, len-66, sv, 0, 32);
			}
			System.arraycopy(ssv, len-32, sv, 32, 32);
			return  sv;
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书主体信息失败", e);
		} 
	}
	
	
	
	
	

}
