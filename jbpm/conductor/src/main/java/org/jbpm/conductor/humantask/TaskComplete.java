package org.jbpm.conductor.humantask;

import java.util.Properties;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.jms.JMSTaskClientConnector;
import org.jbpm.task.service.jms.JMSTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;

public class TaskComplete {

	public static void main(String[] args) throws Exception {
		
		TaskClient client = getTaskClientInstance();
        client.connect();
    
        ContentData content = new ContentData();
        
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        client.complete(1, "kylin", content, responseHandler);
        responseHandler.waitTillDone(1000);
        System.out.println("kylin completing Task ");
        
        client.disconnect();
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

}
