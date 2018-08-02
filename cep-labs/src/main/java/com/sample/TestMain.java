package com.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sample.model.BagScannedEvent;
import com.sample.utils.FactsLoader;

public class TestMain {

    public static void main(String[] args) {

        List<BagScannedEvent> events;
        try(InputStream eventFileInputStream = Rule1Main.class.getClassLoader().getResourceAsStream("events.csv")) {
                events = FactsLoader.loadEvents(eventFileInputStream);
        } catch (IOException ioe) {
                throw new RuntimeException("I/O problem loading event file. Not much we can do in this lab.", ioe);

        }
        
        events.forEach(e -> System.out.println(e));
    }

}
