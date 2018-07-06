package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.bc.Location;


public class BackwardChaining {

    public static void main(String[] args) {


        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-bc");
        
        ksession.insert(new Location("office","house"));
        ksession.insert(new Location("kitchen","house"));
        ksession.insert(new Location("apple","kitchen"));
        ksession.insert(new Location("desk","office"));
        ksession.insert(new Location("flashlight","desk"));
        ksession.insert(new Location("envelope","desk"));
        ksession.insert(new Location("key","envelope"));
        
        ksession.fireAllRules();
        
        ksession.dispose();
    }

}
