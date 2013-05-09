package org.jbpm.quickstarts.usertask;

import java.util.UUID;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.quickstarts.QuickStartBase;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;

public class UserTaskHornetQStart extends QuickStartBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/helloworldUserTask.bpmn");
		
		TaskClient client = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(),new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));  
		client.connect("127.0.0.1", 5446);
		CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
		handler.setClient(client);
		handler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
		
		ProcessInstance processInstance = ksession.startProcess("org.jbpm.quickstarts.helloworldUserTask");
		System.out.println("Process started ... : processInstanceId = " + processInstance.getId());
	}

	public static void main(String[] args) {
		new UserTaskHornetQStart().test();
	}

}
