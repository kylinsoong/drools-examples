package org.jbpm.test.lifecycle;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.query.TaskSummary;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DelegationProcessTest extends BaseTest implements Serializable {

	private static final long serialVersionUID = 3258872100556762544L;
	private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    private SyncWSHumanTaskHandler humanTaskHandler;
    
    @Before
    public void setup() throws IOException{
        this.ksession = this.createKSession();
        
        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
        
        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= "+logFile.getAbsolutePath()+".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
        
        //Configure Sync WIHandler for Human Tasks
        humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        
        this.ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        
    }

    @After
    public void cleanup() throws Exception{
        humanTaskHandler.dispose();
        
        if (this.fileLogger != null){
            this.fileLogger.close();
        }
    } 
    
    @Ignore
    public void delegationOfUnclaimedTaskTest() throws InterruptedException{

        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.plugtree.training.jbpm.sampleprocess");
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        Thread.sleep(500);
        
        //krisv has one potencial task
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary krisvsTask = results.get(0);
        
        //tony doesn't have any :(
        results = localTaskService.getTasksAssignedAsPotentialOwner("Tony Stark", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertTrue(results.isEmpty());

        //But, wait a minute... Krisv is on vacations! The task needs to be
        //delegated.
        localTaskService.delegate(krisvsTask.getId(), "krisv", "Tony Stark");
        
        //Now, Tony owns the task
        results = localTaskService.getTasksOwned("Tony Stark", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary tonysTask = results.get(0);   
        Assert.assertEquals(tonysTask.getId(), krisvsTask.getId());
               
        
        //tony claims the task
        localTaskService.claim(tonysTask.getId(), "Tony Stark");
        
        //tony completes the task
        localTaskService.start(tonysTask.getId(), "Tony Stark");
        localTaskService.complete(tonysTask.getId(), "Tony Stark", null);
        
        Thread.sleep(5000);
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    }
    
    @Test
    public void delegationOfClaimedTaskTest() throws InterruptedException{

        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.sampleprocess");
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        Thread.sleep(500);
        
        //krisv has one potencial task
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary krisvsTask = results.get(0);
        
        //tony doesn't have any :(
        results = localTaskService.getTasksAssignedAsPotentialOwner("Tony Stark", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertTrue(results.isEmpty());
        
        //krisv claims the task
        localTaskService.claim(krisvsTask.getId(), "krisv");

        //Some days later, krisv goes on vacation. All its tasks should be
        //delegated to Tony
        localTaskService.delegate(krisvsTask.getId(), "krisv", "Tony Stark");
        
        //Now, Tony owns the task
        results = localTaskService.getTasksOwned("Tony Stark", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary tonysTask = results.get(0);   
        Assert.assertEquals(tonysTask.getId(), krisvsTask.getId());
               
        
        //tony claims the task
        localTaskService.claim(tonysTask.getId(), "Tony Stark");
        
        //tony completes the task
        localTaskService.start(tonysTask.getId(), "Tony Stark");
        localTaskService.complete(tonysTask.getId(), "Tony Stark", null);
        
        Thread.sleep(2000);
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/sampleProcess.bpmn"), ResourceType.BPMN2);
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
