package com.user.dao;

import com.user.entity.PsSigImg;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface PsSigDao {

    /**
     * 获取个人用户列表(全部)
     */
    @Select("SELECT * FROM PS_SIG_IMG WHERE ACCOUNT = #{account} ORDER BY ID DESC limit 1")
    @Results(id = "psSigImg", value =  {
            @Result(column = "ID", property = "id"),
            @Result(column = "ACCOUNT", property = "account"),
            @Result(column = "SIG1", property = "sig1"),
            @Result(column = "SIG2", property = "sig2"),
            @Result(column = "SIG3", property = "sig3"),
            @Result(column = "SEAL", property = "seal"),

    } )
    PsSigImg findPersonSigImg(String account);


    @Insert("INSERT INTO PS_SIG_IMG(ACCOUNT, SIG1, SIG2, SIG3, SEAL) " +
            " VALUES(#{account}, #{sig1}, #{sig2}, #{sig3}, #{seal})")
    int addPersonSigImg(PsSigImg record);


    @Update("UPDATE PS_SIG_IMG SET ACCOUNT = #{account}, SIG1 = #{sig1}, " +
            " SIG2 = #{sig2}, SIG3 = #{sig3}, SEAL = #{seal} " +
            " WHERE ACCOUNT=#{account}" )
    int uptPersonSigImg(PsSigImg record);

    @Delete("DELETE FROM PS_SIG_IMG WHERE ACCOUNT=#{account} ")
    int delPersonSigImgRecord(String account);

    /** 插入/有则更新*/
    @Insert("INSERT INTO PS_SIG_IMG(ACCOUNT, SEAL) VALUE(#{account}, #{seal})" +
            " ON DUPLICATE KEY UPDATE SEAL=#{seal}")
    int iouPersonSigImgByAccount(PsSigImg record);



}
