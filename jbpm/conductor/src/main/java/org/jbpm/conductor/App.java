package org.jbpm.conductor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

import org.jbpm.conductor.orchestrator.JBPMHumanTaskService;
import org.jbpm.conductor.orchestrator.JBPMWorkflowOrchestrator;
import org.jbpm.conductor.orchestrator.ProcessEntity;
import org.jbpm.conductor.util.DataSourceHelper;
import org.jbpm.conductor.util.H2Helper;
import org.jbpm.conductor.util.PropertyLoader;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.local.LocalTaskService;


public class App {
	
	public static void main(String[] args) throws Exception {
				
		Properties parameters = PropertyLoader.getProperties();
		
		DataSourceHelper.newInstance().setupDataSource(parameters);
		
		JBPMHumanTaskService humanTaskService = new JBPMHumanTaskService(parameters);
		humanTaskService.init();
		
		pause("Press Enter to continue...");
		
		JBPMWorkflowOrchestrator orch = new JBPMWorkflowOrchestrator(parameters);
		
		System.out.println("Start 10 process");
		
		for(int i = 0 ; i < 10 ; i ++){
			ProcessEntity processEntity = orch.createProcess(new ProcessEntity());
		}
		
		pause("Press Enter to continue...");
		
		TaskService taskService = new LocalTaskService(getTaskService(humanTaskService.getServer()));
		
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        System.out.println("The number of tasks for john = " + list.size());
        for(int i = 0 ; i < list.size() ; i++) {
        	TaskSummary task = list.get(i);
    		System.out.println("John is executing task " + task.getName()+ ", task id: " + task.getId());
    		taskService.start(task.getId(), "john");
    		taskService.complete(task.getId(), "john", null);
        }
        		
	}

	private static void pause(String string) {
		System.out.println("\n\r"  + string);
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
	
	private static org.jbpm.task.service.TaskService getTaskService(TaskServer taskServer) {
		try {
			Field handlerField = taskServer.getClass().getSuperclass().getDeclaredField("handler");
			handlerField.setAccessible(true);
			Object firstHandler = handlerField.get(taskServer);
				
			handlerField = firstHandler.getClass().getDeclaredField("handler");
			handlerField.setAccessible(true);
			Object secondHandler = handlerField.get(firstHandler);
				
			Field serviceField = secondHandler.getClass().getDeclaredField("service");
			serviceField.setAccessible(true);
			Object service = serviceField.get(secondHandler);
				
			return (org.jbpm.task.service.TaskService) service;
		} catch (Exception e) {
			return null;
		}

	}


}
