package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Date;
import java.util.Locale;

public class RetailSalesEntryDto implements Comparable {

    private String saleDate;
    private String customerName;
    private String gender;
    private String mobileNumber;
    private String Category;
    private String productDetails;
    private String quantity;
    private String size;
    private String mrp;
    private String discount;
    private String salePrice;
    private String modeOfPayment;
    private String cashPayment;
    private String gPayPayment;
    private String swipePayment;
    private String updatedBy;
    private String brand;

    private String searchString;

    private String isTodaySale;
    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String getgPayPayment() {
        return gPayPayment;
    }

    public void setgPayPayment(String gPayPayment) {
        this.gPayPayment = gPayPayment;
    }

    public String getSwipePayment() {
        return swipePayment;
    }

    public void setSwipePayment(String swipePayment) {
        this.swipePayment = swipePayment;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getIsTodaySale() {
        return isTodaySale;
    }

    public void setIsTodaySale(String isTodaySale) {
        this.isTodaySale = isTodaySale;
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
        this.searchString = brand.toUpperCase() + " " + Category.toUpperCase() + " " + productDetails.toUpperCase();
    }
    @Override
    public int compareTo(Object o) {
        Date thisDate = new Date(this.saleDate);
        Date oDate = new Date(((RetailSalesEntryDto)o).getSaleDate());
        if (thisDate.after(oDate)) {
            return 1;
        }
        if (thisDate.before(oDate)) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
