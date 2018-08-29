package com.cyk.m.dao;

import com.cyk.m.ev.District;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author WangChengyu
 * 2018/8/29 10:59
 */

public interface DistrictDao {

    @SelectProvider(type = DistrictDaoProvider.class, method = "getDList")
    @Results(id = "district", value = {
            @Result(property = "dCode", column = "dcode"),
            @Result(property = "dName", column = "dname"),
    })
    List<District> getDList(@Param("dCode")String dCode, @Param("type")String type);

}
