package org.jbpm.conductor.humantask;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.I18NText;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.PeopleAssignments;
import org.jbpm.task.Task;
import org.jbpm.task.TaskData;
import org.jbpm.task.User;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.jms.JMSTaskClientConnector;
import org.jbpm.task.service.jms.JMSTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingAddTaskResponseHandler;

public class TaskAdd {

	public static void main(String[] args) throws Exception {
		
		TaskClient client = getTaskClientInstance();
        client.connect();
		
        Task task = newTask();
        ContentData content = new ContentData();
        
        BlockingAddTaskResponseHandler addTaskResponseHandler = new BlockingAddTaskResponseHandler();
        client.addTask(task, content, addTaskResponseHandler );
        long taskId = addTaskResponseHandler.getTaskId();
        System.out.println("Add Task to human task service, taskId: " + taskId);
        
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
	
	private static Task newTask() {

        Task task = new Task();
        
		List<I18NText> names = new ArrayList<I18NText>();
		names.add(new I18NText("en-UK", "Test Task"));
		task.setNames(names);

		List<I18NText> descriptions = new ArrayList<I18NText>();
		descriptions.add(new I18NText("en-UK", "test"));
		task.setDescriptions(descriptions);

		List<I18NText> subjects = new ArrayList<I18NText>();
		subjects.add(new I18NText("en-UK", "test"));
		task.setSubjects(subjects);

		task.setPriority(1);

		TaskData taskData = new TaskData();
		taskData.setWorkItemId(1);
		taskData.setProcessInstanceId(1);
		taskData.setSkipable(false);
//		taskData.setStatus(Status.Reserved);

		PeopleAssignments assignments = new PeopleAssignments();
		List<OrganizationalEntity> potentialOwners = new ArrayList<OrganizationalEntity>();
		potentialOwners.add(new User("kylin"));
		taskData.setCreatedBy((User) potentialOwners.get(0));

		assignments.setPotentialOwners(potentialOwners);
		List<OrganizationalEntity> businessAdministrators = new ArrayList<OrganizationalEntity>();
		businessAdministrators.add(new User("Administrator"));
		assignments.setBusinessAdministrators(businessAdministrators);
		task.setPeopleAssignments(assignments);

		task.setTaskData(taskData);

        return task;
}

}
