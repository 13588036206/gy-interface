package com.gy.dao.model;

public class GoodsToJson {
    private String goodsId;
    private String goodsFullName;
    private String brand;
    private Double price;
    private String goodsName;
    private String color;
    private String time;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsFullName() {
        return goodsFullName;
    }

    public void setGoodsFullName(String goodsFullName) {
        this.goodsFullName = goodsFullName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
