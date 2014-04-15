package org.jbpm.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.drools.persistence.PersistenceContextManager;
import org.drools.persistence.TransactionManager;
import org.jbpm.process.audit.AuditLogService;
import org.jbpm.process.audit.JPAAuditLogService;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.spring.persistence.KieSpringJpaManager;
import org.kie.spring.persistence.KieSpringTransactionManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * This is a sample file to launch a process.
 */
public class SpringIntegrationDemo {

	public static final void main(String[] args) throws Throwable {
		
		JBPMHelper.startH2Server();
		
		JBPMHelper.setupDataSource();
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/kmodule-spring.xml");
		
		KieServices kservices = KieServices.Factory.get();
		KieContainer kContainer = kservices.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");
	
		EntityManagerFactory emf = (EntityManagerFactory) context.getBean("jbpmEMF");
	
		AbstractPlatformTransactionManager aptm = (AbstractPlatformTransactionManager) context.getBean( "jbpmTxManager" );	

		
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get()
        	.newDefaultBuilder()
//        	.addAsset(kservices.getResources().newClassPathResource("sample.bpmn"), ResourceType.BPMN2)
				 /**
				  * don't need to set it on environment, there is special method to accept EMF
				  */
		    .addEnvironmentEntry(EnvironmentName.TRANSACTION_MANAGER, aptm)
		    /**
		     * This will be created automatically when transaction manager (line above) is spring object
		     */
		    .entityManagerFactory(emf).knowledgeBase(kbase) ;
        RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "com.sample:spring:1.0");
        
        RuntimeEngine engine = manager.getRuntimeEngine(null);
        KieSession ksession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recipient", "kylin");
        ksession.startProcess("org.jbpm.demo.rewards", params);
        
		// let john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);

		// let mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = list.get(0);
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);

		manager.disposeRuntimeEngine(engine);

		System.exit(0);
		
	}

}