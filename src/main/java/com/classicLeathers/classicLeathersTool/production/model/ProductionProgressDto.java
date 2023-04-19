package com.classicLeathers.classicLeathersTool.production.model;

import java.util.Map;

public class ProductionProgressDto {
    private String sku;
    private String isProductionProgressEntry;
    private Map<String, JobCardSizes> productionStageSizeMap;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getIsProductionProgressEntry() {
        return isProductionProgressEntry;
    }

    public void setIsProductionProgressEntry(String isProductionProgressEntry) {
        this.isProductionProgressEntry = isProductionProgressEntry;
    }

    public Map<String, JobCardSizes> getProductionStageSizeMap() {
        return productionStageSizeMap;
    }

    public void setProductionStageSizeMap(Map<String, JobCardSizes> productionStageSizeMap) {
        this.productionStageSizeMap = productionStageSizeMap;
    }
}
