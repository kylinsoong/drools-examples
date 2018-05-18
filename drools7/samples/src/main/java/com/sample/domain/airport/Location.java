package com.sample.domain.airport;

public enum Location {
    
    CHECK_IN("check-in"), SORTING("sorting"), STAGING("staging"), LOADING("loading");
    
    private String location;
    
    private Location(String location) {
        this.setLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
}

}
