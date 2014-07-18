package org.jbpm.demo.params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.runtime.manager.impl.RuntimeEnvironmentBuilder;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.jbpm.test.JBPMHelper;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.RuntimeEnvironment;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.internal.task.api.InternalTaskService;
import org.kie.internal.task.api.UserGroupCallback;
import org.kie.internal.task.api.model.InternalContent;

import bitronix.tm.resource.jdbc.PoolingDataSource;


public class ProcessMainJPA {
	
	private static EntityManagerFactory emf;

	public static void main(String[] args) {
		
		RuntimeManager manager = getRuntimeManager("sample.bpmn");
        RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());
        KieSession ksession = runtime.getKieSession();

        Map<String, Object> params = new HashMap<String, Object>();
        ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello", params);
        System.out.println("A process instance started : pid = " + pi.getId());

        TaskService taskService = runtime.getTaskService();
        System.out.println(taskService.getClass());

        // ------------
        {
            List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
            for (TaskSummary taskSummary : list) {
                long taskId = taskSummary.getId();
                System.out.println("john starts a task : taskId = " + taskId);
                taskService.start(taskId, "john");

                HashMap<String, Object> contentParams = new HashMap<String, Object>();
                contentParams.put("MyKey1", "MyValue1");
                long contentId = ((InternalTaskService)taskService).addContent(taskId, contentParams);
                
                taskService.complete(taskSummary.getId(), "john", null);
            }
        }

        // -----------
        {
            List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
            for (TaskSummary taskSummary : list) {
                long taskId = taskSummary.getId();
                System.out.println("mary starts a task : taskId = " + taskId);
                taskService.start(taskId, "mary");
                
                HashMap<String, Object> contentParams = new HashMap<String, Object>();
                contentParams.put("MyKey2", "MyValue2");
                long contentId = ((InternalTaskService)taskService).addContent(taskId, contentParams);
                
                taskService.complete(taskSummary.getId(), "mary", null);
            }
        }
        
        // -----------
        {
            ArrayList<Status> statusList = new ArrayList<Status>();
            statusList.add(Status.Completed);
            List<TaskSummary> list = taskService.getTasksOwnedByStatus("john", statusList, "en-UK");
            for (TaskSummary taskSummary : list) {
                long taskId = taskSummary.getId();
                Task task = taskService.getTaskById(taskId);

                Content content = (InternalContent) taskService.getContentById(task.getTaskData().getOutputContentId());
                Map<String, Object> contentMap = (Map<String, Object>)ContentMarshallerHelper.unmarshall(content.getContent(), null);
                for (Entry<String, Object> entry : contentMap.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }
        
        {
            ArrayList<Status> statusList = new ArrayList<Status>();
            statusList.add(Status.Completed);
            List<TaskSummary> list = taskService.getTasksOwnedByStatus("mary", statusList, "en-UK");
            for (TaskSummary taskSummary : list) {
                long taskId = taskSummary.getId();
                Task task = taskService.getTaskById(taskId);

                Content content = (InternalContent) taskService.getContentById(task.getTaskData().getOutputContentId());
                Map<String, Object> contentMap = (Map<String, Object>)ContentMarshallerHelper.unmarshall(content.getContent(), null);
                for (Entry<String, Object> entry : contentMap.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }

        // -----------
        ksession.dispose();

        System.exit(0);
    }

    private static RuntimeManager getRuntimeManager(String process) {
        // load up the knowledge base
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();

        // for external database
        // setupDataSource();

        Properties properties = new Properties();
        properties.setProperty("krisv", "");
        properties.setProperty("mary", "");
        properties.setProperty("john", "");
        UserGroupCallback userGroupCallback = new JBossUserGroupCallbackImpl(properties);

        emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa", null);

        RuntimeEnvironment environment = RuntimeEnvironmentBuilder.getDefault().persistence(true)
                .entityManagerFactory(emf).userGroupCallback(userGroupCallback)
                .addAsset(ResourceFactory.newClassPathResource(process), ResourceType.BPMN2).get();
        return RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(environment);

    }

	public static PoolingDataSource setupDataSource() {
		// Please edit here when you want to use your database
		PoolingDataSource pds = new PoolingDataSource();
		pds.setUniqueName("jdbc/jbpm-ds");
		pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		pds.setMaxPoolSize(5);
		pds.setAllowLocalTransactions(true);
		pds.getDriverProperties().put("user", "jbpm6_user");
		pds.getDriverProperties().put("password", "jbpm6_pass");
		pds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/jbpm6");
		pds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
		pds.init();
		return pds;
	}

}
