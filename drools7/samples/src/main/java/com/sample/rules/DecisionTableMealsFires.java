 package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.travel.Customer;
import com.sample.domain.travel.Reservation;


public class DecisionTableMealsFires {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
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
