package com.gy.dao.mapper;

import com.gy.dao.model.Spxx;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;

public interface SpxxMapper {

    @Select({
        "select",
        "tid, fullname, name, pp, color",
        "from dm_spxx",
        "where tid = #{tid,jdbcType=NUMERIC}"
    })
    @Results({
        @Result(column="tid", property="tid", jdbcType=JdbcType.NUMERIC),
        @Result(column="fullname", property="fullname", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.NUMERIC),
        @Result(column="pp", property="pp", jdbcType=JdbcType.VARCHAR),
        @Result(column="color", property="color", jdbcType=JdbcType.NUMERIC),

    })
    Spxx selectByPrimaryKey(BigDecimal tid);
}