package org.jbpm.demo.rewards.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessService {
	
	private static final Logger logger = Logger.getLogger(ProcessService.class);
	
	private static KnowledgeBase kbase;
	
	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
    private EntityManagerFactory emf;
	
	@Resource
    private UserTransaction ut;
	
	public long startProcess(String recipient) throws Exception {
		
		// Use this when you want to ignore user existence issues
        UserGroupCallbackManager.getInstance().setCallback(new DefaultUserGroupCallbackImpl());
        
        kbase = readKnowledgeBase();
        
        StatefulKnowledgeSession ksession = createKnowledgeSession();
        
        long processInstanceId = -1;
        
        ut.begin();
        
        try {
            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("recipient", recipient);
            ProcessInstance processInstance = ksession.startProcess("org.jbpm.demo.rewards-basic", params);

            processInstanceId = processInstance.getId();

            logger.info("Process started ... : processInstanceId = " + processInstanceId);

            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            throw e;
        } finally {
            ksession.dispose();
        }

        return processInstanceId;
	}
	
	private StatefulKnowledgeSession createKnowledgeSession() {
		
		Environment env = KnowledgeBaseFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        
        StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
        
        new JPAWorkingMemoryDbLogger(ksession);
        
        org.jbpm.task.service.TaskService taskService = new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
		
        LocalTaskService localTaskService = new LocalTaskService(taskService);
        
        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        
        return ksession;
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {

		if (kbase != null) {
			return kbase;
		}

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rewards-basic.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
