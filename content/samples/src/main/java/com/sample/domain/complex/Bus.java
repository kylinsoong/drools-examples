package com.sample.domain.complex;

public class Bus {
    
    private String type;
    
    private String color;

    public Bus() {
        super();
    }

    public Bus(String type, String color) {
        super();
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
