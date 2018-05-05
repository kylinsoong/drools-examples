package com.sample.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class Flight implements Serializable {

    private static final long serialVersionUID = 5146466038407089580L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightid;
    
    private Timestamp arrivaldate;
    
    private Timestamp departuredate;
    
    private BigDecimal firstclassprice;
    
    private Integer flightnumer;
    
    private BigDecimal price;
    
    private Integer seatstaken;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planeid")
    private Plane plane;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "routeid")
    private Route route;
    
    @OneToMany(mappedBy = "flight")
    private Set<Reservation> reservations;
    
    public Flight() {
        
    }

    public Integer getFlightid() {
        return flightid;
    }

    public void setFlightid(Integer flightid) {
        this.flightid = flightid;
    }

    public Timestamp getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(Timestamp arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public Timestamp getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(Timestamp departuredate) {
        this.departuredate = departuredate;
    }

    public BigDecimal getFirstclassprice() {
        return firstclassprice;
    }

    public void setFirstclassprice(BigDecimal firstclassprice) {
        this.firstclassprice = firstclassprice;
    }

    public Integer getFlightnumer() {
        return flightnumer;
    }

    public void setFlightnumer(Integer flightnumer) {
        this.flightnumer = flightnumer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSeatstaken() {
        return seatstaken;
    }

    public void setSeatstaken(Integer seatstaken) {
        this.seatstaken = seatstaken;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

}
