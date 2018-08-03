package org.kie.examples.phreak.nodes;

public class Provider {

    private int rating;
    
    public Provider() {
        
    }

    public Provider(int rating) {
        super();
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
