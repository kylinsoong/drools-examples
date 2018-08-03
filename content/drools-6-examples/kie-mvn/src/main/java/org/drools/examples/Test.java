package org.drools.examples;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;

public class Test {
	
	static {
//		JBPMHelper.startH2Server();
//		JBPMHelper.setupDataSource();
	}

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");

	public static void main(String[] args) {
		KieServices kServices = KieServices.Factory.get();
		ReleaseId releaseId = kServices.newReleaseId( "org.brms", "HelloWorld", "LATEST" );
		KieContainer kContainer = kServices.newKieContainer( releaseId );
		KieScanner kScanner = kServices.newKieScanner( kContainer );
		KieBase kbase = kContainer.getKieBase();
		
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(emf).knowledgeBase(kbase);
		RuntimeManager runtimeManager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "com.sample:example:1.0");
		RuntimeEngine engine = runtimeManager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		ksession.startProcess("com.sample");
	}

}
