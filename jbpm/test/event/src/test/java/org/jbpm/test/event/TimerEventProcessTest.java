/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.test.event;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author esteban
 */
public class TimerEventProcessTest {

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

//    @Ignore("JBPM5 doesn't support iterative timers yet.")
    @Test
    public void simpleProcessTest() throws InterruptedException {
        
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("bill", new Float(0));
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.timereventprocess",parameters);

        //The process is in the Human Task waiting for its completion
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());

        new Thread() {
            @Override
            public void run() {
                ksession.fireUntilHalt();
            }
        }.start();
        
        //wait 1 minute to see how the bill gets incremented. Check the console!
        Thread.sleep(60000);
        
        Double bill = (Double) ((WorkflowProcessInstance)process).getVariable("bill");
        
        System.out.println("Total Bill= "+bill);
        
        //The Human Task is completed
        this.humanTaskMockHandler.completeWorkItem();

        //The process reaches the end node
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    }

    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession() {
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/timerEventProcess.bpmn"), ResourceType.BPMN2);
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