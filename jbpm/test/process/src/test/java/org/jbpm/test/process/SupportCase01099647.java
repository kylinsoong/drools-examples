package org.jbpm.test.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.process.workitem.wsht.CommandBasedWSHumanTaskHandler;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * This is a sample file to launch a process.
 */
public class SupportCase01099647 {

    private static EntityManagerFactory emf;

    public static final void main(String[] args) throws Exception {
        
        setup();
        
        // load up the knowledge base
        KnowledgeBase kbase = readKnowledgeBase();
        
        
        Environment env = KnowledgeBaseFactory.newEnvironment();
    	env.set( EnvironmentName.ENTITY_MANAGER_FACTORY, emf );
    	env.set( EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        		
    	Properties sessionconfigproperties = new Properties();
    	sessionconfigproperties.put("drools.processInstanceManagerFactory", "org.jbpm.persistence.processinstance.JPAProcessInstanceManagerFactory");
    	sessionconfigproperties.put("drools.processSignalManagerFactory", "org.jbpm.persistence.processinstance.JPASignalManagerFactory");
    	KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(sessionconfigproperties);
    	
    	StatefulKnowledgeSession session = JPAKnowledgeService.newStatefulKnowledgeSession( kbase, config, env );
    	
    	CommandBasedWSHumanTaskHandler humanTaskHandler = new CommandBasedWSHumanTaskHandler(session);
    	
    	TaskClient client = getTaskClient();
    	humanTaskHandler.configureClient(client);
    	
    	session.getWorkItemManager().registerWorkItemHandler("Human Task",humanTaskHandler);
    	humanTaskHandler.connect();
    	
    	WorkflowProcessInstance  instance = (WorkflowProcessInstance )session.startProcess("FinancingLeaseProcess.service", null);
        
        System.out.println(session);
        
       
        System.exit(0);
    }
    
    private static TaskClient getTaskClient(){
    	TaskClient client = new TaskClient(new HornetQTaskClientConnector("HornetQConnector" + UUID.randomUUID(), new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
		client.connect("localhost", 5153);
		return client;
    }

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		
		UrlResource resource = (UrlResource) ResourceFactory.newUrlResource("http://localhost:8080/jboss-brms/rest/packages/com.icsfs.test/binary");
		resource.setBasicAuthentication("enabled");
        resource.setUsername("admin");
        resource.setPassword("admin");
		
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(resource, ResourceType.PKG);
        return kbuilder.newKnowledgeBase();
    }

    private static void setup() {
        // for H2
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        
        // for your DB
//        setupDataSource();
        
//        UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
        Map<String, String> map = new HashMap<String, String>();
        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
    }
    
    public static PoolingDataSource setupDataSource() {
        // Please edit here when you want to use your database
        PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName("jdbc/jbpm-ds");
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "mysql");
        pds.getDriverProperties().put("password", "mysql");
        pds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/testbrms531");
        pds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
        pds.init();
        return pds;
    }

    public static StatefulKnowledgeSession newStatefulKnowledgeSession(KnowledgeBase kbase) {
        return loadStatefulKnowledgeSession(kbase, -1);
    }

    public static StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
        StatefulKnowledgeSession ksession;
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());

        if (sessionId == -1) {
            ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        } else {
            ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
        }

        return ksession;
    }

    private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
  
}