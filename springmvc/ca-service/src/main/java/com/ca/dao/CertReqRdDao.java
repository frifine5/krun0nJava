package com.ca.dao;

import com.ca.entity.CertReqRdEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 证书申请表dao
 * @author WangChengyu
 * 2019/4/9 11:39
 */
@Repository
@Mapper
public interface CertReqRdDao {




    @Select("SELECT ID, REQTIME, STATUS, CERT_NAME, UNIT_NAME, UNIT_UCODE, UNIT_DISCODE, UNIT_ADDRESS, UNIT_TELEPHONE, " +
            " VALIDSTART, VALIDEND, AGE, PK, P10  FROM CERT_APPLY WHERE ID = #{id}")
    @Results(id = "certReq", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "reqTime", column = "REQTIME"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "certName", column = "CERT_NAME"),
            @Result(property = "unitName", column = "UNIT_NAME"),
            @Result(property = "unitUCode", column = "UNIT_UCODE"),
            @Result(property = "unitDisCode", column = "UNIT_DISCODE"),
            @Result(property = "unitAddr", column = "UNIT_ADDRESS"),
            @Result(property = "unitTelephone", column = "UNIT_TELEPHONE"),
            @Result(property = "validStart", column = "VALIDSTART"),
            @Result(property = "validEnd", column = "VALIDEND"),
            @Result(property = "age", column = "AGE"),
            @Result(property = "pk", column = "PK"),
            @Result(property = "p10", column = "P10"),
    })
    CertReqRdEntity getRndByCode(String id);

    @Select("SELECT ID, REQTIME, STATUS, CERT_NAME, UNIT_NAME, UNIT_UCODE, UNIT_DISCODE, UNIT_ADDRESS, UNIT_TELEPHONE, " +
            " VALIDSTART, VALIDEND, AGE, PK, P10  FROM CERT_APPLY WHERE CERT_NAME = #{certName}")
    @ResultMap("certReq")
    CertReqRdEntity getRndByName(String certName);

    @Select("SELECT ID, REQTIME, STATUS, CERT_NAME, UNIT_NAME, UNIT_UCODE, UNIT_DISCODE, UNIT_ADDRESS, UNIT_TELEPHONE, VALIDSTART, VALIDEND, " +
            " AGE, PK, P10  FROM CERT_APPLY ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("certReq")
    List<CertReqRdEntity> queryRndListRecentSeveral(int several);

    @Insert("INSERT INTO CERT_APPLY(ID, REQTIME, STATUS, CERT_NAME, UNIT_NAME, UNIT_UCODE, UNIT_DISCODE, UNIT_ADDRESS, " +
            " UNIT_TELEPHONE, VALIDSTART, VALIDEND, AGE, PK, P10) " +
            " VALUES( #{id}, #{reqTime}, #{status}, #{certName}, #{unitName}, #{unitUCode}, #{unitDisCode},  #{unitAddr}, " +
            " #{unitTelephone}, #{validStart}, #{validEnd}, #{age}, #{pk}, #{p10} )")
    int addRnd(CertReqRdEntity nrd);

    @Update("UPDATE CERT_APPLY SET STATUS = #{status} WHERE ID = #{id}")
    int uptRndSts(@Param("id") long id, @Param("status")int status);

    @Update("UPDATE CERT_APPLY SET STATUS=#{status}, REQTIME=#{reqTime}, UNIT_NAME=#{unitName}, " +
            " UNIT_UCODE=#{unitUCode}, UNIT_DISCODE=#{unitDisCode}, UNIT_ADDRESS=#{unitAddr}, " +
            " UNIT_TELEPHONE=#{unitTelephone}, CERT_NAME=#{certName} " +
            " VALIDSTART=#{validStart}, VALIDEND=#{validEnd}, AGE=#{age}, PK=#{pk}, P10=#{p10} " +
            " WHERE ID = #{id}")
    int uptRnd(CertReqRdEntity nrd);



}
