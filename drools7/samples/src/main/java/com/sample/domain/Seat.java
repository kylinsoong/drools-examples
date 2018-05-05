package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Seat implements Serializable {

    private static final long serialVersionUID = 7366930655978444708L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatid;
    
    private String seatname;
    
    private boolean exitrow = false;
    
    @OneToMany(mappedBy = "seat")
    private Set<Reservation> reservations;
    
    @ManyToOne
    @JoinColumn(name = "planeid")
    private Plane plane;
    
    @ManyToOne
    @JoinColumn(name = "seatcategory")
    private ValSeatclass valSeatclass;
    
    @ManyToOne
    @JoinColumn(name = "seatposition")
    private ValSeattype valSeattype;
    
    public Seat() {
        
    }

    public Integer getSeatid() {
        return seatid;
    }

    public void setSeatid(Integer seatid) {
        this.seatid = seatid;
    }

    public String getSeatname() {
        return seatname;
    }

    public void setSeatname(String seatname) {
        this.seatname = seatname;
    }

    public boolean isExitrow() {
        return exitrow;
    }

    public void setExitrow(boolean exitrow) {
        this.exitrow = exitrow;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public ValSeatclass getValSeatclass() {
        return valSeatclass;
    }

    public void setValSeatclass(ValSeatclass valSeatclass) {
        this.valSeatclass = valSeatclass;
    }

    public ValSeattype getValSeattype() {
        return valSeattype;
    }

    public void setValSeattype(ValSeattype valSeattype) {
        this.valSeattype = valSeattype;
    }

}
