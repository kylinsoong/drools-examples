package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Attributes_lock_on_active {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-lockOnActive");
        
//        kSession.getAgenda().getAgendaGroup("partitionA").setFocus();
        kSession.fireAllRules();
        
        kSession.dispose();
    }

}
