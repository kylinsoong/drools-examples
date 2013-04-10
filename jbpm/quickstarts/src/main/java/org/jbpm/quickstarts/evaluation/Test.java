package org.jbpm.quickstarts.evaluation;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskClientConnector;
import org.jbpm.task.service.mina.BaseMinaHandler;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;

public class Test {
	
	private TaskClient client;

	private void test() {
		BaseMinaHandler handler = new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener());
		TaskClientConnector connector = new MinaTaskClientConnector("org.drools.process.workitem.wsht.WSHumanTaskHandler", handler);
		client = new TaskClient(connector);
		
		
	}
	
	
	public static void main(String[] args) {
		new Test().test();
		System.out.println("Test Done");
	}

}
