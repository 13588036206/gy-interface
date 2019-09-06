package com.gy.dao.mapper;

import com.gy.dao.model.GoodsMapping;
import org.apache.ibatis.jdbc.SQL;

public class GoodsMappingSqlProvider {

    public String insertSelective(GoodsMapping record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("goodsMapping");
        
        if (record.getSpdm() != null) {
            sql.VALUES("spdm", "#{spdm,jdbcType=NUMERIC}");
        }
        
        if (record.getGoodsid() != null) {
            sql.VALUES("goodsid", "#{goodsid,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}