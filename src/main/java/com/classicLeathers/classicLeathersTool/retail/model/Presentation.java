package com.classicLeathers.classicLeathersTool.retail.model;

public class Presentation {
    private String month;
    private String totalSalesCategory;
    private Integer totalSales;
    private Integer totalCostPrice;
    private Integer hour;
    private Integer totalHourlySales;
    private Integer hourlyStepInCount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalSalesCategory() {
        return totalSalesCategory;
    }

    public void setTotalSalesCategory(String totalSalesCategory) {
        this.totalSalesCategory = totalSalesCategory;
    }

    public Integer getTotalSales() {
        return totalSales == null ? 0 : totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getTotalHourlySales() {
        return totalHourlySales == null ? 0 : totalHourlySales;
    }

    public Integer getTotalCostPrice() {
        return totalCostPrice == null ? 0 : totalCostPrice;
    }

    public void setTotalCostPrice(Integer totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    public void setTotalHourlySales(Integer totalHourlySales) {
        this.totalHourlySales = totalHourlySales;
    }

    public Integer getHourlyStepInCount() {
        return hourlyStepInCount == null ? 0 : hourlyStepInCount;
    }

    public void setHourlyStepInCount(Integer hourlyStepInCount) {
        this.hourlyStepInCount = hourlyStepInCount;
    }
}
