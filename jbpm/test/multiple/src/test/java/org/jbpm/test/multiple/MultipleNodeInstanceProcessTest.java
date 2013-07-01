package org.jbpm.test.multiple;

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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MultipleNodeInstanceProcessTest {

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
    public void multipleNodeInstanceProcessTest(){
        
        List<Number> numbers = new ArrayList<Number>();
        numbers.add(2);
        numbers.add(4);
        numbers.add(56);
        numbers.add(7);
        numbers.add(10);
        numbers.add(13);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("numberList", numbers);
        parameters.put("errorList", new ArrayList());
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.multiplenodeinstanceprocess",parameters);
        
        //The process will run until there are no more nodes to execute.
        //Because this process doesn't have any wait-state, the process is
        //running from start to end
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        //Let see the error list. It should contain 2 values: 7 and 13
        List errorList = (List) ((WorkflowProcessInstance)process).getVariable("errorList");
        
        Assert.assertFalse(errorList.isEmpty());
        Assert.assertEquals(2,errorList.size());
        Assert.assertTrue(errorList.contains(7));
        Assert.assertTrue(errorList.contains(13));
        
    }
    
    
    @Ignore("Internal signaling is not working")
    public void multipleNodeInstanceWithEscalationProcessTest(){
        
        List<Number> numbers = new ArrayList<Number>();
        numbers.add(2);
        numbers.add(4);
        numbers.add(56);
        numbers.add(7);
        numbers.add(10);
        numbers.add(13);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("numberList", numbers);
        parameters.put("errorList", new ArrayList());
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.multiplenodeinstancewithescalationprocess",parameters);
        
        //The process will run until there are no more nodes to execute.
        //Because this process doesn't have any wait-state, the process is
        //running from start to end
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        //Let see the error list. It should contain 2 values: 7 and 13
        List errorList = (List) ((WorkflowProcessInstance)process).getVariable("errorList");
        
        Assert.assertFalse(errorList.isEmpty());
        Assert.assertEquals(2,errorList.size());
        Assert.assertTrue(errorList.contains(7));
        Assert.assertTrue(errorList.contains(13));
        
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/multipleNodeInstanceProcess.bpmn"), ResourceType.BPMN2);
        kbuilder.add(new ClassPathResource("process/multipleNodeInstanceWithEscalationProcess.bpmn"), ResourceType.BPMN2);
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
