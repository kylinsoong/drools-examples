package org.jbpm.quickstarts.usertask.test;

import javax.persistence.EntityManagerFactory;

import com.customized.tools.persist.DataSourceUtil;
import com.customized.tools.persist.H2Helper;
import com.customized.tools.persist.JPAUtil;

public class TaskServiceTest {

	public static void main(String[] args) {
		
		H2Helper.startH2Server();
		
		DataSourceUtil.setupDataSource("jdbc/jbpm-ds");
		
		EntityManagerFactory emf = JPAUtil.getEntityManagerFactory("org.jbpm.task");
		
		emf.createEntityManager();
		
		System.out.println("org.jbpm.task.service.TaskService");
	}

}
