package com.sample.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public abstract class Fee implements Serializable {

    private static final long serialVersionUID = -8749705701229740399L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feeid;
    
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn(name = "reservationid")
    private Reservation reservation;
    
    public Fee() {
        
    }
    
    public Fee(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getFeeid() {
        return feeid;
    }

    public void setFeeid(Integer feeid) {
        this.feeid = feeid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    
    @Override
    public String toString() {
        return "Amount: " + amount;
    }

    public abstract String getFeeType();

}
