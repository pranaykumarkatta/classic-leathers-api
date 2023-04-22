package com.classicLeathers.classicLeathersTool.production.model;

import java.util.Date;

public class JobCardProgress implements Comparable{
    private String Date;
    private String sku;
    private String leather;
    private String size;
    private String productionStage;
    private String count;
    private String batchNumber;
    private String packingBoxNumber;
    private String courierName;
    private String trackingNumber;

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

    public String getLeather() {
        return leather;
    }

    public void setLeather(String leather) {
        this.leather = leather;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPackingBoxNumber() {
        return packingBoxNumber;
    }

    public void setPackingBoxNumber(String packingBoxNumber) {
        this.packingBoxNumber = packingBoxNumber;
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

    @Override
    public int compareTo(Object o) {
        java.util.Date thisDate = new Date(this.getDate());
        Date oDate = new Date(((JobCardProgress) o).getDate());
        if (thisDate.after(oDate) || ((thisDate.equals(oDate)))) {
            return 1;
        }
        if (thisDate.before(oDate) || ((thisDate.equals(oDate)))) {
            return -1;
        } else {
            return 0;
        }
    }
}
