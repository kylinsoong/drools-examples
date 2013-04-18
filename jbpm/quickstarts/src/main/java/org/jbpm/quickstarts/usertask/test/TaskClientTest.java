package org.jbpm.quickstarts.usertask.test;

import javax.persistence.EntityManagerFactory;

import org.jbpm.quickstarts.usertask.util.JPAUtil;
import org.jbpm.test.JBPMHelper;

public class TaskClientTest {

	public static void main(String[] args) {

		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		
		EntityManagerFactory emf = JPAUtil.getEntityManagerFactory("org.jbpm.persistence.jpa");
		
		System.out.println(emf);
	}

}
