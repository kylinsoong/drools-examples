package com.sample.rules.state;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class StateExampleUsingAgendaGroup {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-state-agendagroup");
        
        final State a = new State( "A" );
        final State b = new State( "B" );
        final State c = new State( "C" );
        final State d = new State( "D" );
        
        ksession.insert( a );
        ksession.insert( b );
        ksession.insert( c );
        ksession.insert( d );
        
        ksession.fireAllRules();
        
        ksession.dispose();
        
    }

}
