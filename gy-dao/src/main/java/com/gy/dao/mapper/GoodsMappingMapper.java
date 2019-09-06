package com.gy.dao.mapper;

import com.gy.dao.model.GoodsMapping;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

public interface GoodsMappingMapper {
    @Insert({
        "insert into goodsMapping (spdm, goodsid)",
        "values (#{spdm,jdbcType=NUMERIC}, #{goodsid,jdbcType=VARCHAR})"
    })
    int insert(GoodsMapping record);

    @InsertProvider(type=GoodsMappingSqlProvider.class, method="insertSelective")
    int insertSelective(GoodsMapping record);

    @Select({
            "select",
            "spdm,goodsid",
            "from goodsMapping",
            "where spdm = #{spdm,jdbcType=NUMERIC}"
    })
    @Results({
            @Result(column="spdm", property="spdm"),
            @Result(column="goodsid", property="goodsid"),
    })
    GoodsMapping selectBySpdm(BigDecimal spdm);
}