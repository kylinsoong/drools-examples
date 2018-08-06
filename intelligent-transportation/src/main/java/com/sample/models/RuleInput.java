package com.sample.models;

import java.io.Serializable;
import java.util.Date;

public class RuleInput implements Serializable {

    private static final long serialVersionUID = -8286627753131708167L;
    
    private String identifyMethod;
    
    private Date detectionTime;
    
    private String detectionPointCode;
    
    private String accountType;
    
    private String vehicleClass;
    
    private int dayOfWeek;
    
    private int timeOfDay;
    
    private String operator;
    
    private String purposeOfUse;
    
    private Integer TollFee;
    
    private Integer AdminFee;
    
    private Integer Discount;
    
    public RuleInput() {}

    public RuleInput(String identifyMethod, Date detectionTime, String detectionPointCode, String accountType,
            String vehicleClass, int dayOfWeek, int timeOfDay, String operator, String purposeOfUse) {
        this.identifyMethod = identifyMethod;
        this.detectionTime = detectionTime;
        this.detectionPointCode = detectionPointCode;
        this.accountType = accountType;
        this.vehicleClass = vehicleClass;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.operator = operator;
        this.purposeOfUse = purposeOfUse;
    }

    public String getIdentifyMethod() {
        return identifyMethod;
    }

    public void setIdentifyMethod(String identifyMethod) {
        this.identifyMethod = identifyMethod;
    }

    public Date getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(Date detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getDetectionPointCode() {
        return detectionPointCode;
    }

    public void setDetectionPointCode(String detectionPointCode) {
        this.detectionPointCode = detectionPointCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPurposeOfUse() {
        return purposeOfUse;
    }

    public void setPurposeOfUse(String purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }

    public Integer getTollFee() {
        return TollFee;
    }

    public void setTollFee(Integer tollFee) {
        TollFee = tollFee;
    }

    public Integer getAdminFee() {
        return AdminFee;
    }

    public void setAdminFee(Integer adminFee) {
        AdminFee = adminFee;
    }

    public Integer getDiscount() {
        return Discount;
    }

    public void setDiscount(Integer discount) {
        Discount = discount;
    }

    @Override
    public String toString() {
        return "RuleInput [identifyMethod=" + identifyMethod + ", detectionTime=" + detectionTime
                + ", detectionPointCode=" + detectionPointCode + ", accountType=" + accountType + ", vehicleClass="
                + vehicleClass + ", dayOfWeek=" + dayOfWeek + ", timeOfDay=" + timeOfDay + ", operator=" + operator
                + ", purposeOfUse=" + purposeOfUse + ", TollFee=" + TollFee + ", AdminFee=" + AdminFee + ", Discount="
                + Discount + "]";
    }
    

}
