package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RemoteDefaultUtil {

	public static void startProcess() {
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("defaultKieSession");
        ksession.startProcess("project1.helloworld");
        ksession.dispose();	
	}
	
	public static void fireRules() {
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("defaultKieSession");
        ksession.fireAllRules();
        ksession.dispose();	
	}
}
