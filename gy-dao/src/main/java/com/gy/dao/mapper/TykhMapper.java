package com.gy.dao.mapper;

import com.gy.dao.model.Tykh;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface TykhMapper {
    @Select({
        "select",
        "a.jg2, a.spdm, a.xgrq",
        "from ${tableName} a inner join dm_spxx b on a.spdm = b.tid",
        "where a.yxbz = 'Y' and a.jg2>0 and a.fsrq = #{fsrq,jdbcType=VARCHAR}",
        "and a.gsdm like #{gsdm,jdbcType=VARCHAR}",
        "and (#{brand,jdbcType=VARCHAR} is null or b.pp like #{brand,jdbcType=VARCHAR})",
        "and (#{anyField,jdbcType=VARCHAR} is null or b.fullname like #{anyField,jdbcType=VARCHAR})",
        "and (#{priceStart,jdbcType=VARCHAR} is null or a.jg2 >= #{priceStart,jdbcType=DOUBLE})",
        "and (#{priceEnd,jdbcType=VARCHAR} is null or a.jg2 <= #{priceEnd,jdbcType=DOUBLE})",
        "and (#{timeStart,jdbcType=VARCHAR} is null or a.xgrq >= #{timeStart,jdbcType=VARCHAR})",
        "and (#{timeEnd,jdbcType=VARCHAR} is null or a.xgrq<=  #{timeEnd,jdbcType=VARCHAR})",
    })
    @Results({
        @Result(column="jg2", property="jg2", jdbcType=JdbcType.DOUBLE),
        @Result(column="spdm", property="goodsMapping",one=@One(select="com.gy.dao.mapper.GoodsMappingMapper.selectBySpdm",fetchType= FetchType.EAGER)),
        @Result(column="spdm", property="spxx", one=@One(select="com.gy.dao.mapper.SpxxMapper.selectByPrimaryKey",fetchType= FetchType.EAGER)),
        @Result(column="xgrq", property="xgrq",  jdbcType=JdbcType.VARCHAR),
    })
    List<Tykh> selectBjd(String fsrq, String gsdm,String brand,String anyField,Double priceStart,Double priceEnd,String timeStart,String timeEnd,String tableName);

    @Select({
            "select",
            "max(fsrq) fsrq",
            "from ${tableName}",
            "where yxbz = 'Y' and gsdm like #{gsdm,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="fsrq", property="fsrq"),
           })
    String selectMaxFsrqByGsdm(String gsdm,String tableName);
}