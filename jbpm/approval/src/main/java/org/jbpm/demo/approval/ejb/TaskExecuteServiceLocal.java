package org.jbpm.demo.approval.ejb;

import java.util.List;

import javax.ejb.Local;

import org.kie.api.task.model.TaskSummary;

@Local
public interface TaskExecuteServiceLocal {
	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception;
    public void approveTask(String actorId, long taskId) throws Exception;
}
