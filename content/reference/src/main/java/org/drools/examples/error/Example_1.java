package org.drools.examples.error;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_1 {

	public static void main(String[] args) {

		 KieServices ks = KieServices.Factory.get();
         KieContainer kContainer = ks.getKieClasspathContainer();
         KieSession kSession = kContainer.newKieSession("ksession-rules-errors");
         kSession.fireAllRules();
         kSession.dispose();
	}

}
