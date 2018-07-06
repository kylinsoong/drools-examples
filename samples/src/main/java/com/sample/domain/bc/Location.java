package com.sample.domain.bc;

import org.kie.api.definition.type.Position;

public class Location {
    
    @Position(0)
    private String thing;
    
    @Position(1)
    private String place;

    public Location(String thing, String place) {
        super();
        this.thing = thing;
        this.place = place;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
