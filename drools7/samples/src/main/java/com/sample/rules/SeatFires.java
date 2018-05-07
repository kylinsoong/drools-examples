package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.Reservation;
import com.sample.domain.User;

public class SeatFires {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("SeatSession");
        
        Reservation reservation = new Reservation();
        reservation.setBags(2);
        ksession.insert(reservation);
        User user = new User();
        user.setLoyaltylevel("NONE");
        user.setAge(20);
        ksession.insert(user);
        
        ksession.fireAllRules();
        
        ksession.dispose();
    }

    

}
