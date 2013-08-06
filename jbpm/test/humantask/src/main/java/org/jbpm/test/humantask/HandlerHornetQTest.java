package org.jbpm.test.humantask;

import org.drools.runtime.process.WorkItemHandler;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.hornetq.HornetQTaskServer;

public class HandlerHornetQTest extends HumanTaskBase {
	
	private TaskServer server;
	
	private TaskClient client;
	
	private WorkItemHandler handler;
	
	public TaskServer getServer() {
		return server;
	}

	public void setServer(TaskServer server) {
		this.server = server;
	}

	public TaskClient getClient() {
		return client;
	}

	public void setClient(TaskClient client) {
		this.client = client;
	}

	public WorkItemHandler getHandler() {
		return handler;
	}

	public void setHandler(WorkItemHandler handler) {
		this.handler = handler;
	}

	public void setUp() throws Exception {
		super.setUp();
		server = new HornetQTaskServer(taskService, 5153);
		System.out.println("Waiting for the HornetQTask Server to come up");
		try {
            startTaskServerThread(server, false);
        } catch (Exception e) {
            startTaskServerThread(server, true);
        }
		
		
	}
	
	protected void tearDown() throws Exception {
		((WSHumanTaskHandler) getHandler()).dispose();
		getClient().disconnect();
		getServer().stop();
		super.tearDown();
	}

}
