package com.ca.dao;

import com.ca.entity.CrlRecordEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CrlsDao {


    @Select("SELECT ID, DATA, RDTIME FROM AGCRLS ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("crlInfo")
    List<CrlRecordEntity> queryRndListRecentSeveral(int several);


    @Select("SELECT ID, DATA, RDTIME FROM AGCRLS WHERE ID = #{id}")
    @Results(id = "crlInfo", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "data", column = "DATA"),
            @Result(property = "rdTime", column = "RDTIME"),
    })
    CrlRecordEntity getRndByCode(String id);

    @Insert("INSERT INTO AGCRLS(ID, DATA, RDTIME) " +
            " VALUES(#{id}, #{data}, #{rdTime})")
    int addRnd(CrlRecordEntity nrd);



}
