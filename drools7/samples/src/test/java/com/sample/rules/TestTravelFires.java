package com.sample.rules;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sample.domain.Reservation;
import com.sample.domain.User;

public class TestTravelFires extends TestBase {

    public TestTravelFires() {
        super("MealSession");
    }
    
    @Test
    public void testMealsFires() {
        Reservation reservation = new Reservation();
        reservation.setUpgrademeal(true);
        ksession.insert(reservation);
        User user = new User();
        user.setLoyaltylevel("None");
        ksession.insert(user);
        
        ksession.fireAllRules();
        assertEquals(3, ksession.getFactCount());
        
    }

}
