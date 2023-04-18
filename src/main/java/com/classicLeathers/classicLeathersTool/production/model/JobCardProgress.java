package com.classicLeathers.classicLeathersTool.production.model;

public class JobCardProgress {
    private String Date;
    private String sku;
    private String productionStage;
    private String count;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductionStage() {
        return productionStage;
    }

    public void setProductionStage(String productionStage) {
        this.productionStage = productionStage;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
