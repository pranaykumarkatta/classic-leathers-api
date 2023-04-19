package com.classicLeathers.classicLeathersTool.production.model;

import java.util.List;
import java.util.Map;

public class ProductionProgressDto {
    private String sku;
    private String leather;
    private String isProductionProgressEntry;
    private Map<String, JobCardSizes>  productionStageSizeList;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getLeather() {
        return leather;
    }

    public void setLeather(String leather) {
        this.leather = leather;
    }

    public String getIsProductionProgressEntry() {
        return isProductionProgressEntry;
    }

    public void setIsProductionProgressEntry(String isProductionProgressEntry) {
        this.isProductionProgressEntry = isProductionProgressEntry;
    }

    public Map<String, JobCardSizes> getProductionStageSizeList() {
        return productionStageSizeList;
    }

    public void setProductionStageSizeList(Map<String, JobCardSizes> productionStageSizeList) {
        this.productionStageSizeList = productionStageSizeList;
    }
}
