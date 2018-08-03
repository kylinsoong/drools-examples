package com.kylin.test;

import org.jbpm.task.service.TaskService;

public class TaskServiceTest {

	public static void main(String[] args) {
		
		JBPMTestHelper.setupDataSource();

		TaskService taskService = JBPMTestHelper.startTaskService();
		
		System.out.println("Done");
	}

}
