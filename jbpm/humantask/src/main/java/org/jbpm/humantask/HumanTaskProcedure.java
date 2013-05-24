package org.jbpm.humantask;

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
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.local.LocalTaskService;

import bitronix.tm.TransactionManagerServices;

public class HumanTaskProcedure {
	
	public static void main(String[] args) {
		new HumanTaskProcedure("test.bpmn", "org.jbpm.humantask.test").start();
	}

	private String name;
	
	private String id;

	public HumanTaskProcedure(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	private EntityManagerFactory emf;
	
	private LocalTaskService localTaskService;
	
	public void start() {

		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(name);
		
		localTaskService = getTaskService(ksession);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "kylin soong");
		params.put("number", "18611907049");
		ksession.startProcess(id, params);
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary task = list.get(0);
                        
        localTaskService.start(task.getId(), "john");
        localTaskService.complete(task.getId(), "john", null);
        
        ksession.dispose();
	}

	private LocalTaskService getTaskService(StatefulKnowledgeSession ksession) {
		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskService);
        
        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return localTaskService;
	}

	public StatefulKnowledgeSession newStatefulKnowledgeSession(String name) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
        return loadStatefulKnowledgeSession(kbuilder.newKnowledgeBase(), -1);
    }

    public StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;
        Map<String, String> map = new HashMap<String, String>();
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
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
