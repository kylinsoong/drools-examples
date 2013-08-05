package org.jbpm.test.humantask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.UserGroupCallbackManager;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

public class BaseTest {
	
	private static final String MVEL_USERS = "/mvel/SampleUsers.mvel";
	private static final String MVEL_GROUPS = "/mvel/SampleGroups.mvel";

	protected EntityManagerFactory emf;

    protected Map<String, User> users;
    protected Map<String, Group> groups;

    protected TaskService taskService;
    protected TaskServiceSession taskSession;

    protected boolean useJTA = false;
    protected static final String DATASOURCE_PROPERTIES = "/datasource.properties";
    private PoolingDataSource pds;
    
    protected StatefulKnowledgeSession ksession;
    protected KnowledgeBase kbase;
    
    public static final long TASK_SERVER_START_WAIT_TIME = 10000;
    
    protected EntityManagerFactory createEntityManagerFactory() { 
    	pds = setupDataSource(loadDataSourceProperties());
        return Persistence.createEntityManagerFactory("org.jbpm.task");
    }
    
	protected PoolingDataSource setupDataSource(Properties properties) {
		
		if (null != pds) {
			return pds;
		}
		
		System.out.println("Set up PoolingDataSource via " + properties.getProperty("persistence.datasource.url"));
		
        // create data source
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName(properties.getProperty("persistence.datasource.name", "jdbc/jbpm-ds"));
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(10);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", properties.getProperty("persistence.datasource.user", "sa"));
        pds.getDriverProperties().put("password", properties.getProperty("persistence.datasource.password", ""));
        pds.getDriverProperties().put("url", properties.getProperty("persistence.datasource.url", "jdbc:h2:tcp://localhost/~/jbpm-db"));
        pds.getDriverProperties().put("driverClassName", properties.getProperty("persistence.datasource.driverClassName", "org.h2.Driver"));
        pds.init();
        return pds;
	}
	
	protected Properties loadDataSourceProperties() { 

        InputStream propsInputStream = getClass().getResourceAsStream(DATASOURCE_PROPERTIES);
        Properties dsProps = new Properties();
        if (propsInputStream != null) {
            try {
                dsProps.load(propsInputStream);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } 
        return dsProps;
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
    
    protected void setUp() throws Exception {

        // Use persistence.xml configuration
        emf = createEntityManagerFactory();

        taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        MockUserInfo userInfo = new MockUserInfo();
        taskService.setUserinfo(userInfo);
        
        Map vars = new HashMap();
        Reader reader = new InputStreamReader(getConfigFileStream(MVEL_USERS));
        users = ( Map<String, User> ) TaskService.eval( reader, vars ); 
        
        vars = new HashMap();
        reader = new InputStreamReader( getConfigFileStream(MVEL_GROUPS));
        groups = ( Map<String, Group> ) TaskService.eval( reader, vars ); 
        
        taskService.addUsersAndGroups(users, groups);
        
        UserGroupCallbackManager.getInstance().setCallback(null);
                
        taskSession = taskService.createSession();
        
        ksession = newStatefulKnowledgeSession(readKnowledgeBase("sample.bpmn"));
    }
    
    protected InputStream getConfigFileStream(String location) throws IOException {
        
        if (location == null) {
            return null;
        }
        
        URL configLocation = BaseTest.class.getResource(location);

		if (configLocation == null) {
			throw new IllegalArgumentException("File was not found at given location " + location);
		}

        return configLocation.openStream();
    }
    
    protected void startTaskServerThread(TaskServer server, boolean failOnLimit) throws InterruptedException {
        Thread thread = new Thread(server);
        thread.start();
        
        long counter = 0;
        while (!server.isRunning()) {
            System.out.print(".");
            Thread.sleep(50);
            counter += 50;
            if (counter > TASK_SERVER_START_WAIT_TIME) {
                if (failOnLimit) {
                    new RuntimeException("Unable to start task server in defined time");
                } else {
                    break;
                }
            }
        }
    }
    
    protected void tearDown() throws Exception {
        if( taskSession != null ) { 
            taskSession.dispose();
        }
        emf.close();
        if( useJTA ) { 
            pds.close();
        }
    }
}
