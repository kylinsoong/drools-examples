package com.sample.rules.dsl;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.sample.domain.troubleticket.Customer;
import com.sample.domain.troubleticket.Ticket;

public class TroubleticketMain {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = kContainer.newKieSession("ksession-dsl-troubleticket");
        
        final Customer a = new Customer( "A", "Drools", "Gold" );
        final Customer b = new Customer( "B", "Drools", "Platinum" );
        final Customer c = new Customer( "C", "Drools", "Silver" );
        final Customer d = new Customer( "D", "Drools", "Silver" );
        
        final Ticket t1 = new Ticket( a );
        final Ticket t2 = new Ticket( b );
        final Ticket t3 = new Ticket( c );
        final Ticket t4 = new Ticket( d );
        
        ksession.insert( a );
        ksession.insert( b );
        ksession.insert( c );
        ksession.insert( d );

        ksession.insert( t1 );
        ksession.insert( t2 );
        final FactHandle ft3 = ksession.insert( t3 );
        ksession.insert( t4 );

        ksession.fireAllRules();

        t3.setStatus( "Done" );

        ksession.update( ft3, t3 );
        
        try {
            System.err.println( "[[ Sleeping 5 seconds ]]" );
            Thread.sleep( 5000 );
        } catch ( final InterruptedException e ) {
            e.printStackTrace();
        }

        System.err.println( "[[ awake ]]" );
        
        ksession.fireAllRules();
        
        ksession.dispose();
    }

}
