package org.jbpm.test.humantask.test.workitem;

import java.util.List;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.humantask.test.BaseTest;
import org.junit.Before;

/**
 * 
 * SyncWSHumanTaskHandler
 * 
 * @author kylin
 *
 */
public class WorkItemBasicTest extends BaseTest {
	
	@Before
	public void setup() throws Exception {
		super.setUp();
		// load up the knowledge base
		kbase = readKnowledgeBase("humantask/workitemSampleProcess.bpmn");
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


	public static void main(String[] args) throws Exception {
		new WorkItemBasicTest().test();
	}


	private void test() throws Exception {
		
		setup();

		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);

		// start a new process instance
		ProcessInstance pi = ksession.startProcess("org.jbpm.test.workitemSampleProcess");
		System.out.println("Process started ... : kid = " + ksession.getId()+ ", pid = " + pi.getId());
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		System.out.println("The number of tasks for john = " + list.size());
		
		TaskSummary taskSummary = list.get(0);
		Task task = localTaskService.getTask(taskSummary.getId());

		localTaskService.start(task.getId(), "john");

		localTaskService.complete(task.getId(), "john", null);
		
		ksession.dispose();
		localTaskService.dispose();

		System.out.println("DONE " + pi.getState());

		System.exit(0);
		
	}

}
