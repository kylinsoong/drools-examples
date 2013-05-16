package com.kylin.ejb.remote.client;

import java.util.List;

import javax.naming.NamingException;

import org.jbpm.demo.rewards.ejb.ProcessRemote;
import org.jbpm.demo.rewards.ejb.TaskRemote;
import org.jbpm.task.query.TaskSummary;

public class RewardsBasicTest extends ClientBase {
	
	private ProcessRemote processRemote;
	private TaskRemote taskRemote;
	
	public RewardsBasicTest() throws NamingException {
		processRemote = (ProcessRemote) getContext().lookup(PROCESS_JNDI);
		taskRemote = (TaskRemote) getContext().lookup(RASK_JNDI);
	}

	public void test() throws Exception {
				
		prompt("krisv start process");
		processRemote.startProcess("krisv");
		
		
		prompt("john execute task");
		List<TaskSummary> list = taskRemote.retrieveTaskList("john");
		for(TaskSummary task : list) {
			taskRemote.approveTask("john", task.getId());
		}
		
		prompt("mary execute task");
		list = taskRemote.retrieveTaskList("mary");
		for(TaskSummary task : list) {
			taskRemote.approveTask("mary", task.getId());
		}
		
		prompt("exit");

	}

	public static void main(String[] args) throws Exception {
		new RewardsBasicTest().test();
	}

}
