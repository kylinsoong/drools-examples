package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_Partition {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules-partition");
        kSession.getAgenda().getAgendaGroup("partitionB").setFocus();
        kSession.fireAllRules();
        kSession.getAgenda().getAgendaGroup("partitionA").setFocus();
        kSession.fireAllRules();
        kSession.dispose();
	}

}
