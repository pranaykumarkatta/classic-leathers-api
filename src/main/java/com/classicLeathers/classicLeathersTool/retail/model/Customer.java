package com.classicLeathers.classicLeathersTool.retail.model;

public class Customer {
    private String name;
    private String gender;
    private Long mobileNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Customer(String name, String gender, Long mobileNumber) {
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
    }
}
