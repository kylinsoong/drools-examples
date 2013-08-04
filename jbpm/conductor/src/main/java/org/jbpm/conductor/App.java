package org.jbpm.conductor;

import java.util.List;

import org.jbpm.conductor.orchestrator.JBPMWorkflowOrchestrator;
import org.jbpm.conductor.orchestrator.ProcessEntity;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.local.LocalTaskService;


public class App {
	
	public static void main(String[] args) throws Exception {
		
		JBPMWorkflowOrchestrator orch = new JBPMWorkflowOrchestrator();
		
		System.out.println("Start 10 process");
		
		for(int i = 0 ; i < 10 ; i ++){
			ProcessEntity processEntity = orch.createProcess(new ProcessEntity());
		}
		
		TaskService taskService = new LocalTaskService(orch.getTaskService());
		
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        for(int i = 0 ; i < list.size() ; i++) {
        	TaskSummary task = list.get(i);
    		System.out.println("John is executing task " + task.getName()+ ", task id: " + task.getId());
    		taskService.start(task.getId(), "john");
    		taskService.complete(task.getId(), "john", null);
        }
        		
	}


}
