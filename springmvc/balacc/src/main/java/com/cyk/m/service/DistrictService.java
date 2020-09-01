package com.cyk.m.service;

import com.cyk.common.ParamsUtil;
import com.cyk.m.dao.DistrictDao;
import com.cyk.m.ev.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    DistrictDao districtDao;


    public List<District> getListByType(String code, String type){
        String dCode;
        List<District> list;
        if(ParamsUtil.checkNull(code)){
            list = districtDao.getDList("省", "1");
        }else if("3".equals(type)){
            dCode = code.substring(0, 4);
            list = districtDao.getDList(dCode, type);
        }else{
            dCode = code.substring(0, 2);
            list = districtDao.getDList(dCode, type);
        }
        return list;
    }

}
