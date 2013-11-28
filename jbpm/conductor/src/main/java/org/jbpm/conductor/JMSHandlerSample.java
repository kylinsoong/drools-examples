package org.jbpm.conductor;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.AsyncWSHumanTaskHandler;
import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.AsyncTaskServiceWrapper;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.jms.JMSTaskClientConnector;
import org.jbpm.task.service.jms.JMSTaskClientHandler;



public class JMSHandlerSample {

	public static void main(String[] args) throws Exception {
		
		KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        
        TaskClient clientForHumanTaskHandler = getTaskClientInstance();
        boolean success = clientForHumanTaskHandler.connect("localhost", 1099);
        System.out.println("Connecting to human task service, success: " + success);
        
        CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
        handler.setClient(clientForHumanTaskHandler);
        handler.connect();
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
        
      //start a new process instance
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello");
        System.out.println("Process started ... : pid = " + pi.getId());
        
		// Wait to make sure that Task is committed
        Thread.sleep(3000);
        
        TaskClient clientForHumanTaskClient = getTaskClientInstance();
        clientForHumanTaskClient.connect("localhost", 1099);
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
        
//        AsyncWSHumanTaskHandler handler = new AsyncWSHumanTaskHandler(client, ksession);
//        handler.setConnection("localhost", 1099);
//        
//        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
//        
//        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello");
//        System.out.println("Process started ... : pid = " + pi.getId());
		
	}
	
	private static TaskClient getTaskClientInstance() {		
    	
    	Properties clientProperties = new Properties();	
    	clientProperties.setProperty("JMSTaskClient.connectionFactory", "ConnectionFactory");		
    	clientProperties.setProperty("JMSTaskClient.transactedQueue", "true");		
    	clientProperties.setProperty("JMSTaskClient.acknowledgeMode", "AUTO_ACKNOWLEDGE");		
    	clientProperties.setProperty("JMSTaskClient.queueName", "jbpm.ht.tasksQueue");		
    	clientProperties.setProperty("JMSTaskClient.responseQueueName",	"jbpm.ht.responseQueue");		
    	clientProperties.setProperty("jbpm.console.task.service.host","localhost");
    	clientProperties.setProperty("jbpm.console.task.service.port","1099");
    				
    	Context ctx = null;		
    	try {		
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            props.put(Context.PROVIDER_URL, "localhost:1099");
            ctx = new InitialContext(props);    	
		} catch (NamingException e) {
			e.printStackTrace();
		}
    	JMSTaskClientConnector connector = new JMSTaskClientConnector("TaskClient" + UUID.randomUUID(), new JMSTaskClientHandler(SystemEventListenerFactory.getSystemEventListener()),clientProperties, ctx);
    	TaskClient client = new TaskClient(connector);		
    	return client;	
    	
    }
	
	private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }

}
