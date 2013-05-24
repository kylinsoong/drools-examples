package org.jbpm.humantask.ejb;

import java.util.List;

import org.jbpm.task.query.TaskSummary;

public interface HumanTaskProcedureService {
	
	public long startProcess() throws Exception ;
	
	public void executeTask(String actorId, long taskId) throws Exception ;
	
	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception ;
	
	public void printDB() throws Exception ;

}
