package org.jbpm.test.humantask.reassignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.humantask.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReassignmentProcessTest extends BaseTest {

	@Before
	public void setup() throws Exception {
		super.setUp();
		// load up the knowledge base
		kbase = readKnowledgeBase("humantask/reassignmentSampleProcess.bpmn");
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
		results = localTaskService.getTasksAssignedAsPotentialOwner("mary","en-UK");
		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());

		ksession.dispose();
		localTaskService.dispose();

		System.out.println("sleeping");
		// Double click 'Task A' to check 'reassignment' configuration.
		// Reassignment will happen after 5 seconds!
		Thread.sleep(6000);
		System.out.println("Coming back");

		ksession = newStatefulKnowledgeSession(kbase);
		localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);

		// john has potential task
		results = localTaskService.getTasksAssignedAsPotentialOwner("john","en-UK");
		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());

		// after reassignment
		results = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		TaskSummary marysTask = results.get(0);
		localTaskService.claim(marysTask.getId(), "mary");
		localTaskService.start(marysTask.getId(), "mary");
		localTaskService.complete(marysTask.getId(), "mary", null);

		Thread.sleep(500);

		// process can not finsish
		// Assert.assertEquals(ProcessInstance.STATE_COMPLETED,
		// process.getState());

	}

	private LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
		TaskService taskeService = new TaskService(emf,SystemEventListenerFactory.getSystemEventListener());
		LocalTaskService localTaskService = new LocalTaskService(taskeService);

		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return localTaskService;
	}

	private void test() throws Exception {
		
		setup();

		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);

		// start a new process instance
		Map<String, Object> params = new HashMap<String, Object>();
		ProcessInstance pi = ksession.startProcess("org.jbpm.test.reassignmentSampleProcess", params);
		System.out.println("Process started ... : kid = " + ksession.getId()+ ", pid = " + pi.getId());

		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		System.out.println("The number of tasks for john = " + list.size());
		list = localTaskService.getTasksAssignedAsPotentialOwner("mary","en-UK");
		System.out.println("The number of tasks for mary = " + list.size());

		ksession.dispose();
		localTaskService.dispose();

		System.out.println("sleeping..."); // Double click 'Task A' to check
											// 'reassignment' configuration.
											// Reassignment will happen after 5
											// seconds!
		Thread.sleep(6000);

		ksession = newStatefulKnowledgeSession(kbase);
		localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);

		list = localTaskService.getTasksAssignedAsPotentialOwner("john","en-UK");
		System.out.println("The number of tasks for john = " + list.size());
		list = localTaskService.getTasksAssignedAsPotentialOwner("mary","en-UK");
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
