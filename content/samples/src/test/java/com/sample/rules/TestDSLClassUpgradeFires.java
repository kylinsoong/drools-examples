package com.sample.rules;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.sample.domain.travel.Customer;
import com.sample.domain.travel.Reservation;

public class TestDSLClassUpgradeFires extends TestBase {

    public TestDSLClassUpgradeFires() {
        super("ksession-dsl-classupgrade");
    }
    
    @Test
    public void testDSLClassUpgradeFires() {
        Customer customer = new Customer();
        customer.setLoyaltyLevel("Bronze");
        Reservation reservation = new Reservation();
        reservation.setClassUpgrade(true);
        ksession.insert(customer);
        ksession.insert(reservation);
 
        ksession.fireAllRules();
        
        assertEquals(new BigDecimal("300.00"), reservation.getClassUpgradeFees());
        
    }

}
