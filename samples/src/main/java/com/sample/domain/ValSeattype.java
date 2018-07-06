package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class ValSeattype implements Serializable {

    private static final long serialVersionUID = 1064361817898094693L;
    
    @Id
    private String seatposition;
    
    @OneToMany(mappedBy = "valSeattype")
    private Set<Seat> seats;
    
    public ValSeattype() {
        
    }

    public String getSeatposition() {
        return seatposition;
    }

    public void setSeatposition(String seatposition) {
        this.seatposition = seatposition;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

}
