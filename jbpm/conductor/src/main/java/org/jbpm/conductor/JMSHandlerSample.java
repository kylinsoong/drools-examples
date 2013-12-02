package org.jbpm.conductor;

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
import org.jbpm.process.workitem.wsht.CommandBasedWSHumanTaskHandler;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.jms.JMSTaskClientConnector;
import org.jbpm.task.service.jms.JMSTaskClientHandler;



public class JMSHandlerSample {

	public static void main(String[] args) throws Exception {
		
		KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        
        TaskClient clientForHumanTaskHandler = getTaskClientInstance();
        clientForHumanTaskHandler.connect();
        
        CommandBasedWSHumanTaskHandler handler = new CommandBasedWSHumanTaskHandler();
        handler.configureClient(clientForHumanTaskHandler);
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
        
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello");
        System.out.println("Process started ... : pid = " + pi.getId());
        
//        AsyncTaskServiceWrapper taskService = new AsyncTaskServiceWrapper(client);
//        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
//        for (TaskSummary task : list){
//        	System.out.println("task.getId() = " + task.getId());
//			if (task.getStatus() == Status.Reserved) {
//				System.out.println("John is starting and completing task, id = " + task.getId() + ", name = " + task.getName());
//				taskService.start(task.getId(), "john");
//				taskService.complete(task.getId(), "john", null);
//			}
//        }
        
//        Thread.sleep(3000);
        
//        taskService.disconnect();
        
//        client.disconnect();
		
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
