package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Plane implements Serializable {

    private static final long serialVersionUID = 1847404802403766074L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planeid;
    
    private String planemodel;
    
    private String tailnumber;
    
    private Integer totalnumberofseats;
    
    @OneToMany(mappedBy = "plane")
    private Set<Flight> fligts;
    
    @OneToMany(mappedBy = "plane")
    private Set<Seat> seats;
    
    public Plane() {
        
    }

    public Integer getPlaneid() {
        return planeid;
    }

    public void setPlaneid(Integer planeid) {
        this.planeid = planeid;
    }

    public String getPlanemodel() {
        return planemodel;
    }

    public void setPlanemodel(String planemodel) {
        this.planemodel = planemodel;
    }

    public String getTailnumber() {
        return tailnumber;
    }

    public void setTailnumber(String tailnumber) {
        this.tailnumber = tailnumber;
    }

    public Integer getTotalnumberofseats() {
        return totalnumberofseats;
    }

    public void setTotalnumberofseats(Integer totalnumberofseats) {
        this.totalnumberofseats = totalnumberofseats;
    }

    public Set<Flight> getFligts() {
        return fligts;
    }

    public void setFligts(Set<Flight> fligts) {
        this.fligts = fligts;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

}
