package org.jbpm.test.humantask.wsht;

import java.util.List;

import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.CommandBasedWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.humantask.HandlerHornetQTest;

public class CommandBasedWSHumanTaskHandlerTest extends HandlerHornetQTest{

	public CommandBasedWSHumanTaskHandlerTest() throws Exception {
		setUp();
	}
	
	protected void testSmapleProcess() {
		
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new CommandBasedWSHumanTaskHandler(ksession));
		
		ProcessInstance p = ksession.startProcess("com.sample.bpmn.hello");
		ksession.fireAllRules();
		System.out.println("process created successfully... id: " + p.getId());
		
		TaskService localTaskService = new LocalTaskService(taskService);
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        for(int i = 0 ; i < list.size() ; i++) {
        	TaskSummary task = list.get(i);
    		System.out.println("John is executing task " + task.getName()+ ", task id: " + task.getId());
    		localTaskService.start(task.getId(), "john");
    		localTaskService.complete(task.getId(), "john", null);
        }
	}
	
	public static void main(String[] args) throws Exception {
		new CommandBasedWSHumanTaskHandlerTest().testSmapleProcess();
	}

}
