package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Route implements Serializable {

    private static final long serialVersionUID = 8149164569350480101L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeid;
    
    private Integer distance;
    
    @OneToMany(mappedBy = "route")
    private Set<Flight> flights;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "originairportid")
    private Airport airport1;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinationairportid")
    private Airport airport2;
    
    public Route() {
        
    }

    public Integer getRouteid() {
        return routeid;
    }

    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    public Airport getAirport1() {
        return airport1;
    }

    public void setAirport1(Airport airport1) {
        this.airport1 = airport1;
    }

    public Airport getAirport2() {
        return airport2;
    }

    public void setAirport2(Airport airport2) {
        this.airport2 = airport2;
    }
    
}
