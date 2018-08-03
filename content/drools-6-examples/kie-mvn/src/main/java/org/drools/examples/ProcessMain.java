package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ProcessMain {

	public static void main(String[] args) throws InterruptedException {
				
		KieServices kServices = KieServices.Factory.get();
		ReleaseId releaseId = kServices.newReleaseId( "org.kie.example", "project1", "LATEST" );
		KieContainer kContainer = kServices.newKieContainer( releaseId );
		KieScanner kScanner = kServices.newKieScanner( kContainer );
		kScanner.start( 10000L );
		
		while(true) {
			KieSession ksession = kContainer.newKieSession("defaultKieSession");
			ksession.startProcess("project1.helloworld");
			ksession.dispose();	
			Thread.sleep(1000 * 5);
		}
		
		
	}

	
}
