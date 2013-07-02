package org.jbpm.test.event;

import java.io.File;
import java.io.IOException;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DelayTimerEventProcessTest {

    private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    
    @Before
    public void setup() throws IOException{
    	
        this.ksession = this.createKSession();
        
        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
        
        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= "+logFile.getAbsolutePath()+".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
    }

    @After
    public void cleanup(){
        if (this.fileLogger != null){
            this.fileLogger.close();
        }
    } 
    
    @Test
    public void delayTimerEventProcessTest() throws InterruptedException{
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.delaytimereventprocess");
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
       
        int sleep = 5000;
        System.out.println("Sleeping " + sleep/1000 + " seconds." );
        for(int i = 5 ; i > 0 ; i --){
        	System.out.println("    " + i);
        	Thread.sleep(1000);
        }
        System.out.println("Awake!");
        
        //The process continues until it reaches the end node
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        Long timerExecutionTime = (Long) ((WorkflowProcessInstance)process).getVariable("timerExecutionTime");
        Assert.assertTrue(timerExecutionTime >= 2000);
        
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/delayTimerEventProcess.bpmn"), ResourceType.BPMN2);
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

        //return a new statefull session
        return kbase.newStatefulKnowledgeSession();
    }
}
