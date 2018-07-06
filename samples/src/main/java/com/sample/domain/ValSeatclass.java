package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class ValSeatclass implements Serializable {

    private static final long serialVersionUID = -910213438121809486L;
    
    @Id
    private String seatcategory;
    
    @OneToMany(mappedBy = "valSeatclass")
    private Set<Seat> seats;
    
    public ValSeatclass() {
        
    }

    public String getSeatcategory() {
        return seatcategory;
    }

    public void setSeatcategory(String seatcategory) {
        this.seatcategory = seatcategory;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

}
