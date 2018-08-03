package com.sample.agent;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.sample.DroolsTest.Message;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsKnowledgeAgentTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            // KnowledgeBase kbase = readKnowledgeBase();
            KnowledgeBase kbase = DroolsKnowledgeAgentTest.readKnowledgeBaseCS();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !
            System.out.println("go");
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            ksession.insert(message);
            System.out.println("gona fire");
            ksession.fireAllRules();
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private static KnowledgeBase readKnowledgeBaseCS() throws Exception {
        final KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");    
        ResourceFactory.getResourceChangeNotifierService().start();
        ResourceFactory.getResourceChangeScannerService().start();
        kagent.setSystemEventListener(new PrintStreamSystemEventListener());
        kagent.applyChangeSet(ResourceFactory.newClassPathResource("changeset.xml"));       
        KnowledgeBase kbase = kagent.getKnowledgeBase();
        return kbase;
    }    


}
