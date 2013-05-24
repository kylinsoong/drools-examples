package org.jbpm.humantask.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
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
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;

@Stateless
public class HumanTaskProcedureBean implements HumanTaskProcedureService {
	
private static final Logger logger = Logger.getLogger(HumanTaskProcedureBean.class);
	
	private KnowledgeBase kbase;
	
	private LocalTaskService localTaskService;
	
	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
    private EntityManagerFactory emf;
	
	@Resource
    private UserTransaction ut;
	

	public long startProcess() throws Exception {
		
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
		
		kbase = readKnowledgeBase();
		
		StatefulKnowledgeSession ksession = createKnowledgeSession();
		
		 long processInstanceId = -1;
	        
	        ut.begin();
	        
	        try {
	            // start a new process instance
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("name", "kylin");
	            params.put("number", "11111");
	            ProcessInstance processInstance = ksession.startProcess("org.jbpm.humantask.test", params);

	            processInstanceId = processInstance.getId();

	            logger.info("Process started ... : processInstanceId = " + processInstanceId);

	            ut.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            if (ut.getStatus() == Status.STATUS_ACTIVE) {
	                ut.rollback();
	            }
	            throw e;
	        } 

	        return processInstanceId;

	}

	public void executeTask(String actorId, long taskId) throws Exception {


        ut.begin();
        
        try {
        	logger.info("approveTask (taskId = " + taskId + ") by " + actorId);
            localTaskService.start(taskId, actorId);
            localTaskService.complete(taskId, actorId, null);

            ut.commit();
            
        }  catch (Exception e) {
            // Transaction might be already rolled back by TaskServiceSession
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            throw new RuntimeException(e);
        }
	}

	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception {
		
		List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner(actorId, "en-UK");
        
        logger.info("retrieveTaskList by " + actorId);
        for (TaskSummary task : list) {
        	logger.info(" task.getId() = " + task.getId());
        }
        
		return list;
	}

	public void printDB() throws Exception {

	}
	
	private StatefulKnowledgeSession createKnowledgeSession() {
		
		Environment env = KnowledgeBaseFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        
        StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        
        new JPAWorkingMemoryDbLogger(ksession);
        
        org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		
		localTaskService = new LocalTaskService(taskService);
        
        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        
        return ksession;
	}
	
	private KnowledgeBase readKnowledgeBase() throws Exception {

		if (kbase != null) {
			return kbase;
		}

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("test.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
