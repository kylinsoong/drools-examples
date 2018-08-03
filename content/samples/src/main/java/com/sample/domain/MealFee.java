package com.sample.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MEAL")
public class MealFee extends Fee {

    private static final long serialVersionUID = 8556745745479760724L;
    
    public MealFee () {
        
    }
    
    public MealFee(BigDecimal amount) {
        super(amount);
    }

    @Override
    public String getFeeType() {
        return "Meal Upgrade";
    }

}
