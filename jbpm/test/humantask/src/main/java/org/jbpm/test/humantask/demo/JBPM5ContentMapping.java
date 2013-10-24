package org.jbpm.test.humantask.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

public class JBPM5ContentMapping {

	
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {

		setupDataSource();
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample-map.bpmn"), ResourceType.BPMN2);
        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        
        StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
                
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("workflowTask", new WorkflowPOJO());

        ksession.startProcess("com.sample.bpmn.hello", params);
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("Administrator", "en-UK");
		
		TaskSummary taskSummary = list.get(0);
		Map<String, Object> map = getContentData(localTaskService, taskSummary);
		WorkflowPOJO workflowPOJO = (WorkflowPOJO) map.get("inWorkflowTask");
		System.out.println("Before Mapping: " + workflowPOJO);
		workflowPOJO.setId("101010");
		workflowPOJO.setName("JBPM5ContentMapping");		
		localTaskService.start(taskSummary.getId(), "Administrator");
		ContentData contentData = createContentData(workflowPOJO);
		localTaskService.complete(taskSummary.getId(), "Administrator", contentData);

		ksession.dispose();

		System.exit(0);
        
	}
	
	private static ContentData createContentData(WorkflowPOJO workflowPOJO) {
		ContentData contentData = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("outWorkflowTask", workflowPOJO);
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(results);
            out.close();
            contentData = new ContentData();
            contentData.setContent(bos.toByteArray());
            contentData.setAccessType(AccessType.Inline);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentData;
	}

	private static Map<String, Object> getContentData(LocalTaskService localTaskService, TaskSummary taskSummary) {
        Task task = localTaskService.getTask(taskSummary.getId());
        Content content = localTaskService.getContent(task.getTaskData().getDocumentContentId());
        ByteArrayInputStream bis = new ByteArrayInputStream(content.getContent());
        ObjectInputStream in;
        Map<String, Object> result = null;
        try {
            in = new ObjectInputStream(bis);
            Object obj = (Object) in.readObject();
            result = (Map<String, Object>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {

        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
	
	private static void setupDataSource() {
		
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName("jdbc/jbpm-ds");
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "jbpm_user");
        pds.getDriverProperties().put("password", "jbpm_pass");
        pds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/jbpm");
        pds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
        pds.init();
        
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
        Map<String, String> map = new HashMap<String, String>();
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
	}

}
