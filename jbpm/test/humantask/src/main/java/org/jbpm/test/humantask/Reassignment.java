package org.jbpm.test.humantask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.PeopleAssignments;
import org.jbpm.task.Task;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingGetTaskResponseHandler;
import org.jbpm.test.JBPMHelper;

public class Reassignment {
	
	public static void main(String[] args) throws Exception {

		startUp();
		KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = JBPMHelper.newStatefulKnowledgeSession(kbase);
        
        TaskClient taskClient = null;
        
        CommandBasedHornetQWSHumanTaskHandler commandBasedHornetQWSHumanTaskHandler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
        commandBasedHornetQWSHumanTaskHandler.setConnection("127.0.0.1", 5153);
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", commandBasedHornetQWSHumanTaskHandler);
		SystemEventListenerFactory.setSystemEventListener(new SystemEventListener());
		
		HornetQTaskClientHandler hqtch = new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener());
		HornetQTaskClientConnector hornetQTaskClientConnector = new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(),hqtch);
		taskClient = new TaskClient(hornetQTaskClientConnector);
		taskClient.connect("127.0.0.1", 5153);
		Thread.sleep(1000);
		long taskId=12;
		List<String> list = new ArrayList<String>();
		list.add("ABHISHEK");
		//ksession.abortProcessInstance(11);
		Reassignment reassignment = new Reassignment();
		reassignment.reassignUsers(taskId, list, taskClient);
		
	}
	
	private void reassignUsers(long taskId, List<String> newAssignees, TaskClient tc) {
		  Task task = getTask(taskId, tc);
		  System.out.println("Status of the task : " +task.getTaskData().getStatus());		  
		  System.out.println("Assigned User Id : " + task.getPeopleAssignments().getPotentialOwners());
		  List<OrganizationalEntity> newEntities = new ArrayList<OrganizationalEntity>();
		  for (String newAssignee : newAssignees) {
		  	newEntities.add(new User(newAssignee));
		     }
		   PeopleAssignments assignments = new PeopleAssignments();
		   assignments.setPotentialOwners(newEntities);
		   task.setPeopleAssignments(assignments);		   
		   System.out.println(task.getPeopleAssignments().getPotentialOwners());
	}
	
	private Task getTask(long taskId, TaskClient tc){
		BlockingGetTaskResponseHandler getTaskResponseHandler = new BlockingGetTaskResponseHandler();
		tc.getTask(taskId, getTaskResponseHandler);
		Task t = getTaskResponseHandler.getTask();
		return t;
	}
	
	private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }
	
	private static void startUp() {
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
    }
	
	private static class SystemEventListener implements org.drools.SystemEventListener {
		public void debug(String arg0) {
		}
		public void debug(String arg0, Object arg1) {
		}
		public void exception(Throwable arg0) {
		}
		public void exception(String arg0, Throwable arg1) {
		}
		public void info(String arg0) {
		}
		public void info(String arg0, Object arg1) {
		}
		public void warning(String arg0) {
		}
		public void warning(String arg0, Object arg1) {
		}
	}

}
