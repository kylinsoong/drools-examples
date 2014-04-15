package org.jbpm.demo;

import org.jbpm.test.JBPMHelper;
import org.kie.api.runtime.manager.RuntimeManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JTAEntityManagerFactorySpringTest {

	public static final void main(String[] args) throws Throwable {
		
		JBPMHelper.startH2Server();

		JBPMHelper.setupDataSource();
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/jta-emf-spring.xml");
		
		RuntimeManager manager = (RuntimeManager) context.getBean("runtimeManager");
		
		System.out.println(manager);
	}
}
