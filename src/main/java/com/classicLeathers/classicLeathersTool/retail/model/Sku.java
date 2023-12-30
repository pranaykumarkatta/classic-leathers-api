package com.classicLeathers.classicLeathersTool.retail.model;

public class Sku {
    private String id;
    private String sku;
    private String brand;
    private String category;
    private String description;
    private String purchaseCost;
    private String retailMrp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(String purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public String getRetailMrp() {
        return retailMrp;
    }

    public void setRetailMrp(String retailMrp) {
        this.retailMrp = retailMrp;
    }
}
