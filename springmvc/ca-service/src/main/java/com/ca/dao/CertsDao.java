package com.ca.dao;

import com.ca.entity.CertsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CertsDao {


    @Select("SELECT ID, TYPE, DN, PRTK, FT, CERT, RDTIME FROM CERTS ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("certInfo")
    List<CertsEntity> queryRndListRecentSeveral(int several);

    @Select("SELECT ID, TYPE, DN, PRTK, FT, CERT, RDTIME FROM CERTS WHERE ID = #{id}")
    @Results(id = "certInfo", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "type", column = "TYPE"),
            @Result(property = "dn", column = "DN"),
            @Result(property = "prtkey", column = "PRTK"),
            @Result(property = "fatherId", column = "FT"),
            @Result(property = "cert", column = "CERT"),
            @Result(property = "rdTime", column = "RDTIME"),
    })
    CertsEntity getRndByCode(String id);

    @Insert("INSERT INTO CERTS(ID, TYPE, DN, PRTK, FT, CERT, RDTIME) " +
            " VALUES( #{id}, #{type}, #{dn}, #{prtkey}, #{fatherId}, #{cert}, #{rdTime} )")
    int addRnd(CertsEntity nrd);



}
