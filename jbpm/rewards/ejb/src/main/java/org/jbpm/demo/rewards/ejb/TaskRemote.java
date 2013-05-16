package org.jbpm.demo.rewards.ejb;

import java.util.List;

import org.jbpm.task.query.TaskSummary;

public interface TaskRemote {

	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception;

    public void approveTask(String actorId, long taskId) throws Exception;
}
