package com.sample;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.sample.DroolsTest.Message;

public class DroolsTestAgent {

	 public static final void main(String[] args) {
	        try {
	            KnowledgeBase kbase = DroolsTestAgent.readKnowledgeBase();
	            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
	           
	            Message message = new Message();
	            message.setMessage("Hello World");
	            message.setStatus(Message.HELLO);
	            ksession.insert(message);
	            ksession.fireAllRules();
	            logger.close();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	    }
	 
	 private static KnowledgeBase readKnowledgeBase() throws Exception {
	        final KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");    
	        ResourceFactory.getResourceChangeNotifierService().start();
	        ResourceFactory.getResourceChangeScannerService().start();
	        kagent.setSystemEventListener(new PrintStreamSystemEventListener());
	        kagent.applyChangeSet(ResourceFactory.newClassPathResource("changeset.xml"));       
	        KnowledgeBase kbase = kagent.getKnowledgeBase();
	        return kbase;
	    }  

}
