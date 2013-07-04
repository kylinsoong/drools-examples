package org.jbpm.test.humantask.reassignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bitronix.tm.TransactionManagerServices;

public class ReassignmentProcessTest {

	private EntityManagerFactory emf;
	private KnowledgeBase kbase;

	@Before
	public void setup() throws Exception {

		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		UserGroupCallbackManager.getInstance().setCallback( new DefaultUserGroupCallbackImpl());

		Map<String, String> map = new HashMap<String, String>();
		emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
		// load up the knowledge base
		kbase = readKnowledgeBase();
	}
	
	@Test
	public void testReassignmentTaskTest() throws Exception {
		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
        // Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.reassignmentSampleProcess");
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        // john has potential task
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        
        // no reassignment
        results = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(0, results.size());
        
        ksession.dispose();
        localTaskService.dispose();
        
        System.out.println("sleeping..."); 
        // Double click 'Task A' to check 'reassignment' configuration. Reassignment will happen after 5 seconds! 
        Thread.sleep(6000);
        System.out.println("Coming back");
        
        ksession = newStatefulKnowledgeSession(kbase);
        localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        
		// john has potential task
        results = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(0, results.size());
        
        // after reassignment
        results = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        
        TaskSummary marysTask = results.get(0);
        localTaskService.start(marysTask.getId(), "mary");
        localTaskService.complete(marysTask.getId(), "mary", null);
        
        Thread.sleep(2000);
        
        // process can not finsish
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        

	}
	
	private StatefulKnowledgeSession newStatefulKnowledgeSession(KnowledgeBase kbase) {
        return loadStatefulKnowledgeSession(kbase, -1);
    }
	
	private LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
        TaskService taskeService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskeService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
	
	private StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;

        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());

        if (sessionId == -1) {
            ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        } else {
            ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
        }

        return ksession;
    }
	
	private KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("humantask/reassignmentSampleProcess.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }
	
	private void test() throws Exception {
		setup();

		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        
		// start a new process instance
        Map<String,Object> params = new HashMap<String, Object>();
        ProcessInstance pi = ksession.startProcess("org.jbpm.test.reassignmentSampleProcess", params);
        System.out.println("Process started ... : kid = " + ksession.getId() + ", pid = " + pi.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        System.out.println("The number of tasks for mary = " + list.size());
        
        ksession.dispose();
        localTaskService.dispose();
        
        System.out.println("sleeping..."); // Double click 'Task A' to check 'reassignment' configuration. Reassignment will happen after 5 seconds! 
        Thread.sleep(6000);
        
        ksession = newStatefulKnowledgeSession(kbase);
        localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        
        list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        System.out.println("The number of tasks for mary = " + list.size());
        
        TaskSummary taskSummary = list.get(0);
        Task task = localTaskService.getTask(taskSummary.getId());

        localTaskService.start(task.getId(), "mary");
        
        localTaskService.complete(task.getId(), "mary", null);
        
        ksession.dispose();
        localTaskService.dispose();
        
        System.out.println("DONE");
        
        System.exit(0);
		
	}

	public static final void main(String[] args) throws Exception {
		new ReassignmentProcessTest().test();
		
	}

}
