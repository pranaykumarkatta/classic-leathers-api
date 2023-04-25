package com.classicLeathers.classicLeathersTool.production.model;

import java.util.Objects;

public class PackingListEntry {
    private String boxNumber;
    private String batchNumber;
    private String brand;
    private String poNumber;
    private String sku;
    private String leather;
    private String size_40_quantity;
    private String size_41_quantity;
    private String size_42_quantity;
    private String size_43_quantity;
    private String size_44_quantity;
    private String size_45_quantity;
    private String size_46_quantity;
    private String size_47_quantity;
    private String total;
    private String courierName;
    private String trackingNumber;
    private String shippedDate;

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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

    public String getSize_40_quantity() {
        return size_40_quantity;
    }

    public void setSize_40_quantity(String size_40_quantity) {
        this.size_40_quantity = size_40_quantity;
    }

    public String getSize_41_quantity() {
        return size_41_quantity;
    }

    public void setSize_41_quantity(String size_41_quantity) {
        this.size_41_quantity = size_41_quantity;
    }

    public String getSize_42_quantity() {
        return size_42_quantity;
    }

    public void setSize_42_quantity(String size_42_quantity) {
        this.size_42_quantity = size_42_quantity;
    }

    public String getSize_43_quantity() {
        return size_43_quantity;
    }

    public void setSize_43_quantity(String size_43_quantity) {
        this.size_43_quantity = size_43_quantity;
    }

    public String getSize_44_quantity() {
        return size_44_quantity;
    }

    public void setSize_44_quantity(String size_44_quantity) {
        this.size_44_quantity = size_44_quantity;
    }

    public String getSize_45_quantity() {
        return size_45_quantity;
    }

    public void setSize_45_quantity(String size_45_quantity) {
        this.size_45_quantity = size_45_quantity;
    }

    public String getSize_46_quantity() {
        return size_46_quantity;
    }

    public void setSize_46_quantity(String size_46_quantity) {
        this.size_46_quantity = size_46_quantity;
    }

    public String getSize_47_quantity() {
        return size_47_quantity;
    }

    public void setSize_47_quantity(String size_47_quantity) {
        this.size_47_quantity = size_47_quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    @Override
    public String toString() {
        return "PackingListEntry{" +
                "boxNumber='" + boxNumber + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", poNumber='" + poNumber + '\'' +
                ", sku='" + sku + '\'' +
                ", leather='" + leather + '\'' +
                ", courierName='" + courierName + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", shippedDate='" + shippedDate + '\'' +
                '}';
    }
    public String getDispatchString() {
        return "PackingListEntry{" +
                ", batchNumber='" + batchNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", poNumber='" + poNumber + '\'' +
                ", sku='" + sku + '\'' +
                ", leather='" + leather + '\'' +
                ", shippedDate='" + shippedDate + '\'' +
                '}';
    }
}
