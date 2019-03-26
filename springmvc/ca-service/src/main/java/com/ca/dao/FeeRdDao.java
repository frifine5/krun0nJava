package com.ca.dao;

import com.ca.entity.FeeRecordEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FeeRdDao {


    @Select("SELECT ID, SN, COE, RDTIME FROM FEERD ORDER BY RDTIME DESC LIMIT #{several}")
    @ResultMap("crlInfo")
    List<FeeRecordEntity> queryRndListRecentSeveral(int several);

    @Select("SELECT ID, SN, COE, RDTIME FROM FEERD ORDER BY RDTIME DESC LIMIT #{start}, #{mount}")
    @ResultMap("crlInfo")
    List<FeeRecordEntity> queryRndListLimit(@Param("start") int start, @Param("mount")int mount);



    @Select("SELECT ID, SN, COE, RDTIME FROM FEERD WHERE ID = #{id}")
    @Results(id = "crlInfo", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "sn", column = "SN"),
            @Result(property = "coefficient", column = "COE"),
            @Result(property = "rdTime", column = "RDTIME"),
    })
    FeeRecordEntity getRndByCode(String id);

    @Insert("INSERT INTO AGCRLS(ID, SN, COE, RDTIME) " +
            " VALUES(#{id}, #{sn}, #{coefficient}, #{rdTime})")
    int addRnd(FeeRecordEntity nrd);



}
