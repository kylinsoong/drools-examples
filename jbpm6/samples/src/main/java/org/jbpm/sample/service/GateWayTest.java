package org.jbpm.sample.service;

import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;

public class GateWayTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase-service");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().knowledgeBase(kbase);
		RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "org.jbpm.sample.service:sample:1.0");
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		ksession.startProcess("org.jbpm.sample.gateway");
		manager.disposeRuntimeEngine(engine);
		System.exit(0);
	}

}
