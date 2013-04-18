package org.jbpm.quickstarts.usertask.util;

import javax.persistence.EntityManagerFactory;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.test.JBPMHelper;

public class QuickStartHelper {

	public static void startTaskService() {
		
		JBPMHelper.startH2Server();
		
		JBPMHelper.setupDataSource();
		
		EntityManagerFactory emf = JPAUtil.getEntityManagerFactory("org.jbpm.task");
		
		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		
		MinaTaskServer taskServer = new MinaTaskServer(taskService);
		
		new Thread(taskServer).start();
	}
}
