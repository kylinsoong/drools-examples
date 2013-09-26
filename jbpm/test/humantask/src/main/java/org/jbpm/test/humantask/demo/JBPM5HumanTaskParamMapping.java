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
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
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
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;

public class JBPM5HumanTaskParamMapping {
	
	private static final String JBPM5_BASIC_NAME = "demo/humantaskParamMapping.bpmn";
	
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) throws Exception {
		
		// set up database, data source
		setupDataSource();
		
		// load up the knowledge base
        KnowledgeBase kbase = readKnowledgeBase(JBPM5_BASIC_NAME);
        
		// create ksession/localTaskService
		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
		new JPAWorkingMemoryDbLogger(ksession);
		
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		// Start process
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> initialMyMap = new HashMap<String, Object>();
        initialMyMap.put("var1", "val1");
        initialMyMap.put("var2", "val2");
        params.put("myMap", initialMyMap);

        ProcessInstance processInstance = ksession.startProcess("org.jbpm.test.humantask.demo.parammapping", params);
        System.out.println("Start Process... : " + processInstance.getId());
        
		// let john execute Task 1
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary taskSummary = list.get(0);

        Map<String, Object> myMapForTask = getContentData(localTaskService, taskSummary);

        System.out.println("======== myMap which mapped to 'Content' ==========");
        for (Object key : myMapForTask.keySet()) {
            System.out.println("key = " + key + ", value = " + myMapForTask.get(key));
        }
        System.out.println("======================================");


        System.out.println("John is executing task " + taskSummary.getName());
        localTaskService.start(taskSummary.getId(), "john");

        myMapForTask.put("var1", "new value");
        ContentData contentData = createContentData(myMapForTask);

        localTaskService.complete(taskSummary.getId(), "john", contentData);
        
		ksession.dispose();
        
        System.exit(0);
	}
	
	private static void setupDataSource() {
		
		JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
        Map<String, String> map = new HashMap<String, String>();
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
	}

	private static Map<String, Object> getContentData(LocalTaskService localTaskService, TaskSummary taskSummary) {
        Task task = localTaskService.getTask(taskSummary.getId());
        Content content = localTaskService.getContent(task.getTaskData().getDocumentContentId());
        ByteArrayInputStream bis = new ByteArrayInputStream( content.getContent());
        ObjectInputStream in;
        Map<String, Object> result = null;
        try {
            in = new ObjectInputStream(bis);
            Object obj = (Object) in.readObject();
            System.out.println("reading... " + obj);
            result = (Map<String, Object>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	private static ContentData createContentData(Map<String, Object> myMapForTask) {
        ContentData contentData = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("myMapForTask", myMapForTask);
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
	
	private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {

        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
	
	private static KnowledgeBase readKnowledgeBase(String name) throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }
	
	public static StatefulKnowledgeSession newStatefulKnowledgeSession(KnowledgeBase kbase) {
        return loadStatefulKnowledgeSession(kbase, -1);
    }
	
	public static StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());

        if (sessionId == -1) {
            ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        } else {
            ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
        }

        return ksession;
    }

}
