package org.drools.examples.ksession.test;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class Case01074704Reproduce {
	
	private StatefulKnowledgeSession ksession;
	
	private KnowledgeAgent kagent;
	
	public Case01074704Reproduce() {
		KnowledgeBaseConfiguration kconfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kconfig.setOption(EventProcessingOption.STREAM);
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kconfig);
		kbase.addEventListener(new KnowledgeBaseEventListenerImpl());
		
		KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		kaconf.setProperty("drools.agent.scanDirectories", "true");
		kaconf.setProperty("drools.agent.scanResources", "true");
		kaconf.setProperty("drools.agent.newInstance", "false");
		
		KnowledgeBuilderConfiguration kbc = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();

		kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent", kbase, kaconf, kbc); 
		ResourceChangeScannerConfiguration sconf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		sconf.setProperty("drools.resource.scanner.interval","30");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.applyChangeSet(ResourceFactory.newClassPathResource("conf/changeset.xml"));
		
//		ksession = kagent.getKnowledgeBase().newStatefulKnowledgeSession();
		
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.addEventListener(new KnowledgeAgentEventListenerImpl());
		kagent.monitorResourceChangeEvents(true);
	}
	
	public void test() throws InterruptedException {
		int i = 0;
		while(true) {
			System.out.println("\n" + i++);
			ksession = kagent.getKnowledgeBase().newStatefulKnowledgeSession();
			ksession.fireAllRules();
			ksession.dispose();
			Thread.currentThread().sleep(1000 * 10);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new Case01074704Reproduce().test();
	}

}
