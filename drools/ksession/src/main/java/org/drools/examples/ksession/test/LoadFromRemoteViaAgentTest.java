package org.drools.examples.ksession.test;

import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.event.knowledgeagent.AfterChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.AfterChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.AfterResourceProcessedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.BeforeResourceProcessedEvent;
import org.drools.event.knowledgeagent.KnowledgeAgentEventListener;
import org.drools.event.knowledgeagent.KnowledgeBaseUpdatedEvent;
import org.drools.event.knowledgeagent.ResourceCompilationFailedEvent;
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
		kagent.addEventListener(new KnowledgeAgentEventListener() {

			public void beforeChangeSetApplied(BeforeChangeSetAppliedEvent event) {
				
			}

			public void afterChangeSetApplied(AfterChangeSetAppliedEvent event) {
				
			}

			public void beforeChangeSetProcessed( BeforeChangeSetProcessedEvent event) {
				
			}

			public void afterChangeSetProcessed(AfterChangeSetProcessedEvent event) {
				
			}

			public void beforeResourceProcessed(BeforeResourceProcessedEvent event) {
				
			}

			public void afterResourceProcessed(AfterResourceProcessedEvent event) {
				
			}

			public void knowledgeBaseUpdated(KnowledgeBaseUpdatedEvent event) {
				//event.getKnowledgeBase() is the updated kbase.
				event.getKnowledgeBase().newStatefulKnowledgeSession().fireAllRules();
			}

			public void resourceCompilationFailed(ResourceCompilationFailedEvent event) {
				
			}});
	}
	
	
	private void test() throws Exception {
		
		int i = 1;
		
		Thread.currentThread().sleep(Long.MAX_VALUE);
		
//		while(true) {
//			System.out.println("\n" + i);
//			StatefulKnowledgeSession ksession = kagent.getKnowledgeBase().newStatefulKnowledgeSession();
//			ksession.fireAllRules();
//			ksession.dispose();
//			i++;
//			Thread.currentThread().sleep(5000);
//		}
	}

	public static void main(String[] args) throws Exception {

		new LoadFromRemoteViaAgentTest().test();
	}

}
