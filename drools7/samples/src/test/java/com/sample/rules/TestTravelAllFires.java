package com.sample.rules;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.kie.api.runtime.rule.FactHandle;

import com.sample.domain.travel.Customer;
import com.sample.domain.travel.Fee;
import com.sample.domain.travel.Reservation;

public class TestTravelAllFires extends TestBase {

    public TestTravelAllFires() {
        super("TravelsSession");
    }
    
    @Test
    public void testFiresAll() {
        
        Reservation reservation = new Reservation();
        reservation.setBagsChecked(3); 
        reservation.setClassUpgrade(true);
        reservation.setComfortUpgrade(true);
        reservation.setDrinkUpgrade(true);
        reservation.setEntertainmentUpgrade(true);
        reservation.setFlightInsurance(true);
        reservation.setMealUpgrade(true);
        ksession.insert(reservation);
        
        
        Customer customer = new Customer();
        customer.setLoyaltyLevel("Bronze");
        customer.setAge(22);
        ksession.insert(customer);
        
        ksession.fireAllRules();
        
        assertEquals(10, ksession.getFactCount());
        
        Collection<Fee> fees = new HashSet<Fee>();
        for(FactHandle handle : ksession.getFactHandles()) {
            Object obj = ksession.getObject(handle);
            if(obj instanceof Fee) {
                fees.add((Fee)obj);
            }
        }
        
        BigDecimal sum = fees.stream().map(f -> f.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        
        assertEquals(new BigDecimal("599"), sum);
        
    }
    
    

}
