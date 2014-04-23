package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsMain {

	public static void main(String[] args) throws InterruptedException {
		
		KieServices kServices = KieServices.Factory.get();
		ReleaseId releaseId = kServices.newReleaseId( "org.kie.example", "project1", "1.2.0-SNAPSHOT" );
		KieContainer kContainer = kServices.newKieContainer( releaseId );
//		KieScanner kScanner = kServices.newKieScanner( kContainer );
//		kScanner.start( 1000 * 10);
//		
//		
//		while(true) {
//			KieSession ksession = kContainer.newKieSession();
//            ksession.fireAllRules();
//
//            Thread.sleep(10000);
//		}
	}

}
