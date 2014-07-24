package org.jbpm.demo.jms;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.services.client.api.RemoteJmsRuntimeEngineFactory;

public class JBPMJMSClient {
	
	public static void main(String[] args) {
		new JBPMJMSClient().startProcessAndTaskViaJmsRemoteJavaAPI("localhost", "org.kie.example:project1:1.0.0-SNAPSHOT", "kylin", "password1!");
	}

	public void startProcessAndTaskViaJmsRemoteJavaAPI(String serverHostName, String deploymentId, String user, String password) {
		
		InitialContext remoteInitialContext = getRemoteInitialContext(serverHostName, user, password);
		int maxTimeoutSecs = 5;
		RemoteJmsRuntimeEngineFactory remoteJmsFactory = new RemoteJmsRuntimeEngineFactory(deploymentId, remoteInitialContext, user, password, maxTimeoutSecs);
		
		RuntimeEngine engine = remoteJmsFactory.newRuntimeEngine();
		KieSession ksession = engine.getKieSession();
		
		ProcessInstance processInstance = ksession.startProcess("project1.TestProcess");
		long procId = processInstance.getId();
		
		System.out.println("Process started, id = " + procId);
		
		TaskService taskService = engine.getTaskService();
		List<Long> tasks = taskService.getTasksByProcessInstanceId(procId);
		taskService.start(tasks.get(0), user);
		
	}

	private InitialContext getRemoteInitialContext(String jbossServerHostName, String user, String password) {
		
		Properties initialProps = new Properties();
		initialProps.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		initialProps.setProperty(InitialContext.PROVIDER_URL, "remote://"+ jbossServerHostName + ":4447");
		initialProps.setProperty(InitialContext.SECURITY_PRINCIPAL, user);
		initialProps.setProperty(InitialContext.SECURITY_CREDENTIALS, password);
		
		for (Object keyObj : initialProps.keySet()) {
			String key = (String) keyObj;
			System.setProperty(key, (String) initialProps.get(key));
		}
		
		try {
			return new InitialContext(initialProps);
		} catch (NamingException e) {
			throw new RuntimeException("Unable to create " + InitialContext.class.getSimpleName(), e);
		}
		
	}
}
