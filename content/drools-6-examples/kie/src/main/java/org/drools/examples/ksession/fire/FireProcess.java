package org.drools.examples.ksession.fire;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class FireProcess {
	
	public static void fireRulesOnRemoteServer() {
		KieServices ks = KieServices.Factory.get();
		ReleaseId releaseId = ks.newReleaseId("org.brms", "HelloWorld","1.0");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession ksession = kContainer.newKieSession("defaultKieSession");
        ksession.fireAllRules();
        ksession.dispose();
	}

	public static void main(String[] args) throws Exception {
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("defaultKieSession");
        ksession.fireAllRules();
        ksession.dispose();
	}
	

}
