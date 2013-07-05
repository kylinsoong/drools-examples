package org.jbpm.test.humantask;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.service.DefaultUserGroupCallbackImpl;
import org.jbpm.task.service.UserGroupCallbackManager;
import org.jbpm.test.JBPMHelper;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

public class BaseTest {

	protected EntityManagerFactory emf;
	protected KnowledgeBase kbase;
	
	public void init() throws Exception {

		// for H2
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		
		// for your DB. Please change dialect in persistence.xml, too.
//		setupDataSource();
		
		UserGroupCallbackManager.getInstance().setCallback( new DefaultUserGroupCallbackImpl());

		Map<String, String> map = new HashMap<String, String>();
		emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", map);
		
	}
	
	protected PoolingDataSource setupDataSource() {
        PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName("jdbc/jbpm-ds");
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "test_user");
        pds.getDriverProperties().put("password", "test_pass");
        pds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/test");
        pds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
        pds.init();
        return pds;
    }
	
	protected KnowledgeBase readKnowledgeBase(String name) throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }
	
	protected StatefulKnowledgeSession newStatefulKnowledgeSession(KnowledgeBase kbase) {
		return loadStatefulKnowledgeSession(kbase, -1);
	}
	
	protected StatefulKnowledgeSession loadStatefulKnowledgeSession(KnowledgeBase kbase, int sessionId) {
		
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
