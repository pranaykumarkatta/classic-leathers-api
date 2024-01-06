package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Map;
import java.util.Objects;

public class StockReportDto {
    private String brand;
    private String article;
    private String size;
    private String from;
    private String to;
    private Integer stockInQuantity=0;
    private Integer retailSalesQuantity=0;
    private Integer OnlineSalesQuantity=0;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getStockInQuantity() {
        return stockInQuantity;
    }

    public void setStockInQuantity(Integer stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    public Integer getRetailSalesQuantity() {
        return retailSalesQuantity;
    }

    public void setRetailSalesQuantity(Integer retailSalesQuantity) {
        this.retailSalesQuantity = retailSalesQuantity;
    }

    public Integer getOnlineSalesQuantity() {
        return OnlineSalesQuantity;
    }

    public void setOnlineSalesQuantity(Integer onlineSalesQuantity) {
        OnlineSalesQuantity = onlineSalesQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockReportDto)) return false;
        StockReportDto that = (StockReportDto) o;
        return Objects.equals(getBrand(), that.getBrand()) && Objects.equals(getArticle(), that.getArticle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrand(), getArticle());
    }
}
