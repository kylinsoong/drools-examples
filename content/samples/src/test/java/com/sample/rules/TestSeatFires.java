package com.sample.rules;

import org.junit.Test;

import com.sample.domain.Reservation;
import com.sample.domain.User;

public class TestSeatFires extends TestBase {

    public TestSeatFires() {
        super("SeatSession");
    }
    
    @Test
    public void testSeatFires() {
        Reservation reservation = new Reservation();
        reservation.setBags(2);
        ksession.insert(reservation);
        User user = new User();
        user.setLoyaltylevel("NONE");
        user.setAge(20);
        ksession.insert(user);
        
        ksession.fireAllRules();
    }

}
