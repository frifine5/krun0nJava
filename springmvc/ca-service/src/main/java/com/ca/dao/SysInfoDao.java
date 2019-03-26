package com.ca.dao;

import com.ca.entity.SysInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysInfoDao {

    @Select("SELECT VALUE FROM SYS_INFO WHERE CODE = #{code}")
    String getValueByCode(String code);

    @Select("SELECT CODE, VALUE, ENT, MD FROM SYS_INFO WHERE CODE = #{code}")
    @Results(id = "sysInfo", value = {
            @Result(property = "code", column = "CODE"),
            @Result(property = "value", column = "VALUE"),
            @Result(property = "extend", column = "ENT"),
            @Result(property = "mark", column = "MD"),
    })
    SysInfoEntity getRndByCode(String code);

    @Update("UPDATE SYS_INFO SET VALUE = #{value} WHERE CODE = #{code}")
    int uptValueByCode(@Param("code") String code, @Param("value") String value);

    @Update("UPDATE SYS_INFO SET VALUE = #{value}, ENT = #{extend}, MD = #{mark} WHERE CODE = #{code}")
    int uptEntityByCode(SysInfoEntity nrd);

}
