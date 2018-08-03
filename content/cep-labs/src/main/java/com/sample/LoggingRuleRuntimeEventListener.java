package com.sample;

import org.drools.core.event.DefaultRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;

public class LoggingRuleRuntimeEventListener extends DefaultRuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("Object inserted: " + event.getObject().toString());
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("Event deleted from WorkingMemory: " + event.getOldObject());
        System.out.println("Number of facts in session: " + event.getKieRuntime().getFactCount());
    }

}
