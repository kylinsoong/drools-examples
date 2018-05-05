package com.sample.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Reservation implements Serializable {

	private static final long serialVersionUID = 6856375764570240249L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationid;
	
	private Integer checkedin;
	
	private String comment;
	
	private boolean upgradeclass;
	private boolean upgrademeal;
	private boolean upgradecomfort;
	private boolean bagcheck;
	private Integer bags = 0;
	private boolean upgradeentertainment;
	private boolean upgradedrink;
	private boolean tripinsurance;
	
	@Enumerated(EnumType.ORDINAL)
	private ReservationStatusType status = ReservationStatusType.NEW;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "flightid")
	private Flight flight;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatid")
	private Seat seat;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meal")
	private ValMealtype valMealtype;
	
	@OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
	private Set<Fee> fees = new HashSet<>();
	
	public Reservation() {
	    
	}

    public Integer getReservationid() {
        return reservationid;
    }

    public void setReservationid(Integer reservationid) {
        this.reservationid = reservationid;
    }

    public Integer getCheckedin() {
        return checkedin;
    }

    public void setCheckedin(Integer checkedin) {
        this.checkedin = checkedin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isUpgradeclass() {
        return upgradeclass;
    }

    public void setUpgradeclass(boolean upgradeclass) {
        this.upgradeclass = upgradeclass;
    }

    public boolean isUpgrademeal() {
        return upgrademeal;
    }

    public void setUpgrademeal(boolean upgrademeal) {
        this.upgrademeal = upgrademeal;
    }

    public boolean isUpgradecomfort() {
        return upgradecomfort;
    }

    public void setUpgradecomfort(boolean upgradecomfort) {
        this.upgradecomfort = upgradecomfort;
    }

    public boolean isBagcheck() {
        return bagcheck;
    }

    public void setBagcheck(boolean bagcheck) {
        this.bagcheck = bagcheck;
    }

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
    }

    public boolean isUpgradeentertainment() {
        return upgradeentertainment;
    }

    public void setUpgradeentertainment(boolean upgradeentertainment) {
        this.upgradeentertainment = upgradeentertainment;
    }

    public boolean isUpgradedrink() {
        return upgradedrink;
    }

    public void setUpgradedrink(boolean upgradedrink) {
        this.upgradedrink = upgradedrink;
    }

    public boolean isTripinsurance() {
        return tripinsurance;
    }

    public void setTripinsurance(boolean tripinsurance) {
        this.tripinsurance = tripinsurance;
    }

    public ReservationStatusType getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusType status) {
        this.status = status;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ValMealtype getValMealtype() {
        return valMealtype;
    }

    public void setValMealtype(ValMealtype valMealtype) {
        this.valMealtype = valMealtype;
    }

    public Set<Fee> getFees() {
        return fees;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }
	
	
}
