package org.jbpm.test.humantask.demo;

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
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;

public class JBPM5HumanTaskBasic {

	private static final String JBPM5_BASIC_NAME = "sample.bpmn";

	private static EntityManagerFactory emf;
	
	public static void main(String[] args) throws Exception {

		setupDataSource();
		
		KnowledgeBase kbase = readKnowledgeBase(JBPM5_BASIC_NAME);
		
		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
		new JPAWorkingMemoryDbLogger(ksession);
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		Map<String, Object> params = new HashMap<String, Object>();
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello", params);
        System.out.println("Start Process... : " + processInstance.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary taskSummary = list.get(0);
        
        localTaskService.start(taskSummary.getId(), "john");
        
        localTaskService.complete(taskSummary.getId(), "john", null);
        
		ksession.dispose();

		System.exit(0);
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

		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		        
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
		        
		Map<String, String> map = new HashMap<String, String>();
		emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
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
