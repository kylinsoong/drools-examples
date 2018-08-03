package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class BRMSUtil {

private KieContainer kContainer = null;
    
    public BRMSUtil() {	    
    	
    	KieServices kServices = KieServices.Factory.get();

		ReleaseId releaseId = kServices.newReleaseId( "org.kie.example", "project1", "1.0.0" );

		kContainer = kServices.newKieContainer( releaseId );

		KieScanner kScanner = kServices.newKieScanner( kContainer );

		// Start the KieScanner polling the maven repository every 10 seconds

		kScanner.start( 10000L );
    }

	public StatelessKieSession getStatelessSession() {
		return kContainer.newStatelessKieSession();
	}

	/*
	 * KieSession is the new StatefulKnowledgeSession from BRMS 5.3.
	 */
	public KieSession getStatefulSession() {
		return kContainer.newKieSession();
	}

}
