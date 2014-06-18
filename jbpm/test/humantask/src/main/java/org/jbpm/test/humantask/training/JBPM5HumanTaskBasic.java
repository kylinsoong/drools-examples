package org.jbpm.test.humantask.training;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
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
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;


/*
 * 1. From the viewing the process, there are 6 human tasks in process, each reference a actor as following
 *     Approval               ->  Application_Owner
 *     Verify DB Code Review  ->  30392
 *     Code Review            ->  30392
 *     Approval               ->  563019
 *     Change Management      ->  30392
 *     Verify                 ->  Initiator
 */


public class JBPM5HumanTaskBasic {
	
	private static final String JBPM5_BASIC_NAME = "New_Espresso_1677.SERVICES44.DBCodeReview_2059.bpmn2";
	
	private static EntityManagerFactory emf;

	public static void main(String[] args) throws Exception {
		
		setupDataSource();
		
		KnowledgeBase kbase = readKnowledgeBase(JBPM5_BASIC_NAME);
		
		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Application_Owner", "john");
		params.put("Initiator", "mary");
		params.put("dbenvironment", "Production");
		ProcessInstance processInstance = ksession.startProcess("New_Espresso_1677.SERVICES44.DBCodeReview_2059", params);
        System.out.println("Start Process... : " + processInstance.getId());
				
		System.exit(0);
		
	}
	
	private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {

        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        
        Map<String, User> users = new HashMap<String, User>();
        Map<String, Group> groups = new HashMap<String, Group>();
        Reader reader = null;
        
        reader = new InputStreamReader(JBPM5HumanTaskBasic.class.getResourceAsStream("/mvel/SampleUsers.mvel"));
        users = ( Map<String, User> ) TaskService.eval( reader, new HashMap());
        
        reader = new InputStreamReader(JBPM5HumanTaskBasic.class.getResourceAsStream("/mvel/SampleGroups.mvel"));
		groups = ( Map<String, Group> ) TaskService.eval( reader, new HashMap());
		
        taskService.addUsersAndGroups(users, groups);
        
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
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
	
	private static KnowledgeBase readKnowledgeBase(String name) throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
	
	private static void setupDataSource() {

		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		        
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
		        
		Map<String, String> map = new HashMap<String, String>();
		emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
	}

}
