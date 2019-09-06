package com.gy.dao.mapper;

import com.gy.dao.model.CommunicationLog;
import org.apache.ibatis.annotations.*;

public interface CommunicationLogMapper {
    @Insert({
        "insert into communicationLog (company, ",
        "requestTime, state, ",
        "inputTime)",
        "values (#{company,jdbcType=VARCHAR}, ",
        "#{requesttime,jdbcType=VARCHAR}, #{state,jdbcType=INTEGER}, ",
        "#{inputtime,jdbcType=TIMESTAMP})"
    })
    int insert(CommunicationLog record);

    @Select({
            "select",
            "count(*) count",
            "from communicationLog",
            "where company = #{company,jdbcType=VARCHAR} and inputtime like #{inputtime,jdbcType=VARCHAR} and state = 1"
    })
    @Results({
            @Result(column="count"),
    })
    int countLogs(String company,String inputtime);
}