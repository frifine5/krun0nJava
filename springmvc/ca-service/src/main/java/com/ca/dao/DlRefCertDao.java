package com.ca.dao;

import com.ca.entity.CertsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 双证书关系数据表dao
 * @author WangChengyu
 * 2019/4/9 11:38
 */
@Repository
@Mapper
public interface DlRefCertDao {


    @Select("SELECT ID, SIGN_SN, ENC_SN, PRT_ENC_KEY, RDTIME FROM DLCERT WHERE ID = #{id}")
    @Results(id = "dlRefCert", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "signSn", column = "SIGN_SN"),
            @Result(property = "encSn", column = "ENC_SN"),
            @Result(property = "prtEncKey", column = "PRT_ENC_KEY"),
            @Result(property = "rdTime", column = "RDTIME"),
    })
    CertsEntity getRndByCode(String id);

    @Insert("INSERT INTO CERTS(ID, SIGN_SN, ENC_SN, PRT_ENC_KEY, RDTIME) " +
            " VALUES( #{id}, #{signSn}, #{encSn}, #{prtEncKey}, #{rdTime} )")
    int addRnd(CertsEntity nrd);

    @Update("UPDATE CERTS SET SIGN_SN=#{signSn}, ENC_SN=#{encSn}, PRT_ENC_KEY=#{prtEncKey}, RDTIME=#{rdTime} " +
            " WHERE ID = #{id}")
    int uptRnd(CertsEntity nrd);


}
