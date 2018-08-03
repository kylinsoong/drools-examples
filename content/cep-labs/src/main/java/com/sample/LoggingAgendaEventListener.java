package com.sample;

import java.util.List;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.event.rule.BeforeMatchFiredEvent;

public class LoggingAgendaEventListener extends DefaultAgendaEventListener {

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        System.out.println("Match fired: " + event.getMatch().getRule().getName());
        List<Object> objects = event.getMatch().getObjects();
        objects.stream().forEach(object -> {System.out.println("    Object match: " + object);});
    }

    
}
