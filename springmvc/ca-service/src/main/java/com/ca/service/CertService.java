package com.ca.service;

import com.ca.dao.CertReqRdDao;
import com.ca.dao.CertsDao;
import com.cer.SM2CaCert;
import com.cer.SM2CertGenUtil;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CertService {

    @Autowired
    CertReqRdDao reqRdDao;

    @Autowired
    CertsDao certsDao;

    @Autowired
    GenNumberService genNumberService;

    @Autowired
    SysService sysService;






    public int addReqRecord(){



        return 0;
    }


    public byte[] generateCertByPk(byte[] pk, int age){

        long serial = genNumberService.getNumber();
        byte[] tbsc = SM2CertGenUtil.generateCertTBSCert(1, serial, "test", age, pk);
        GMTSM2 sm2 = GMTSM2.getInstance();
        String[] kp = sysService.getSysKPair();
        byte[] spk = Util.hexStringToBytes(kp[0]);
        byte[] ssk = Util.hexStringToBytes(kp[1]);


        byte[] md = SM3Util.sm3Digest(tbsc, spk);
        byte[] sv = sm2.sm2Sign(md, ssk);

        byte[] bytCert = SM2CertGenUtil.makeSM2Cert(tbsc, sv);

        return bytCert;
    }

    public byte[] generateCertByCsr(byte[] csr, int age){
        byte[] pk;
        try{
            pk = SM2CaCert.getSM2PublicKeyFromCSR(csr);
        }catch (Exception e){
            throw new RuntimeException("p10结构错误", e);
        }
        return generateCertByPk(pk, age);
    }







}
