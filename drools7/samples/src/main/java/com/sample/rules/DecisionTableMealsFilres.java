 package com.sample.rules;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.travel.domain.Customer;
import com.sample.travel.domain.Reservation;


public class DecisionTableMealsFilres {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("ksession-dtables");
        
        Customer customer = new Customer();
        customer.setLoyaltyLevel("None");
        Reservation reservation = new Reservation();
        reservation.setMealUpgrade(true);
        ksession.insert(customer);
        ksession.insert(reservation);
        ksession.fireAllRules();
        ksession.dispose();
        
        System.out.println("Fees: " + reservation.getMealFees());
        
        ksession.dispose();
    }

}
