package org.jbpm.demo.approval.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceUnit;
import javax.transaction.RollbackException;
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
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.PermissionDeniedException;
import org.jbpm.task.service.local.LocalTaskService;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskExecuteService {
	
	private static final Logger logger = Logger.getLogger(TaskExecuteService.class);

	private static KnowledgeBase kbase;

	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
	private EntityManagerFactory emf;

	@Resource
	private UserTransaction ut;
	
	public void approveTask(String actorId, long taskId) throws Exception {
		
		kbase = readKnowledgeBase();

        StatefulKnowledgeSession ksession = createKnowledgeSession();
        TaskService localTaskService = getTaskService(ksession);

        ut.begin();
        
        try {
        	logger.info("approveTask (taskId = " + taskId + ") by " + actorId);
            localTaskService.start(taskId, actorId);
            localTaskService.complete(taskId, actorId, null);

            //Thread.sleep(10000); // To test OptimisticLockException

            ut.commit();
        } catch (RollbackException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof OptimisticLockException) {
                // Concurrent access to the same process instance
                throw new ProcessOperationException("The same process instance has likely been accessed concurrently", e);
            }
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
            // Transaction might be already rolled back by TaskServiceSession
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            // Probably the task has already been started by other users
            throw new ProcessOperationException("The task (id = " + taskId  + ") has likely been started by other users ", e);
        } catch (Exception e) {
            // Transaction might be already rolled back by TaskServiceSession
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            ksession.dispose();
        }

        return;
	}

	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception {

		kbase = readKnowledgeBase();

        StatefulKnowledgeSession ksession = createKnowledgeSession();
        TaskService localTaskService = getTaskService(ksession);
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner(actorId, "en-UK");
        
        logger.info("retrieveTaskList by " + actorId);
        for (TaskSummary task : list) {
        	logger.info(" task.getId() = " + task.getId());
        }

        ksession.dispose();
        
        return list;
	}
	
	private TaskService getTaskService(StatefulKnowledgeSession ksession) {

        org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());

        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);

        return localTaskService;
    }
	
	private StatefulKnowledgeSession createKnowledgeSession() {
		
		Environment env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);

		StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);

		new JPAWorkingMemoryDbLogger(ksession);

		return ksession;
	}

	private KnowledgeBase readKnowledgeBase() throws Exception {

		if (kbase != null) {
			return kbase;
		}

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("approval-demo.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
