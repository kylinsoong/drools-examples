package com.sample.models;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = -8373800527913207816L;

    private String level;

    private Integer fee;
    
    private Boolean isSpecial;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    @Override
    public String toString() {
        return "Order [level=" + level + ", fee=" + fee + "]";
    }

}
