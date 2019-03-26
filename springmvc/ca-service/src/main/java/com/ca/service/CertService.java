package com.ca.service;

import com.ca.dao.CertReqRdDao;
import com.ca.dao.CertsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertService {

    @Autowired
    CertReqRdDao reqRdDao;

    @Autowired
    CertsDao certsDao;


    public int addReqRecord(){



        return 0;
    }






}
