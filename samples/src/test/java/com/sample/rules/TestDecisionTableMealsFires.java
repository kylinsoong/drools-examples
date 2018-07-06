package com.sample.rules;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.sample.domain.travel.Customer;
import com.sample.domain.travel.Reservation;

public class TestDecisionTableMealsFires extends TestBase {

    public TestDecisionTableMealsFires() {
        super("ksession-dtables");
    }

    @Test
    public void testDecisionTableMealsFires() {
        Customer customer = new Customer();
        customer.setLoyaltyLevel("None");
        Reservation reservation = new Reservation();
        reservation.setMealUpgrade(true);
        ksession.insert(customer);
        ksession.insert(reservation);
        ksession.fireAllRules();
        ksession.dispose();
        
        assertEquals(new BigDecimal("15"), reservation.getMealFees());
    }
}
