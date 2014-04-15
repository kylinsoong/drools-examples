package org.jbpm.test.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * This is a sample file to launch a process.
 */
public class ProcessMainJPA {

    private static EntityManagerFactory emf;

    public static final void main(String[] args) throws Exception {
        
        setup();
        
        // load up the knowledge base
        KnowledgeBase kbase = readKnowledgeBase();
        
        // create ksession/localTaskService
        StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
        LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
       

        ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
                
        Object obj = ((WorkflowProcessInstance) ksession.getProcessInstance(processInstance.getId())).getVariable("marketingStrategy");
        System.out.println(obj);

        // let john execute Task 1
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner( "john", "en-UK");
        TaskSummary taskSummary = list.get(0);


        System.out.println("John is executing task " + taskSummary.getName());
        localTaskService.start(taskSummary.getId(), "john");

        localTaskService.complete(taskSummary.getId(), "john", null);
        
        obj = ((WorkflowProcessInstance) ksession.getProcessInstance(processInstance.getId()));
        System.out.println(obj);

        // -----------
        ksession.dispose();
        
        System.exit(0);
    }

    private static char[] WorkflowProcessInstance(
			ProcessInstance processInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sample2.bpmn"), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }

    private static void setup() {
        // for H2
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        
        // for your DB
//        setupDataSource();
        
        UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
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