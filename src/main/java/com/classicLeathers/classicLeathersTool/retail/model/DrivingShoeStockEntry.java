package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Date;

public class DrivingShoeStockEntry {
    private String rowNumber;
    private String brand;
    private String sku;
    private String leather;
    private String searchString;
    private Integer size_40_quantity = 0;
    private Integer size_41_quantity = 0;
    private Integer size_42_quantity = 0;
    private Integer size_43_quantity = 0;
    private Integer size_44_quantity = 0;
    private Integer size_45_quantity = 0;
    private Integer size_46_quantity = 0;
    private Integer size_47_quantity = 0;
    private Integer totalQuantity;
    private Integer actualOrderedQuantity;
    private String size_40_SalesStockInfo;
    private String size_41_SalesStockInfo;
    private String size_42_SalesStockInfo;
    private String size_43_SalesStockInfo;
    private String size_44_SalesStockInfo;
    private String size_45_SalesStockInfo;
    private String size_46_SalesStockInfo;
    private String size_47_SalesStockInfo;
    private String total_Quantity_SalesStockInfo;
    private boolean validEntry = true;
    private String stockInDate;
    private Integer costPrice;
    private String level_0_Category;
    private String level_1_Category;

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = this.brand.toUpperCase() + " " + this.sku.toUpperCase() + " " + this.leather.toUpperCase();
    }

    public Integer getSize_40_quantity() {
        return size_40_quantity;
    }

    public void setSize_40_quantity(Integer size_40_quantity) {
        this.size_40_quantity = size_40_quantity;
    }

    public Integer getSize_41_quantity() {
        return size_41_quantity;
    }

    public void setSize_41_quantity(Integer size_41_quantity) {
        this.size_41_quantity = size_41_quantity;
    }

    public Integer getSize_42_quantity() {
        return size_42_quantity;
    }

    public void setSize_42_quantity(Integer size_42_quantity) {
        this.size_42_quantity = size_42_quantity;
    }

    public Integer getSize_43_quantity() {
        return size_43_quantity;
    }

    public void setSize_43_quantity(Integer size_43_quantity) {
        this.size_43_quantity = size_43_quantity;
    }

    public Integer getSize_44_quantity() {
        return size_44_quantity;
    }

    public void setSize_44_quantity(Integer size_44_quantity) {
        this.size_44_quantity = size_44_quantity;
    }

    public Integer getSize_45_quantity() {
        return size_45_quantity;
    }

    public void setSize_45_quantity(Integer size_45_quantity) {
        this.size_45_quantity = size_45_quantity;
    }

    public Integer getSize_46_quantity() {
        return size_46_quantity;
    }

    public void setSize_46_quantity(Integer size_46_quantity) {
        this.size_46_quantity = size_46_quantity;
    }

    public Integer getSize_47_quantity() {
        return size_47_quantity;
    }

    public void setSize_47_quantity(Integer size_47_quantity) {
        this.size_47_quantity = size_47_quantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getActualOrderedQuantity() {
        return actualOrderedQuantity;
    }

    public void setActualOrderedQuantity(Integer actualOrderedQuantity) {
        this.actualOrderedQuantity = actualOrderedQuantity;
    }

    public String getSize_40_SalesStockInfo() {
        return size_40_SalesStockInfo;
    }

    public void setSize_40_SalesStockInfo(String size_40_SalesStockInfo) {
        this.size_40_SalesStockInfo = size_40_SalesStockInfo;
    }

    public String getSize_41_SalesStockInfo() {
        return size_41_SalesStockInfo;
    }

    public void setSize_41_SalesStockInfo(String size_41_SalesStockInfo) {
        this.size_41_SalesStockInfo = size_41_SalesStockInfo;
    }

    public String getSize_42_SalesStockInfo() {
        return size_42_SalesStockInfo;
    }

    public void setSize_42_SalesStockInfo(String size_42_SalesStockInfo) {
        this.size_42_SalesStockInfo = size_42_SalesStockInfo;
    }

    public String getSize_43_SalesStockInfo() {
        return size_43_SalesStockInfo;
    }

    public void setSize_43_SalesStockInfo(String size_43_SalesStockInfo) {
        this.size_43_SalesStockInfo = size_43_SalesStockInfo;
    }

    public String getSize_44_SalesStockInfo() {
        return size_44_SalesStockInfo;
    }

    public void setSize_44_SalesStockInfo(String size_44_SalesStockInfo) {
        this.size_44_SalesStockInfo = size_44_SalesStockInfo;
    }

    public String getSize_45_SalesStockInfo() {
        return size_45_SalesStockInfo;
    }

    public void setSize_45_SalesStockInfo(String size_45_SalesStockInfo) {
        this.size_45_SalesStockInfo = size_45_SalesStockInfo;
    }

    public String getSize_46_SalesStockInfo() {
        return size_46_SalesStockInfo;
    }

    public void setSize_46_SalesStockInfo(String size_46_SalesStockInfo) {
        this.size_46_SalesStockInfo = size_46_SalesStockInfo;
    }

    public String getSize_47_SalesStockInfo() {
        return size_47_SalesStockInfo;
    }

    public void setSize_47_SalesStockInfo(String size_47_SalesStockInfo) {
        this.size_47_SalesStockInfo = size_47_SalesStockInfo;
    }

    public String getTotal_Quantity_SalesStockInfo() {
        return total_Quantity_SalesStockInfo;
    }

    public void setTotal_Quantity_SalesStockInfo(String total_Quantity_SalesStockInfo) {
        this.total_Quantity_SalesStockInfo = total_Quantity_SalesStockInfo;
    }

    public boolean isValidEntry() {
        return validEntry;
    }

    public void setValidEntry(boolean validEntry) {
        this.validEntry = validEntry;
    }

    public String getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(String stockInDate) {
        this.stockInDate = stockInDate;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public String getLevel_0_Category() {
        return level_0_Category;
    }

    public void setLevel_0_Category(String level_0_Category) {
        this.level_0_Category = level_0_Category;
    }

    public String getLevel_1_Category() {
        return level_1_Category;
    }

    public void setLevel_1_Category(String level_1_Category) {
        this.level_1_Category = level_1_Category;
    }
}
