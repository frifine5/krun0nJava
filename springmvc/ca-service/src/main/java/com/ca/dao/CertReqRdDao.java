package com.ca.dao;

import com.ca.entity.CertReqRdEntity;
import com.ca.entity.CertsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CertReqRdDao {



    @Select("SELECT ID, REQTIME, STATUS, UNIT_NAME, UNIT_UCODE, UNIT_ADDRESS, VALIDSTART, VALIDEND, " +
            " AGE, PK, P10  FROM DLCERT_REQUEST ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("certReq")
    List<CertReqRdEntity> queryRndListRecentSeveral(int several);

    @Select("SELECT ID, REQTIME, STATUS, UNIT_NAME, UNIT_UCODE, UNIT_ADDRESS, VALIDSTART, VALIDEND," +
            " AGE, PK, P10  FROM DLCERT_REQUEST WHERE ID = #{id}")
    @Results(id = "certReq", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "reqTime", column = "REQTIME"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "unitName", column = "UNIT_NAME"),
            @Result(property = "unitUCode", column = "UNIT_UCODE"),
            @Result(property = "unitAddr", column = "UNIT_ADDRESS"),
            @Result(property = "validStart", column = "VALIDSTART"),
            @Result(property = "validEnd", column = "VALIDEND"),
            @Result(property = "age", column = "AGE"),
            @Result(property = "pk", column = "PK"),
            @Result(property = "p10", column = "P10"),
    })
    CertReqRdEntity getRndByCode(String id);

    @Insert("INSERT INTO DLCERT_REQUEST(ID, REQTIME, STATUS, UNIT_NAME, UNIT_UCODE, UNIT_ADDRESS, " +
            " VALIDSTART, VALIDEND, AGE, PK, P10) " +
            " VALUES( #{id}, #{reqTime}, #{status}, #{unitName}, #{unitUCode}, #{unitAddr}, " +
            " #{validStart}, #{validEnd}, #{age}, #{pk}, #{p10} )")
    int addRnd(CertsEntity nrd);

    @Update("UPDATE DLCERT_REQUEST SET STATUS = #{status} WHERE ID = #{id}")
    int uptRndSts(@Param("id") long id, @Param("status")int status);

    @Update("UPDATE DLCERT_REQUEST SET STATUS=#{status}, REQTIME=#{reqTime}, UNIT_NAME=#{unitName}, UNIT_UCODE=#{unitUCode}, " +
            " UNIT_ADDRESS=#{unitAddr}, VALIDSTART=#{validStart}, VALIDEND=#{validEnd}, AGE=#{age}, PK=#{pk}, P10=#{p10} " +
            " WHERE ID = #{id}")
    int uptRnd(CertsEntity nrd);



}
