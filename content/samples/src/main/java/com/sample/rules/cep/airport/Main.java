package com.sample.rules.cep.airport;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.time.SessionClock;
import org.kie.api.time.SessionPseudoClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.domain.airport.Event;
import com.sample.domain.airport.Fact;

public class Main {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

//    private static final String EVENTS_CSV_FILE = "events.csv";

    private static final String CEP_STREAM = "AirportStream";

    public static void main(String[] args) {
        
        LOGGER.info("Initialize KIE.");
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = kContainer.newKieSession("ksession-airport");

        List<Event> events = FactsLoader.loadEvents(new File("src/main/resources/events.csv"));
        
        events.forEach(e -> {
            insert(ksession, CEP_STREAM, e);
            ksession.fireAllRules();
        });
        
        ksession.dispose();
    }

    private static FactHandle insert(KieSession kieSession, String stream, Fact fact) {
        LOGGER.info("Inserting fact with id: '" + fact.getId() + "' into stream: " + stream);
        SessionClock clock = kieSession.getSessionClock();
        if (!(clock instanceof SessionPseudoClock)) {
            String errorMessage = "This fact inserter can only be used with KieSessions that use a SessionPseudoClock";
            LOGGER.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        SessionPseudoClock pseudoClock = (SessionPseudoClock) clock;
        EntryPoint ep = kieSession.getEntryPoint(stream);

        // First insert the fact
        LOGGER.debug("Inserting fact: " + fact.toString());
        FactHandle factHandle = ep.insert(fact);

        // And then advance the clock
        // We only need to advance the time when dealing with Events. Our facts don't have timestamps.
        if (fact instanceof Event) {

            long advanceTime = ((Event) fact).getTimestamp().getTime() - pseudoClock.getCurrentTime();
            if (advanceTime > 0) {
                LOGGER.info("Advancing the PseudoClock with " + advanceTime + " milliseconds.");
                pseudoClock.advanceTime(advanceTime, TimeUnit.MILLISECONDS);
            } else {
                LOGGER.info("Not advancing time. Fact timestamp is '" + ((Event) fact).getTimestamp().getTime()
                        + "', PseudoClock timestamp is '" + pseudoClock.getCurrentTime() + "'.");
            }

        }
        return factHandle;
    }


}
