package com.sample.rules.backwardchaining;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class HouseOfDoomMain {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-backwardchaining");
        
        KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "target/drools");
        
        ksession.insert( new Location("Office", "House") );
        ksession.insert( new Location("Kitchen", "House") );
        ksession.insert( new Location("Knife", "Kitchen") );
        ksession.insert( new Location("Cheese", "Kitchen") );
        ksession.insert( new Location("Desk", "Office") );
        ksession.insert( new Location("Chair", "Office") );
        ksession.insert( new Location("Computer", "Desk") );
        ksession.insert( new Location("Draw", "Desk") );
        ksession.insert( new Location("Key", "Draw") );
        
//        ksession.insert( "go1" );
//        ksession.fireAllRules();
//        System.out.println("---");

//        ksession.insert( "go2" );
//        ksession.fireAllRules();
//        System.out.println("---");

//        ksession.insert( "go3" );
//        ksession.fireAllRules();
//        System.out.println("---");

//        ksession.insert( "go4" );
//        ksession.fireAllRules();
//        System.out.println("---");

        ksession.insert( "go5" );
        ksession.fireAllRules();
        
        logger.close();
        ksession.dispose();
    }

}
