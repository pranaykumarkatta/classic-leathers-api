package com.classicLeathers.classicLeathersTool.retail.model;

public class Presentation {
    private String month;
    private String category;
    private Integer totalSales;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalSales() {
        return totalSales == null ? 0 : totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }
}
