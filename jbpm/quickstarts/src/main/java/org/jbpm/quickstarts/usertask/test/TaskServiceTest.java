package org.jbpm.quickstarts.usertask.test;

import javax.persistence.EntityManagerFactory;

import org.drools.SystemEventListenerFactory;
import org.jbpm.quickstarts.usertask.util.JPAUtil;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.test.JBPMHelper;


/**
 * How to build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to run?
 *   mvn clean install dependency:copy-dependencies
 * 
 * @author kylin
 *
 */
public class TaskServiceTest {

	public static void main(String[] args) {
		
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		
		EntityManagerFactory emf = JPAUtil.getEntityManagerFactory("org.jbpm.task");
		
		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		
		MinaTaskServer taskServer = new MinaTaskServer(taskService);
		new Thread(taskServer).start();
		
	}

}
