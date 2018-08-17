package com.t.dao;


import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IOrderDao {

    @Select("select name from ordertbl where date = #{date}")
    List<String> findName(String date);


}
