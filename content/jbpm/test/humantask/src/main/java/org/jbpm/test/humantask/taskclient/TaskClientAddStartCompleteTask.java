package org.jbpm.test.humantask.taskclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.AccessType;
import org.jbpm.task.I18NText;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.PeopleAssignments;
import org.jbpm.task.Task;
import org.jbpm.task.TaskData;
import org.jbpm.task.User;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingAddTaskResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;

public class TaskClientAddStartCompleteTask {

	public static void main(String[] args) throws Exception {
		
		TaskClient client = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        boolean success = client.connect("127.0.0.1", 5153);
        System.out.println("Connecting to human task service, success: " + success);
        
        Task task = createTask();
        ContentData content = createContentData();
        
        BlockingAddTaskResponseHandler addTaskResponseHandler = new BlockingAddTaskResponseHandler();
        client.addTask(task, content, addTaskResponseHandler );
        long taskId = addTaskResponseHandler.getTaskId();
        System.out.println("Add Task to human task service, taskId: " + taskId);
        
        BlockingTaskSummaryResponseHandler taskSummaryResponseHandler = new BlockingTaskSummaryResponseHandler();
		client.getTasksAssignedAsPotentialOwner("kylin", "en-UK", taskSummaryResponseHandler);
		List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();
		System.out.println("Getting tasks for human task service via user kylin, tasks size: " + tasks.size());
		
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		client.start(taskId, "kylin", responseHandler);
		responseHandler.waitTillDone(1000);
		System.out.println("kylin starting Task " + taskId);

		responseHandler = new BlockingTaskOperationResponseHandler();
		client.complete( taskId, "kylin", content, responseHandler );
		responseHandler.waitTillDone(1000);
		System.out.println("kylin completing Task " + taskId);
              
        client.disconnect();
	}

	private static ContentData createContentData() throws IOException {
		
		ContentData content = null;
		
		Map<String, Object> contentObject = new HashMap<String, Object>();
		contentObject.put("group", "PM");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(contentObject);
            out.close();
            content = new ContentData();
            content.setContent(bos.toByteArray());
            content.setAccessType(AccessType.Inline);
        } catch (IOException e) {
           throw e;
        }
		
		return content;
	}

	private static Task createTask() {

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
