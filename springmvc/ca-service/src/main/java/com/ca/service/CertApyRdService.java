package com.ca.service;

import com.ca.dao.CertReqRdDao;
import com.ca.dao.CertsDao;
import com.ca.entity.CertReqRdEntity;
import com.cer.SM2CaCert;
import com.cer.SM2CertGenUtil;
import com.cer.SM2CsrUtil;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class CertApyRdService {

    @Autowired
    CertReqRdDao reqRdDao;

    @Autowired
    CertsDao certsDao;

    @Autowired
    GenNumberService genNumberService;

    @Autowired
    SysService sysService;


    /**
     *
     * @param apyNrd
     * @return
     */
    public int rdApplyRnd(CertReqRdEntity apyNrd){
        CertReqRdEntity oldRnd = reqRdDao.getRndByName(apyNrd.getCertName());
        if(null != oldRnd){
            throw new RuntimeException("申请的证书名称["+apyNrd.getCertName()+"]记录已经存在");
        }
        reqRdDao.addRnd(apyNrd);
        return 0;
    }


    public String parsePk(String ft, String txt){
        byte[] bytPk = null;
        switch (ft){
            case "0":// 公钥
                bytPk = Base64.getDecoder().decode(txt);
                break;
            case "1":// p10
                byte[] bytCsr = Base64.getDecoder().decode(txt);
                try {
                    bytPk = SM2CaCert.getSM2PublicKeyFromCSR(bytCsr);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
                break;
            default:{
                throw new RuntimeException("文件类型不支持");
            }
        }
        if(bytPk.length !=65 && bytPk.length !=132){
            throw new RuntimeException("公钥数据长度错误:expect 65 或 132, actual "+bytPk.length);
        }else{
            return txt;
        }
    }








}
