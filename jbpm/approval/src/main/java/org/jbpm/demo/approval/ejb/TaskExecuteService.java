package org.jbpm.demo.approval.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceUnit;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.jbpm.services.task.exception.PermissionDeniedException;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskExecuteService implements TaskExecuteServiceLocal{
	
	private static final Logger logger = Logger.getLogger(TaskExecuteService.class);

	@Resource
	private UserTransaction ut;
	
	@Inject
    TaskService taskService;
	
	public void approveTask(String actorId, long taskId) throws Exception {
		
		ut.begin();

        try {
            System.out.println("approveTask (taskId = " + taskId + ") by " + actorId);
            taskService.start(taskId, actorId);
            taskService.complete(taskId, actorId, null);

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
            throw new ProcessOperationException("The task (id = " + taskId + ") has likely been started by other users ", e);
        } catch (Exception e) {
            e.printStackTrace();
            // Transaction might be already rolled back by TaskServiceSession
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            throw new RuntimeException(e);
        } 
	}

	public List<TaskSummary> retrieveTaskList(String actorId) throws Exception {
		
		 ut.begin();
	        
	        List<TaskSummary> list;
	        
	        try {
	            list = taskService.getTasksAssignedAsPotentialOwner(actorId, "en-UK");
	            ut.commit();
	        } catch (RollbackException e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }

	        logger.info("retrieveTaskList by " + actorId);
	        for (TaskSummary task : list) {
	        	logger.info(" task.getId() = " + task.getId());
	        }

	        return list;
	}

}
