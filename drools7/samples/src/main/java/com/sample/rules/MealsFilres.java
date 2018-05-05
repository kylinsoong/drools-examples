 package com.sample.rules;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class MealsFilres {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        
        
        KieSession ksession = container.newKieSession("MealsSession");
        
        
        
        ksession.dispose();
    }

}
