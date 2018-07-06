package com.sample.domain.conflict;

public class Room {

    private int number;
    
    private boolean accessible;
    
    private boolean reserved;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Room [number=" + number + ", accessible=" + accessible + ", reserved=" + reserved + "]";
    }
}
