package org.plugtree.training.jbpm;

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
import org.jbpm.test.subprocess.model.Car;
import org.jbpm.test.subprocess.model.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DynamicReusableSubProcessTest {

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
    public void reusableProcessTest(){
        
        //Test the process with a Person
        String personName = "Mary";
        Person person = new Person();
        person.setName(personName);
        
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("object", person);
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.dynamicreusablesubprocessparent",parameters);
        
        //Because an activation exists, the process is waiting to fireAllRules()
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        ksession.fireAllRules();
        
        //Now the process is completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //The right subprocess was chosen and the Person's name changed
		System.out.println("Person's name= " + person.getName());
        Assert.assertFalse(person.getName().equals(personName));
        
        
        //Now test the process with a Car
        String carBrand = "Ford";
        String carModel = "Mustang";
        
        Car car = new Car();
        car.setBrand(carBrand);
        car.setModel(carModel);
        
        parameters = new HashMap<String, Object>();
        parameters.put("object", car);
        
        //Start the process using its id
        process = ksession.startProcess("org.jbpm.test.dynamicreusablesubprocessparent",parameters);
        
        //Because an activation exists, the process is waiting to fireAllRules()
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        ksession.fireAllRules();
        
        //Now the process is completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //The right subprocess was chosen and the Person's name changed
        System.out.println("Car's brand= "+car.getModel());
        System.out.println("Car's model= "+car.getBrand());
        Assert.assertFalse(car.getModel().equals(carModel));
        Assert.assertFalse(car.getBrand().equals(carBrand));
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("rules/dynamic/subProcessSelectionRules.drl"), ResourceType.DRL);
        kbuilder.add(new ClassPathResource("process/dynamic/dynamicReusableSubProcess-Parent.bpmn"), ResourceType.BPMN2);
        kbuilder.add(new ClassPathResource("process/dynamic/personTaggerProcess.bpmn"), ResourceType.BPMN2);
        kbuilder.add(new ClassPathResource("process/dynamic/carTaggerProcess.bpmn"), ResourceType.BPMN2);
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
