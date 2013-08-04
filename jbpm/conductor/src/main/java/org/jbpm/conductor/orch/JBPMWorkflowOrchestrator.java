package org.jbpm.conductor.orch;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.base.MapGlobalResolver;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.BPMN2ProcessFactory;
import org.drools.compiler.ProcessBuilderFactory;
import org.drools.io.ResourceFactory;
import org.drools.marshalling.impl.ProcessMarshallerFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.ProcessRuntimeFactory;
import org.jbpm.bpmn2.BPMN2ProcessProviderImpl;
import org.jbpm.marshalling.impl.ProcessMarshallerFactoryServiceImpl;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;
import org.jbpm.process.workitem.wsht.CommandBasedWSHumanTaskHandler;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.mina.MinaTaskServer;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

public class JBPMWorkflowOrchestrator {
	
	private static final String DEFAULT_PROC_ID = "com.sample.bpmn.hello";
	
	private static final String DEFAULT_PROC_NAME = "sample.bpmn";
	
	private KnowledgeBase kbase;
	
	private boolean initialized = false;
	
	private Environment env;
	
	private KnowledgeSessionConfiguration config;
	
	private TaskService taskService;
	
	private Properties properties ;
	
	public JBPMWorkflowOrchestrator() throws Exception {
		this(DEFAULT_PROC_NAME);
	}
	
	public JBPMWorkflowOrchestrator(String processName) throws Exception{
		if(null == processName) {
			processName = DEFAULT_PROC_NAME;
		}
		kbase = readKnowledgeBase(processName) ;
		properties = getProperties();
		setupDataSource();
	}
	
	
	public TaskService getTaskService() {
		return taskService;
	}

	public ProcessEntity createProcess(ProcessEntity processEntity) throws Exception {
		
		createjBPMProcess(processEntity);
		
		return processEntity;
		
	}
	
	private void createjBPMProcess(ProcessEntity processEntity) throws Exception{
		
		String processId = processEntity.getProcessStartId();
		if(null == processId){
			processId = DEFAULT_PROC_ID;
		}
		Map<String, Object> params = processEntity.getProcessParamMap();
		
		StatefulKnowledgeSession ksession = createSession();
		ProcessInstance p = ksession.startProcess(processId, params);
		ksession.fireAllRules();
		
		System.out.println("process created successfully... id: " + p.getId());
		
		processEntity.setProcessInstanceId(p.getId());
		
//		ksession.dispose();
	}
	
	private synchronized StatefulKnowledgeSession createSession() throws Exception {
		
		System.out.println("************ Creating Knowledge Session *************");
		
		init();
		
		if (env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER)).clear();
		}
		if (env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER)).clear();
		}

		StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, config, env);

		System.out.println("************ Created Knowledge Session, Session id = " + ksession.getId() + " ************");
		
		registerHandlers(ksession);
		
		return ksession;
	}
	
	public synchronized StatefulKnowledgeSession loadSession(int id) throws Exception {
		
		System.out.println("************ Loading Knowledge Session " + id + " *************");

		init();

		if (env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER)).clear();
		}
		if (env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER) != null) {
			((EntityManager) env.get(EnvironmentName.APP_SCOPED_ENTITY_MANAGER)).clear();
		}

		StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(id, kbase, config, env);

		System.out.println("************ Loaded Knowledge Session, Session id = " + ksession.getId() + " ************");
		
		registerHandlers(ksession);
		
		return ksession;
	}

	private void registerHandlers(StatefulKnowledgeSession ksession) {

		registerHumanTaskHandlers(ksession);
	}

	private void registerHumanTaskHandlers(StatefulKnowledgeSession ksession) {
		
		Properties properties = getProperties();
		String transport = properties.getProperty("taskservice.transport", "mina");
        if ("mina".equals(transport)) {
			ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new CommandBasedWSHumanTaskHandler(ksession));
        } else {
        	throw new RuntimeException("Unknown task service transport " + transport);
        }
		
//		TaskClient taskClient = new TaskClient(new MinaTaskClientConnector("MinaTaskHandler" + UUID.randomUUID(), new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
//		taskClient.connect();
//		WSHumanTaskHandler humanTaskHandler = new WSHumanTaskHandler(ksession);
//		humanTaskHandler.setClient(taskClient);
//		humanTaskHandler.connect();
//		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
	}



	private synchronized void init() throws Exception {

		if (initialized == false) {
			
			ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
			ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
			ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
			BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());
			
			String dialect = properties.getProperty("persistence.persistenceunit.dialect", "org.hibernate.dialect.H2Dialect");
			Map<String, String> map = new HashMap<String, String>();
			map.put("hibernate.dialect", dialect);		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(properties.getProperty("persistence.persistenceunit.name", "org.jbpm.persistence.jpa"), map);
			
			env = KnowledgeBaseFactory.newEnvironment();
			env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
			env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
			env.set(EnvironmentName.GLOBALS, new MapGlobalResolver());
			
			UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
			System.out.println("Task service registered usergroup callback ...");
			
			Properties props = new Properties();
			props.put("drools.processInstanceManagerFactory", "org.jbpm.persistence.processinstance.JPAProcessInstanceManagerFactory");
			props.put("drools.processSignalManagerFactory", "org.jbpm.persistence.processinstance.JPASignalManagerFactory");
			config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(props);
			
			taskService = startTaskService();
			
			String transport = properties.getProperty("taskservice.transport", "mina");
			
			if ("mina".equals(transport)){
				System.out.println("Starting Mina task server.................");
				MinaTaskServer taskServer = new MinaTaskServer(taskService);
	            Thread thread = new Thread(taskServer);
	            thread.start();
			} else {
	        	throw new RuntimeException("Unknown task service transport " + transport);
	        }
			
			initialized = true;
		}
	}
	
	private TaskService startTaskService() {
		
		String dialect = properties.getProperty("persistence.persistenceunit.dialect", "org.hibernate.dialect.H2Dialect");
		Map<String, String> map = new HashMap<String, String>();
		map.put("hibernate.dialect", dialect);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(properties.getProperty("taskservice.datasource.name", "org.jbpm.task"), map);
		
        System.setProperty("jbpm.usergroup.callback", properties.getProperty("taskservice.usergroupcallback", "org.jbpm.task.service.DefaultUserGroupCallbackImpl"));
		
		TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		
		return taskService;
	}

	private Properties getProperties() {
	    Properties properties = new Properties();
		try {
			properties.load(JBPMWorkflowOrchestrator.class.getResourceAsStream("/jBPM.properties"));
		} catch (Throwable t) {
			// do nothing, use defaults
		}
		return properties;
	}
	
	private PoolingDataSource setupDataSource() {
		Properties properties = getProperties();
        // create data source
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName(properties.getProperty("persistence.datasource.name", "jdbc/jbpm-ds"));
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", properties.getProperty("persistence.datasource.user", "sa"));
        pds.getDriverProperties().put("password", properties.getProperty("persistence.datasource.password", ""));
        pds.getDriverProperties().put("url", properties.getProperty("persistence.datasource.url", "jdbc:h2:tcp://localhost/~/jbpm-db"));
        pds.getDriverProperties().put("driverClassName", properties.getProperty("persistence.datasource.driverClassName", "org.h2.Driver"));
        pds.init();
        return pds;
	}

	private KnowledgeBase readKnowledgeBase(String processName) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(processName), ResourceType.BPMN2);
		System.out.println("Compiling process " + processName);
		
		if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                    System.out.println("Error building kbase: " + error.getMessage());
                }
            }
            throw new RuntimeException("Error building kbase!");
        }
		
		return kbuilder.newKnowledgeBase();
	}


}
