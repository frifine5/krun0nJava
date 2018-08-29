package com.cyk.m.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class DistrictDaoProvider {

    public DistrictDaoProvider() {
    }

    /**
     *
     * @return
     */

    public String getDList(@Param("dCode")String dCode, @Param("type")String type){
        SQL sql = new SQL().SELECT("DCODE, DNAME").FROM("DISTRICT");
        if(!StringUtils.hasText(dCode)){
            sql.WHERE("DCODE is null"); // 返回不存在的数据条件
        }else if(!StringUtils.hasText(type) || "1".equals(type)){// 返回省列表
            sql.WHERE("DCODE LIKE '__0000'");
        }else if("2".equals(type)){// 返回市列表
            sql.WHERE("DCODE LIKE concat('', #{dCode}, '__00') and DCODE <> concat('', #{dCode}, '0000') ");
        }else if("3".equals(type)){// 返回县列表
            sql.WHERE("DCODE LIKE concat('', #{dCode}, '__') and DCODE <> concat('', #{dCode}, '00') ");
        }else{
            sql.WHERE("DCODE is null");
        }

        return sql.toString();
    }
}
