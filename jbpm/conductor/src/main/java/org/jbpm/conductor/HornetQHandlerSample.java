package org.jbpm.conductor;

import java.util.List;
import java.util.UUID;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.AsyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;

public class HornetQHandlerSample {

	public static void main(String[] args) throws Exception {
		
		// load up the knowledge base
		KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        
        TaskClient clientForHumanTaskHandler = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        clientForHumanTaskHandler.connect("127.0.0.1", 5153);
        
        CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
        handler.setClient(clientForHumanTaskHandler);
        handler.connect();
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
        
        //start a new process instance
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello");
        System.out.println("Process started ... : pid = " + pi.getId());
        
		// Wait to make sure that Task is committed
        Thread.sleep(3000);
        
		// Instantiate another TaskClient to simulate that the human task client is located in a different JVM from the one which started the process.
        TaskClient clientForHumanTaskClient = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        clientForHumanTaskClient.connect("127.0.0.1", 5153);
        AsyncTaskServiceWrapper taskService = new AsyncTaskServiceWrapper(clientForHumanTaskClient);
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        for (TaskSummary task : list) {
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId());
            if (task.getStatus() == Status.Reserved) {
                System.out.println("John is starting and completing task " + task.getName());
                taskService.start(task.getId(), "john");
                taskService.complete(task.getId(), "john", null);
            }
            if (task.getStatus() == Status.InProgress) {
                System.out.println("John is completing task " + task.getName());
                taskService.complete(task.getId(), "john", null);
            }
        }
        
        Thread.sleep(3000);
        
		taskService.disconnect();
        
        clientForHumanTaskHandler.disconnect();
        
        System.out.println("taskService is disconnected");
        
        System.exit(0);
	}
	
	private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }

}
