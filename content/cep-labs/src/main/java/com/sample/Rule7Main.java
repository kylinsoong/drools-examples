package com.sample;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.impl.PseudoClockScheduler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.model.*;
import com.sample.utils.FactsLoader;

public class Rule7Main {
    
    
    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rules7");
        
//        kieSession.addEventListener(new LoggingAgendaEventListener());
//        kieSession.addEventListener(new LoggingRuleRuntimeEventListener());
//        
        List<BagScannedEvent> events = FactsLoader.loadEvents("events-extra.csv");
        System.out.println("All events:");
        events.forEach(e -> System.out.println("    " + e));
        
        events.stream().forEach(event -> { insertAndFire(kieSession, event);});
    }

    private static void insertAndFire(KieSession kieSession, BagScannedEvent event) {

//        System.out.println(event);
        PseudoClockScheduler clock = kieSession.getSessionClock();
        Location location = event.getLocation();
        switch(location) {
        case CHECK_IN :
            kieSession.getEntryPoint("CheckIn").insert(event);
            break;
        case SORTING :
            kieSession.getEntryPoint("Sorting").insert(event);
            break;
        case STAGING :
            kieSession.getEntryPoint("Staging").insert(event);
            break;
        case LOADING :
            kieSession.getEntryPoint("Loading").insert(event);
            break; 
        default:
            throw new IllegalArgumentException("Unexpected location.");
        }
        kieSession.insert(event);
        long deltaTime = event.getTimestamp().getTime() - clock.getCurrentTime();
        if (deltaTime > 0) {
//            System.out.println("Advancing clock with: " + deltaTime);
            clock.advanceTime(deltaTime, TimeUnit.MILLISECONDS);
        }
        kieSession.fireAllRules();

    }


}
