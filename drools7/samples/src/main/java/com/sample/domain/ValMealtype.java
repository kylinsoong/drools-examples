package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class ValMealtype implements Serializable {

    private static final long serialVersionUID = 203580960816573203L;
    
    @Id
    private String meal;
    
    @OneToMany(mappedBy = "valMealtype")
    private Set<Reservation> reservations;

    public ValMealtype() {
        
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
