package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.travel.Customer;
import com.sample.domain.travel.Reservation;

public class DSLClassUpgradeFires {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = container.newKieSession("ksession-dsl-classupgrade");
        
        Customer customer = new Customer();
        customer.setLoyaltyLevel("Bronze");
        Reservation reservation = new Reservation();
        reservation.setClassUpgrade(true);
        ksession.insert(customer);
        ksession.insert(reservation);
 
        ksession.fireAllRules();
        
        System.out.println("升舱费: " + reservation.getClassUpgradeFees());
        
        ksession.dispose();
    }

}
