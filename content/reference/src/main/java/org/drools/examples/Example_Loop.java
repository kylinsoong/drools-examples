package org.drools.examples;

import org.drools.examples.model.Cursor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_Loop {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules-no-loop");
        
        Cursor cur = new Cursor(5);
        kSession.insert(cur);
        kSession.fireAllRules();
        kSession.dispose();
	}

}
