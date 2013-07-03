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
import org.junit.Test;

public class EscalationTaskTest extends BaseTest implements Serializable{

	private static final long serialVersionUID = -5171718948555731390L;
	
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
	    
	@Test
	public void normalExecuteTest() {

		ProcessInstance process = ksession.startProcess("org.jbpm.test.escalationprocess");
	        
		Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
	        
		// krisv extract Task
		List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		TaskSummary krisvsTask = results.get(0);
		
		localTaskService.start(krisvsTask.getId(), "krisv");
		localTaskService.complete(krisvsTask.getId(), "krisv", null);
		
		//The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
	}
	    
	public StatefulKnowledgeSession createKSession(){
		
		// Create the kbuilder
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		// Add simpleProcess.bpmn to kbuilder
		kbuilder.add(new ClassPathResource("process/escalationSampleProcess.bpmn"), ResourceType.BPMN2);
		System.out.println("Compiling resources");
	        
		// Check for errors
		if (kbuilder.hasErrors()) {
			if (kbuilder.getErrors().size() > 0) {
				for (KnowledgeBuilderError error : kbuilder.getErrors()) {
					System.out.println("Error building kbase: " + error.getMessage());
				}
			}
			throw new RuntimeException("Error building kbase!");
		}

		// Create a knowledge base and add the generated package
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		// return a new statefull session
		return kbase.newStatefulKnowledgeSession();
	}

}
