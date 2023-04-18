package com.classicLeathers.classicLeathersTool.production.model;

import java.util.List;
import java.util.Map;

public class ProductionProgressDto {
    private String sku;
    private String leather;
    private Map<String, List<JobCardSizes>>  productionStageSizeList;

}
