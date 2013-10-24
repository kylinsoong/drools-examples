package org.jbpm.test.humantask.persistence;

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
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

public class JBPM5PersistenceMysql {

	
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) {

		setupDataSource();
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"), ResourceType.BPMN2);
        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        
        StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        System.out.println(ksession.getId());
        
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
        
        ProcessInstance pInstance = ksession.startProcess("com.sample.bpmn.hello");
		System.out.println("Start Process...: " + pInstance.getId());
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		System.out.println("list size: " + list.size());
		
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
