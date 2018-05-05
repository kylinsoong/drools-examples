package com.sample.travel.domain;

import java.math.BigDecimal;

public class Reservation {

    private Boolean mealUpgrade;
    
    private Boolean classUpgrade;
    
    private Boolean drinkUpgrade;
    
    private Boolean comfortUpgrade;
    
    private Boolean entertainmentUpgrade;
    
    private Boolean flightInsurance;
    
    private Integer bagsChecked;
    
    private BigDecimal mealFees = new BigDecimal(0.00);

    public Boolean getMealUpgrade() {
        return mealUpgrade;
    }

    public void setMealUpgrade(Boolean mealUpgrade) {
        this.mealUpgrade = mealUpgrade;
    }

    public Boolean getClassUpgrade() {
        return classUpgrade;
    }

    public void setClassUpgrade(Boolean classUpgrade) {
        this.classUpgrade = classUpgrade;
    }

    public Boolean getDrinkUpgrade() {
        return drinkUpgrade;
    }

    public void setDrinkUpgrade(Boolean drinkUpgrade) {
        this.drinkUpgrade = drinkUpgrade;
    }

    public Boolean getComfortUpgrade() {
        return comfortUpgrade;
    }

    public void setComfortUpgrade(Boolean comfortUpgrade) {
        this.comfortUpgrade = comfortUpgrade;
    }

    public Boolean getEntertainmentUpgrade() {
        return entertainmentUpgrade;
    }

    public void setEntertainmentUpgrade(Boolean entertainmentUpgrade) {
        this.entertainmentUpgrade = entertainmentUpgrade;
    }

    public Boolean getFlightInsurance() {
        return flightInsurance;
    }

    public void setFlightInsurance(Boolean flightInsurance) {
        this.flightInsurance = flightInsurance;
    }

    public Integer getBagsChecked() {
        return bagsChecked;
    }

    public void setBagsChecked(Integer bagsChecked) {
        this.bagsChecked = bagsChecked;
    }

    public BigDecimal getMealFees() {
        return mealFees;
    }

    public void setMealFees(BigDecimal mealFees) {
        this.mealFees = mealFees;
    }

    @Override
    public String toString() {
        return "Reservation [mealUpgrade=" + mealUpgrade + ", classUpgrade=" + classUpgrade + ", drinkUpgrade="
                + drinkUpgrade + ", comfortUpgrade=" + comfortUpgrade + ", entertainmentUpgrade=" + entertainmentUpgrade
                + ", flightInsurance=" + flightInsurance + ", bagsChecked=" + bagsChecked + "]";
    }
}
