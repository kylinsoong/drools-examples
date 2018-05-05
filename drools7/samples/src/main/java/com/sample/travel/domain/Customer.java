package com.sample.travel.domain;

public class Customer {

    private Integer age;
    
    private String loyaltyLevel;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public void setLoyaltyLevel(String loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
    }

    @Override
    public String toString() {
        return "Age: " + age + ", loyaltyLevel: " + loyaltyLevel;
    }
}
