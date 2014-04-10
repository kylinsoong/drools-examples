package org.drools.examples.ksession.test;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class LoadFromRemoteViaAgentTest {
	
	private KnowledgeAgent kagent;
	
	public LoadFromRemoteViaAgentTest() {
		kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");    
		ResourceChangeScannerConfiguration sconf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		sconf.setProperty("drools.resource.scanner.interval","30");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.applyChangeSet(ResourceFactory.newClassPathResource("changeset.xml"));
	}
	

	private StatefulKnowledgeSession readKnowledgeSession(String changeset) throws Exception {
		
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}
	
	private void test() throws Exception {
		
		int i = 1;
		
		while(true) {
			System.out.println("\n" + i);
			StatefulKnowledgeSession ksession = readKnowledgeSession("changeset.xml");
			ksession.fireAllRules();
			ksession.dispose();
			i++;
			Thread.currentThread().sleep(5000);
		}
	}

	public static void main(String[] args) throws Exception {

		new LoadFromRemoteViaAgentTest().test();
	}

}
