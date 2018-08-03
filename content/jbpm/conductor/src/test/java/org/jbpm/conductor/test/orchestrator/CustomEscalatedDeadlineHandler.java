package org.jbpm.conductor.test.orchestrator;

import org.jbpm.task.Content;
import org.jbpm.task.Deadline;
import org.jbpm.task.Task;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.task.service.TaskService;

public class CustomEscalatedDeadlineHandler implements EscalatedDeadlineHandler {

	public void executeEscalatedDeadline(Task task, Deadline deadline, Content content, TaskService service) {
		
	}

}
