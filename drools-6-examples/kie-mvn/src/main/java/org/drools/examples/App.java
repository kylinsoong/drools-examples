package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class App {

	public static void main(String[] args) {
		System.out.println("\nDrools 6.x fire rules");
		
		fireRules();
		
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId releaseId = kieServices.newReleaseId( "org.brms", "HelloWorld", "1.0" );
		KieContainer kContainer = kieServices.newKieContainer( releaseId );
		KieSession ksession = kContainer.newKieSession("defaultKsession");
		ksession.fireAllRules();
		ksession.dispose();
		
	}

	private static void fireRules() {
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("defaultKsession");
        ksession.fireAllRules();
        ksession.dispose();	
        ksession.destroy();
	}

}
