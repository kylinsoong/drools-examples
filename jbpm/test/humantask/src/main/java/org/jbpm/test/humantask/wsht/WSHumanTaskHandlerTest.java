package org.jbpm.test.humantask.wsht;

import java.util.List;

import org.drools.SystemEventListenerFactory;
import org.drools.process.instance.impl.WorkItemImpl;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.AsyncWSHumanTaskHandler;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.jbpm.test.humantask.HandlerHornetQTest;
import org.jbpm.test.humantask.TestStatefulKnowledgeSession;


public class WSHumanTaskHandlerTest extends HandlerHornetQTest {
	
	public WSHumanTaskHandlerTest() throws Exception {
		
		setUp();
	}
	
	private TaskClient initTaskClient(String name){
		HornetQTaskClientHandler hornetQTaskClientHandler = new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener());
		HornetQTaskClientConnector hornetQTaskClientConnector = new HornetQTaskClientConnector(name, hornetQTaskClientHandler);
		TaskClient taskClient = new TaskClient(hornetQTaskClientConnector);
		return taskClient;
	}
	
	protected void testWSHumanTaskHandler() {
			
		setClient(initTaskClient("client 1"));
		
		getClient().connect("127.0.0.1", 5153);
		
		WSHumanTaskHandler handler = new WSHumanTaskHandler(ksession);
		handler.setClient(getClient());
		
		setHandler(handler);
	}
	
	protected void testAsyncMultipleHandlers() throws Exception {
		
		setClient(initTaskClient("client 1"));
		
		MyCallbackWorkItemManager manager = new MyCallbackWorkItemManager();
		
		TestStatefulKnowledgeSession ksession = new TestStatefulKnowledgeSession();
		
		AsyncWSHumanTaskHandler handler = new AsyncWSHumanTaskHandler(getClient(), ksession);
        handler.setConnection("127.0.0.1", 5153);
        handler.setManager(manager);
        
        ksession.setWorkItemManager(manager);
        WorkItemImpl workItem = new WorkItemImpl();
        workItem.setName("Human Task");
        workItem.setParameter("TaskName", "TaskName");
        workItem.setParameter("Comment", "Comment");
        workItem.setParameter("Priority", "10");
        workItem.setParameter("ActorId", "Darth Vader");
        workItem.setProcessInstanceId(10);
        handler.executeWorkItem(workItem, manager);
        
        Thread.sleep(1000);
        
        BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
        getClient().getTasksAssignedAsPotentialOwner("Darth Vader", "en-UK", responseHandler);
        List<TaskSummary> tasks = responseHandler.getResults();
        System.out.println("Tasks size: " + tasks.size());
        TaskSummary task = tasks.get(0);
        System.out.println("Task Name: " + task.getName());
        System.out.println("Task Priority: " + task.getPriority());
        System.out.println("Task Comment: " + task.getDescription());
        System.out.println("Task Status: " + task.getStatus());
        System.out.println("Task Owner: " + task.getActualOwner().getId());
        System.out.println("Task ProcessInstanceId: " + task.getProcessInstanceId());
        System.out.println("Task Id: " + task.getId());

        BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
        getClient().start(task.getId(), "Darth Vader", operationResponseHandler);
        operationResponseHandler.waitTillDone(5000);
        System.out.println("Task Status(handler 1 start): " + task.getStatus());
        
        handler.dispose();
        
        setClient(initTaskClient("client 2"));
        
        AsyncWSHumanTaskHandler handler2 = new AsyncWSHumanTaskHandler(getClient(), ksession);
        handler2.setConnection("127.0.0.1", 5153);
        handler2.setManager(manager);
        handler2.connect();
        
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        getClient().complete(task.getId(), "Darth Vader", null, operationResponseHandler);
        operationResponseHandler.waitTillDone(5000);
        System.out.println("Task Status(handler 2 complete): " + task.getStatus());
        Thread.sleep(2000);
        handler2.dispose();
        getClient().disconnect();
	}
	
	protected void testSampleProcessWithHandler() throws Exception {
		
		setClient(initTaskClient("client 1"));
		print("Create Task Client: " + getClient());
		
		AsyncWSHumanTaskHandler handler = new AsyncWSHumanTaskHandler(getClient(), ksession);
		handler.setConnection("127.0.0.1", 5153);
//		handler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
		
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
		ksession.fireAllRules();
		print("process created successfully... id: " + processInstance.getId());
		
		BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
        getClient().getTasksAssignedAsPotentialOwner("john", "en-UK", responseHandler);
        List<TaskSummary> tasks = responseHandler.getResults();
        TaskSummary task = tasks.get(0);
        print("Task Status: " + task.getStatus());
		
		BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
		getClient().start(task.getId(), "john", operationResponseHandler);
        operationResponseHandler.waitTillDone(5000);
        print("Task Status: " + task.getStatus());
        
        operationResponseHandler = new BlockingTaskOperationResponseHandler();
        getClient().complete(task.getId(), "john", null, operationResponseHandler);
        operationResponseHandler.waitTillDone(5000);
        print("Task Status: " + task.getStatus());
		
	}

	public static void main(String[] args) throws Exception {
		
//		new BasicTaskServiceTest().testWSHumanTaskHandler();
		
//		new HumanTaskHandlerTest().testAsyncMultipleHandlers();
		
		new WSHumanTaskHandlerTest().testSampleProcessWithHandler();
	}
}
