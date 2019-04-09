package com.ca.dao;

import com.ca.entity.CertsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 证书表dao
 * @author WangChengyu
 * 2019/4/9 11:38
 */
@Repository
@Mapper
public interface CertsDao {


    @Select("SELECT ID, TYPE, FTID, STATUS, CERT, RDTIME FROM CERTS ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("certInfo")
    List<CertsEntity> queryRndListRecentSeveral(int several);

    @Select("SELECT ID, TYPE, FTID, STATUS, CERT, RDTIME FROM CERTS WHERE ID = #{id}")
    @Results(id = "certInfo", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "type", column = "TYPE"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "fatherId", column = "FTID"),
            @Result(property = "cert", column = "CERT"),
            @Result(property = "rdTime", column = "RDTIME"),
    })
    CertsEntity getRndByCode(String id);

    @Insert("INSERT INTO CERTS(ID, TYPE, FTID, STATUS, CERT, RDTIME) " +
            " VALUES( #{id}, #{type}, #{status}, #{fatherId}, #{cert}, #{rdTime} )")
    int addRnd(CertsEntity nrd);

    @Update("UPDATE CERTS SET TYPE=#{type}, FTID=#{fatherId}, STATUS=#{status}, CERT=#{cert}, RDTIME=#{rdTime} " +
            " WHERE ID = #{id}")
    int uptRnd(CertsEntity nrd);


}
