package com.user.dao;


import com.user.entity.AUser;
import com.user.entity.PUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {


    /**
     * 获取个人用户列表(全部)
     */
    @Select("SELECT * FROM PS_USER ORDER BY ID DESC")
    @Results(id = "psUser", value =  {
            @Result(column = "ID", property = "id"),
            @Result(column = "ACCOUNT", property = "account"),
            @Result(column = "PWD", property = "pwd"),
            @Result(column = "NAME", property = "name"),
            @Result(column = "SEX", property = "sexCode"),
            @Result(column = "CODE", property = "code"),
            @Result(column = "CODE_TYPE", property = "codeType"),
            @Result(column = "CRT_TIME", property = "crtTime"),

    } )
    List<PUser> listAllPersonUsers();

    /**
     * 指定范围的
     */
    @Select("SELECT * FROM PS_USER ORDER BY ID DESC limit #{st}, #{pageSize}")
    @ResultMap("psUser")
    List<PUser> listPagePersonUsers(@Param("st")int start, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(ID) FROM PS_USER")
    int listPsTotalMount();
    /**
     * 姓名+证件号查重
     */
    @Select("SELECT * FROM PS_USER WHERE NAME=#{name} AND CODE=#{code} LIMIT 1")
    @ResultMap("psUser")
    PUser findPUserExist(@Param("name") String name, @Param("code") String code);

    /** 账号查重 */
    @Select("SELECT * FROM PS_USER WHERE ACCOUNT=#{account}")
    @ResultMap("psUser")
    PUser findPAccExist(String account);

    @Insert("INSERT INTO PS_USER(ACCOUNT, PWD, NAME, SEX, CODE, CODE_TYPE, CRT_TIME) " +
            " VALUES(#{account}, #{pwd}, #{name}, #{sexCode}, #{code}, #{codeType}, #{crtTime} )")
    int addOnePsUser(PUser nUser);

    @Update("UPDATE PS_USER SET ACCOUNT=#{account}, PWD=#{pwd}, NAME=#{name}, " +
            " SEX=#{sexCode}, CODE=#{code}, CODE_TYPE=#{codeType}, CRT_TIME=#{crtTime}" +
            " WHERE ACCOUNT=#{account} ")
    int modOnePsUser(PUser nUser);


    @Delete("DELETE FROM PS_USER WHERE ACCOUNT=#{account}")
    int delOnePsUser(String account);

    /*****************************************************************************************************/

    /**
     *
     */
    @Select("SELECT * FROM AD_USER ORDER BY ID DESC")
    @Results(id = "adUser", value =  {
            @Result(column = "ID", property = "id"),
            @Result(column = "ACCOUNT", property = "account"),
            @Result(column = "PWD", property = "pwd"),
            @Result(column = "NAME", property = "name"),
            @Result(column = "ROLE_ID", property = "roleId"),
            @Result(column = "STATUS", property = "statusCode"),
            @Result(column = "CRT_TIME", property = "crtTime"),

    } )
    List<AUser> listAllAdmUsers();

    /**
     * 指定范围的
     */
    @Select("SELECT * FROM AD_USER ORDER BY ID DESC limit #{st}, #{pageSize}")
    @ResultMap("adUser")
    List<AUser> listPageAdmUsers(@Param("st")int start, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(ID) FROM AD_USER")
    int listAdmTotalMount();

    /** adm 显示名查重 */
    @Select("SELECT * FROM AD_USER WHERE NAME=#{name} LIMIT 1")
    @ResultMap("adUser")
    AUser findAdmUserExist(String name);

    /** adm 账号查重 */
    @Select("SELECT * FROM AD_USER WHERE ACCOUNT=#{account}")
    @ResultMap("adUser")
    AUser findAdmAccExist(String account);

    @Insert("INSERT INTO AD_USER(ACCOUNT, PWD, NAME, ROLE_ID, STATUS, CRT_TIME) " +
            " VALUES(#{account}, #{pwd}, #{name}, #{roleId}, #{statusCode}, #{crtTime} )")
    int addOneAdmUser(AUser nUser);

    @Update("UPDATE AD_USER SET ACCOUNT=#{account}, PWD=#{pwd}, NAME=#{name}, " +
            " ROLE_ID=#{roleId}, STATUS=#{statusCode}, CRT_TIME=#{crtTime}" +
            " WHERE ACCOUNT=#{account} ")
    int modOneAdmUser(AUser nUser);


    @Delete("DELETE FROM AD_USER WHERE ACCOUNT=#{account}")
    int delOneAdmUser(String account);



}
