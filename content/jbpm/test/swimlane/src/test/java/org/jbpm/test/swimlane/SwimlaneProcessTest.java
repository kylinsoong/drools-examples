package org.jbpm.test.swimlane;

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
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SwimlaneProcessTest extends BaseTest implements Serializable {

	private static final long serialVersionUID = -8250671574072074393L;
	private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    private long waitTime = 1000;
    
    @Before
    public void setup() throws IOException{
        this.ksession = this.createKSession();
        
        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
        
        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= "+logFile.getAbsolutePath()+".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
        
        //Configure WIHandler for Human Tasks
        this.ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new WSHumanTaskHandler());
        
    }

    @After
    public void cleanup(){
        if (this.fileLogger != null){
            this.fileLogger.close();
        }
    } 
    
    @Test
    public void swimlaneProcessTest() throws InterruptedException{

        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.swimlaneprocess");
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        Thread.sleep(1000);
                
        //krisv has one potencial task
        BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
        client.getTasksAssignedAsPotentialOwner("krisv", "en-UK", responseHandler);
        List<TaskSummary> results = responseHandler.getResults();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary krisvsTask = results.get(0);
        
        //tony also has one potencial task (the same task)
        responseHandler = new BlockingTaskSummaryResponseHandler();
        client.getTasksAssignedAsPotentialOwner("Tony Stark", "en-UK", responseHandler);
        results = responseHandler.getResults();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary tonysTask = results.get(0);
        Assert.assertEquals(krisvsTask.getId(), tonysTask.getId());
        
        //tony claims the task
        BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.claim(tonysTask.getId(), "Tony Stark", operationResponseHandler );
        operationResponseHandler.waitTillDone(waitTime);
        
        //tony completes the task
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.start(tonysTask.getId(), "Tony Stark", operationResponseHandler);
        operationResponseHandler.waitTillDone(waitTime);
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.complete(tonysTask.getId(), "Tony Stark", null, operationResponseHandler);
        operationResponseHandler.waitTillDone(waitTime);
        
        Thread.sleep(5000);
        
        System.out.println("Process Node= "+((WorkflowProcessInstance)process).getNodeInstances().iterator().next().getNodeName());
        
        //now steve has a task
        responseHandler = new BlockingTaskSummaryResponseHandler();
        client.getTasksAssignedAsPotentialOwner("Steve Rogers", "en-UK", responseHandler);
        results = responseHandler.getResults();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary stevesTask = results.get(0);
        
        //steve completes the task
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.start(stevesTask.getId(), "Steve Rogers", operationResponseHandler);
        operationResponseHandler.waitTillDone(waitTime);
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.complete(stevesTask.getId(), "Steve Rogers", null, operationResponseHandler);
        operationResponseHandler.waitTillDone(waitTime);
        
        Thread.sleep(5000);
        
        System.out.println("Process Node= "+((WorkflowProcessInstance)process).getNodeInstances().iterator().next().getNodeName());
        
        //At this point, the third task should be assigned to tony because
        //"Task A" and "Task C" are in the same swimlane
        //So, krisv doesn't have a task now
        System.out.println("Lets see Krisv's tasks");
        responseHandler = new BlockingTaskSummaryResponseHandler();
        client.getTasksAssignedAsPotentialOwner("krisv", "en-UK", responseHandler);
        results = responseHandler.getResults();
        Assert.assertNotNull(results);
        Assert.assertTrue(results.isEmpty());
        
        //Tony has Task C, because he completed Tas A (Remember that Task A and
        //Task C are in the same swimlane)
        responseHandler = new BlockingTaskSummaryResponseHandler();
        client.getTasksAssignedAsPotentialOwner("Tony Stark", "en-UK", responseHandler);
        results = responseHandler.getResults();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        tonysTask = results.get(0);
        
        //Tony completes Task C
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.start(tonysTask.getId(), "Tony Stark", operationResponseHandler);
        operationResponseHandler.waitTillDone(waitTime);
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        client.complete(tonysTask.getId(), "Tony Stark", null, operationResponseHandler);
        
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
        kbuilder.add(new ClassPathResource("process/swimlaneProcess.bpmn"), ResourceType.BPMN2);
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
