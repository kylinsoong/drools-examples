package com.kylin.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.drools.SystemEventListenerFactory;
import org.h2.tools.Server;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class JBPMTestHelper {
	
	private static final Logger logger = Logger.getLogger(JBPMTestHelper.class);
	
	public static Server startH2Server() {
		try {
			Server server = Server.createTcpServer(new String[0]);
	        server.start();
	        
	        logger.info("Start h2 server, server status: " + server.getStatus());
	        
	        return server;
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}
	
	public static void startUp() {
		Properties properties = getProperties();
		String driverClassName = properties.getProperty("persistence.datasource.driverClassName", "org.h2.Driver");
		if (driverClassName.startsWith("org.h2")) {
			JBPMTestHelper.startH2Server();
		}
		String persistenceEnabled = properties.getProperty("persistence.enabled", "false");
		String humanTaskEnabled = properties.getProperty("taskservice.enabled", "false");
		if ("true".equals(persistenceEnabled) || "true".equals(humanTaskEnabled)) {
			JBPMTestHelper.setupDataSource();
		}
		if ("true".equals(humanTaskEnabled)) {
			JBPMTestHelper.startTaskService();
		}
	}
	
	public static PoolingDataSource setupDataSource() {
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
	
	public static TaskService startTaskService() {
		Properties properties = getProperties();
		String dialect = properties.getProperty("persistence.persistenceunit.dialect", "org.hibernate.dialect.H2Dialect");
		Map<String, String> map = new HashMap<String, String>();
		map.put("hibernate.dialect", dialect);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(properties.getProperty("taskservice.datasource.name", "org.jbpm.task"), map);
		System.setProperty("jbpm.usergroup.callback", properties.getProperty("taskservice.usergroupcallback", "org.jbpm.task.service.DefaultUserGroupCallbackImpl"));
        TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        String transport = properties.getProperty("taskservice.transport", "mina");
        if ("mina".equals(transport)) {
    		MinaTaskServer taskServer = new MinaTaskServer(taskService);
            Thread thread = new Thread(taskServer);
            thread.start();
        } else {
        	throw new RuntimeException("Unknown task service transport " + transport);
        }
        return taskService;
	}
	
	public static Properties getProperties() {
	    Properties properties = new Properties();
		try {
			properties.load(JBPMTestHelper.class.getResourceAsStream("/jBPM.properties"));
		} catch (Throwable t) {
			// do nothing, use defaults
		}
		return properties;
	}

	public static void main(String[] args) throws SQLException {
		PoolingDataSource ds = setupDataSource();
		Connection conn = ds.getConnection();
		System.out.println(conn);
	}

}
