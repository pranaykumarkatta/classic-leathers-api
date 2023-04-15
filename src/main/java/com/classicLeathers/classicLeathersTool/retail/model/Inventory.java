package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Map;

public class Inventory {
    private String article;
    private String product;
    private String brand;

    private Map<String,Integer> quantity;

    private Integer totalQuantity;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Map<String, Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(Map<String, Integer> quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "\nInventory{" +
                "article='" + article + '\'' +
                ", product='" + product + '\'' +
                ", brand='" + brand + '\'' +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
