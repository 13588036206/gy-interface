package com.gy.dao.model;

import java.math.BigDecimal;

public class GoodsMapping {
    private BigDecimal spdm;

    private String goodsid;

    public BigDecimal getSpdm() {
        return spdm;
    }

    public void setSpdm(BigDecimal spdm) {
        this.spdm = spdm;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }
}