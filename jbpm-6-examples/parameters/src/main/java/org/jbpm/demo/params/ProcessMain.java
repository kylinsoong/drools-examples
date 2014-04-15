package org.jbpm.demo.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.TaskSummary;

public class ProcessMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args)  {
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		// start a new process instance
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "krisv");
		params.put("age", 35);
		params.put("content", "Yearly performance evaluation");
		ksession.startProcess("org.jbpm.demo.parameters", params);
		
		// start User Task
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
		TaskSummary task = tasks.get(0);
		taskService.start(task.getId(), "krisv");
		
		// show input parameters
		Task userTask = taskService.getTaskById(task.getId());
		TaskData taskData = userTask.getTaskData();
		Content content = taskService.getContentById(taskData.getDocumentContentId());
		Map<String, Object> map =  (Map<String, Object>) ContentMarshallerHelper.unmarshall(content.getContent(), null);
		System.out.println("\nShow Variables, Input parameters in User Task");
		System.out.println("input_name -> " + map.get("input_name"));
		System.out.println("input_age -> " + map.get("input_age"));
		System.out.println("input_content -> " + map.get("input_content"));
				
		// complete User Task
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("output_name", "Kylin");
		results.put("output_age", 29);
		results.put("output_content", "performance evaluation finish");
		taskService.complete(task.getId(), "krisv", results);
		
		manager.disposeRuntimeEngine(engine);
		manager.close();
		
		System.exit(0);
	}
	
	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(emf).userGroupCallback(new JBossUserGroupCallbackImpl("classpath:/usergroups.properties")).knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "org.jbpm.demo:parameters:1.0");
	}

}
