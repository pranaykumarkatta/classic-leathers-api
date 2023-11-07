package com.classicLeathers.classicLeathersTool.retail.model;
public class ProductSalesHistoryDto {
    private String brand;
    private String Category;
    private String leather;
    private String product;
    private Integer size_40_quantity;
    private Integer size_41_quantity;
    private Integer size_42_quantity;
    private Integer size_43_quantity;
    private Integer size_44_quantity;
    private Integer size_45_quantity;
    private Integer size_46_quantity;
    private Integer size_47_quantity;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLeather() {
        return leather;
    }

    public void setLeather(String leather) {
        this.leather = leather;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getSize_40_quantity() {
        return size_40_quantity == null ? 0 : size_40_quantity;
    }

    public void setSize_40_quantity(Integer size_40_quantity) {
        this.size_40_quantity = size_40_quantity;
    }

    public Integer getSize_41_quantity() {
        return size_41_quantity == null ? 0 : size_41_quantity;
    }

    public void setSize_41_quantity(Integer size_41_quantity) {
        this.size_41_quantity = size_41_quantity;
    }

    public Integer getSize_42_quantity() {
        return size_42_quantity == null ? 0 : size_42_quantity;
    }

    public void setSize_42_quantity(Integer size_42_quantity) {
        this.size_42_quantity = size_42_quantity;
    }

    public Integer getSize_43_quantity() {
        return size_43_quantity == null ? 0 : size_43_quantity;
    }

    public void setSize_43_quantity(Integer size_43_quantity) {
        this.size_43_quantity = size_43_quantity;
    }

    public Integer getSize_44_quantity() {
        return size_44_quantity == null ? 0 : size_44_quantity;
    }

    public void setSize_44_quantity(Integer size_44_quantity) {
        this.size_44_quantity = size_44_quantity;
    }

    public Integer getSize_45_quantity() {
        return size_45_quantity == null ? 0 : size_45_quantity;
    }

    public void setSize_45_quantity(Integer size_45_quantity) {
        this.size_45_quantity = size_45_quantity;
    }

    public Integer getSize_46_quantity() {
        return size_46_quantity == null ? 0 : size_46_quantity;
    }

    public void setSize_46_quantity(Integer size_46_quantity) {
        this.size_46_quantity = size_46_quantity;
    }

    public Integer getSize_47_quantity() {
        return size_47_quantity == null ? 0 : size_47_quantity;
    }

    public void setSize_47_quantity(Integer size_47_quantity) {
        this.size_47_quantity = size_47_quantity;
    }

    @Override
    public String toString() {
        return "ProductSalesHistoryDto{" +
                "brand='" + brand + '\'' +
                ", Category='" + Category + '\'' +
                ", leather='" + leather + '\'' +
                ", size_40_quantity=" + size_40_quantity +
                ", size_41_quantity=" + size_41_quantity +
                ", size_42_quantity=" + size_42_quantity +
                ", size_43_quantity=" + size_43_quantity +
                ", size_44_quantity=" + size_44_quantity +
                ", size_45_quantity=" + size_45_quantity +
                ", size_46_quantity=" + size_46_quantity +
                ", size_47_quantity=" + size_47_quantity +
                '}';
    }
}
