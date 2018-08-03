package com.kylin.brms.jbpm.humantask;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.TaskService;

public class StartHumanTaskServiceTest {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.task");

		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());

	}

}
