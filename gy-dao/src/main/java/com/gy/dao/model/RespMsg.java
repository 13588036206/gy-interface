package com.gy.dao.model;

import net.sf.json.JSONObject;

import java.util.List;

public class RespMsg {
    private String code;
    private String msg;
    private List<GoodsToJson> list;
    public static String OVERMAXCODE = "-2";
    public static String OVERMAXMSG = "超过最大访问次数";

    public static String ERRORCODE = "-1";
    public static String ERRORMSG = "系统错误，稍候再试";

    public static String SUCCESSCODE = "0";
    public static String SUCCESSMSG = "成功";

    public static String EXCODE = "1";
    public static String EXMSG = "报价单列表为空";


    public RespMsg() {
        this.code = SUCCESSCODE;
        this.msg = SUCCESSMSG;
    }

    public RespMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GoodsToJson> getList() {
        return list;
    }

    public void setList(List<GoodsToJson> list) {
        this.list = list;
    }
}
