package org.jbpm.test.event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.impl.ClassPathResource;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.test.event.handler.HumanTaskMockHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoundaryTimerEventProcessTest {

    private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    private HumanTaskMockHandler humanTaskMockHandler;

    @Before
    public void setup() throws IOException {
        this.ksession = this.createKSession();

        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);

        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= " + logFile.getAbsolutePath() + ".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, logFile.getAbsolutePath());
    }

    @After
    public void cleanup() {
        if (this.fileLogger != null) {
            this.fileLogger.close();
        }
    }

    @Test
    public void validScenarioTest() throws InterruptedException {
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("emailService", new ArrayList<String>()); 
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.BoundaryTimerEvent",parameters);

        //The process is in the Human Task waiting for its completion
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

        //wait 5 seconds to complete the task
        Thread.sleep(5000);
        
        //The Human Task is completed
        this.humanTaskMockHandler.completeWorkItem();

        //The process reaches the end node
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //The boss shouldn't be notified
        List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable("emailService");
        Assert.assertTrue(emailService.isEmpty());
    }
    
    
    @Test
    public void invalidScenarioTest() throws InterruptedException {
        
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("emailService", new ArrayList<String>()); 
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.BoundaryTimerEvent",parameters);

        //The process is in the Human Task waiting for its completion
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

        //wait 1 minute to see if the boss is notified
        Thread.sleep(60000);
        
        //The Human Task is completed
        this.humanTaskMockHandler.completeWorkItem();
        
        //The process reaches the end node
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //The boss should have been notified
        List<String> emailService = (List<String>) ((WorkflowProcessInstance)process).getVariable("emailService");

        Assert.assertFalse(emailService.isEmpty());
    }

    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession() {
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/boundaryTimerProcess.bpmn"), ResourceType.BPMN2);
        System.out.println("Compiling resources");

        //Check for errors
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                    System.out.println("Error building kbase: " + error.getMessage());
                }
            }
            throw new RuntimeException("Error building kbase!");
        }

        //Create a knowledge base and add the generated package
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        //create a new statefull session
        final StatefulKnowledgeSession newSession = kbase.newStatefulKnowledgeSession();
        //Register Human Task Handler
        humanTaskMockHandler = new HumanTaskMockHandler();

        newSession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskMockHandler);

        return newSession;
    }
}