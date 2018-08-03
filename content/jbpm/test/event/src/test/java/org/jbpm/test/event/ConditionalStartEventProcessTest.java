package org.jbpm.test.event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.jbpm.test.event.model.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConditionalStartEventProcessTest {

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
    public void conditionalStartEventProcessTest(){
        //instanciate 2 Person objects
        Person john = new Person("John");
        Person peter = new Person("Peter");
        
        
        //Because we are not going to start the process manualy (i.e. ksession.startPRocess()),
        //we can't pass any parameter to the process. That is why we are going
        //to use a global instead of a process variable to communicate with it
        List<Person> executionHistory = new ArrayList<Person>();
        
        ksession.setGlobal("executionHistory", executionHistory);
        
        //we insert Peter in the ksession, nothing should happpen because
        //the process only starts if a Person with name John is found in the session
        ksession.insert(peter);
        
        //We need to fire all the activated rules (if any). This is what is 
        //going to start the process if the condition in its Start Event Node
        //is met
        ksession.fireAllRules();
        
        Assert.assertTrue(executionHistory.isEmpty());
        
        //now lets insert John and see what happen
        ksession.insert(john);
        
        //Again, we need to fire all the activated rules
        ksession.fireAllRules();
        
        //The process should have run and the global list should contain 1 element
        Assert.assertFalse(executionHistory.isEmpty());
        Assert.assertEquals(1, executionHistory.size());
    }
    
    @Test
    public void conditionalStartEventProcessWithMultipleSeparatedObjectsTest(){
        //instanciate 3 Person objects
        Person john1 = new Person("John");
        Person john2 = new Person("John");
        Person peter = new Person("Peter");
        
        
        //Because we are not going to start the process manualy (i.e. ksession.startPRocess()),
        //we can't pass any parameter to the process. That is why we are going
        //to use a global instead of a process variable to communicate with it
        List<Person> executionHistory = new ArrayList<Person>();
        
        ksession.setGlobal("executionHistory", executionHistory);
        
        //we insert Peter in the ksession, nothing should happpen because
        //the process only starts if a Person with name John is found in the session
        ksession.insert(peter);
        
        //We need to fire all the activated rules (if any). This is what is 
        //going to start the process if the condition in its Start Event Node
        //is met
        ksession.fireAllRules();
        
        Assert.assertTrue(executionHistory.isEmpty());
        
        //now lets insert a John and see what happen
        ksession.insert(john1);
        
        //Again, we need to fire all the activated rules
        ksession.fireAllRules();
        
        //The process should have run and the global list should contain 1 element
        Assert.assertFalse(executionHistory.isEmpty());
        Assert.assertEquals(1, executionHistory.size());
        
        //now, insert another John
        //now lets insert a John and see what happen
        ksession.insert(john2);
        
        //Again, we need to fire all the activated rules
        ksession.fireAllRules();
        
        //The process should have run and the global list should contain 2 elements
        //(because we didn't empty the list)
        Assert.assertFalse(executionHistory.isEmpty());
        Assert.assertEquals(2, executionHistory.size());
    }
    
    @Test
    public void conditionalStartEventProcessWithMultipleObjectsAtTheSameTimeTest(){
        //instanciate 3 Person objects
        Person john1 = new Person("John");
        Person john2 = new Person("John");
        Person peter = new Person("Peter");
        
        
        //Because we are not going to start the process manualy (i.e. ksession.startPRocess()),
        //we can't pass any parameter to the process. That is why we are going
        //to use a global instead of a process variable to communicate with it
        List<Person> executionHistory = new ArrayList<Person>();
        
        ksession.setGlobal("executionHistory", executionHistory);
        
        //we insert Peter in the ksession, nothing should happpen because
        //the process only starts if a Person with name John is found in the session
        ksession.insert(peter);
        
        //We need to fire all the activated rules (if any). This is what is 
        //going to start the process if the condition in its Start Event Node
        //is met
        ksession.fireAllRules();
        
        Assert.assertTrue(executionHistory.isEmpty());
        
        //now lets insert two Johns and see what happen
        ksession.insert(john1);
        ksession.insert(john2);
        
        //Again, we need to fire all the activated rules
        ksession.fireAllRules();
        
        //The process should have run and the global list should contain 1 element
        Assert.assertFalse(executionHistory.isEmpty());
        Assert.assertEquals(2, executionHistory.size());
        
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add the drl containing the global definition to kbuilder
        kbuilder.add(new ClassPathResource("rule/conditionalStartEventRule.drl"), ResourceType.DRL);
        //Add process to kbuilder
        kbuilder.add(new ClassPathResource("process/conditionalStartEventProcess.bpmn"), ResourceType.BPMN2);
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
