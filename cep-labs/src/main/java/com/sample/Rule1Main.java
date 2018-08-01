package com.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.impl.PseudoClockScheduler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.FactsLoader;
import com.sample.model.Event;

public class Rule1Main {
    
    private static final String EVENTS_FILE_NAME = "events.csv";

    public static void main(String[] args) {
            KieServices kieServices = KieServices.Factory.get();
            KieContainer kieContainer = kieServices.getKieClasspathContainer();
            KieSession kieSession = kieContainer.newKieSession();
            List<Event> events;
            try(InputStream eventFileInputStream = Rule1Main.class.getClassLoader().getResourceAsStream(EVENTS_FILE_NAME)) {
                    events = FactsLoader.loadEvents(eventFileInputStream);
            } catch (IOException ioe) {
                    throw new RuntimeException("I/O problem loading event file. Not much we can do in this lab.", ioe);

            }
            events.stream().forEach(event -> { insertAndFire(kieSession, event);});
    }

    private static void insertAndFire(KieSession kieSession, Event event) {

//        System.out.println(event);
        PseudoClockScheduler clock = kieSession.getSessionClock();
        kieSession.insert(event);
        long deltaTime = event.getTimestamp().getTime() - clock.getCurrentTime();
        if (deltaTime > 0) {
                System.out.println("Advancing clock with: " + deltaTime);
                clock.advanceTime(deltaTime, TimeUnit.MILLISECONDS);
        }
        kieSession.fireAllRules();

        System.out.println();
    }


}
