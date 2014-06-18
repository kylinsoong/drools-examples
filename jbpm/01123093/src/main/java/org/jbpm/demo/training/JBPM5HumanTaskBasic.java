package org.jbpm.demo.training;

import java.io.InputStreamReader;
import java.io.Reader;
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
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.UserInfo;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultEscalatedDeadlineHandler;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;


/*
 * From the viewing the process, there are 6 human tasks in process, each reference a actor as following
 *     Approval               ->  Application_Owner
 *     Verify DB Code Review  ->  30392
 *     Code Review            ->  30392
 *     Approval               ->  563019
 *     Change Management      ->  30392
 *     Verify                 ->  Initiator
 */
public class JBPM5HumanTaskBasic {

	private static final String JBPM5_BASIC_NAME = "New_Espresso_1677.SERVICES44.DBCodeReview_2059.bpmn2";
	
	private static final String Handler_Class = "org.jbpm.task.service.DefaultEscalatedDeadlineHandler";
	
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) throws Exception {

		setupDataSource();
		
		KnowledgeBase kbase = readKnowledgeBase(JBPM5_BASIC_NAME);
		
		StatefulKnowledgeSession ksession = newStatefulKnowledgeSession(kbase);
//		new JPAWorkingMemoryDbLogger(ksession);
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		/*
		 *  1. Start Process
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Application_Owner", "john");
		params.put("Initiator", "mary");
		params.put("dbenvironment", "Production");
		ProcessInstance processInstance = ksession.startProcess("New_Espresso_1677.SERVICES44.DBCodeReview_2059", params);
        System.out.println("Start Process... : " + processInstance.getId());
        
        /*
         * 2. john execute Approval
         */
        System.out.println("john execute Approval");
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary taskSummary = list.get(0);
        localTaskService.start(taskSummary.getId(), "john");
        localTaskService.complete(taskSummary.getId(), "john", null);
        
        /*
         * 3. 30392 execute Verify DB Code Review
         */
        System.out.println("30392 execute Verify DB Code Review");
        list = localTaskService.getTasksAssignedAsPotentialOwner("30392", "en-UK");
        taskSummary = list.get(0);
        localTaskService.start(taskSummary.getId(), "30392");
        localTaskService.complete(taskSummary.getId(), "30392", null);
        
        /*
         * 4. 30392 execute Code Review
         */
        System.out.println("30392 execute Code Review");
        list = localTaskService.getTasksAssignedAsPotentialOwner("30392", "en-UK");
        taskSummary = list.get(0);
        localTaskService.start(taskSummary.getId(), "30392");
        localTaskService.complete(taskSummary.getId(), "30392", null);
        
        /*
         * 4. 563019 execute Approval
         */
        System.out.println("563019 execute Approval");
        list = localTaskService.getTasksAssignedAsPotentialOwner("563019", "en-UK");
        taskSummary = list.get(0);
        localTaskService.start(taskSummary.getId(), "563019");
        localTaskService.complete(taskSummary.getId(), "563019", null);
        
        /*
         * 5. 30392 execute Change Management
         */
        System.out.println("30392 execute Change Management");
        list = localTaskService.getTasksAssignedAsPotentialOwner("30392", "en-UK");
        if(list.size() > 0) {
        	taskSummary = list.get(0);
            localTaskService.start(taskSummary.getId(), "30392");
            localTaskService.complete(taskSummary.getId(), "30392", null);
        } else {
        	System.out.println("User Task 'Change Management' be ignored");
        }
        
        
        /*
         * 6. mary execute Verify
         */
        System.out.println("mary execute Verify");
        list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        taskSummary = list.get(0);
        localTaskService.start(taskSummary.getId(), "mary");
        localTaskService.complete(taskSummary.getId(), "mary", null);
        
        System.out.println("Process finished");
        
		ksession.dispose();

		System.exit(0);
	}
	
	protected static <T> T getInstance(String className) {
    	
		if (className == null || "".equalsIgnoreCase(className)) {
			return null;
		}
        
		Object instance;
		try {
			instance = Class.forName(className).newInstance();
		
			return (T) instance;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error while creating instance of configurable class, class name: " + className, e);
		}
    }
	
	private static LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {

//        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        
        TaskService taskService = null;
    	try {
        	EscalatedDeadlineHandler handler = getInstance(Handler_Class);
        	if (handler instanceof DefaultEscalatedDeadlineHandler) {
        		UserInfo userInfo = null;
        		try {
		        	userInfo = getInstance("org.jbpm.task.UserInfo");
        		} catch (IllegalArgumentException e) {
        			throw e;
				}
	        	
	        	((DefaultEscalatedDeadlineHandler)handler).setUserInfo(userInfo);
        	}
        	
        	taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener(), handler);
        } catch (Exception e) {
        	taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		}
        
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
	
	private static void setupDataSource() {

		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		        
		UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
		        
		Map<String, String> map = new HashMap<String, String>();
		emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
	}
	
	private static KnowledgeBase readKnowledgeBase(String name) throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
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

}
