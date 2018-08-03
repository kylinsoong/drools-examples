package com.sample;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.impl.PseudoClockScheduler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.model.BagScannedEvent;
import com.sample.utils.FactsLoader;

public class Rule2Main {
    
    
    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rules2");
        List<BagScannedEvent> events = FactsLoader.loadEvents();
        
        PseudoClockScheduler clock = kieSession.getSessionClock();
        long deltaTime = events.get(0).getTimestamp().getTime() - clock.getCurrentTime();
        if (deltaTime > 0) {
//            System.out.println("Advancing clock with: " + deltaTime);
            clock.advanceTime(deltaTime, TimeUnit.MILLISECONDS);
        }
        
        events.stream().forEach(event -> { insertAndFire(kieSession, event);});
    }

    private static void insertAndFire(KieSession kieSession, BagScannedEvent event) {

//        System.out.println(event);
        
        kieSession.insert(event);
        
        kieSession.fireAllRules();

    }


}
