package org.jbpm.demo.evaluation;

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
	public static void main(String[] args) {
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();

		// start a new process instance
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("employee", "krisv");
		params.put("reason", "Yearly performance evaluation");
		ksession.startProcess("org.jbpm.demo.evaluation", params);
		System.out.println("Process started ...");
		
		// complete Self Evaluation
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
		TaskSummary task = tasks.get(0);
		System.out.println("'krisv' completing task " + task.getName() + ": " + task.getDescription());
		taskService.start(task.getId(), "krisv");
		Task userTask = taskService.getTaskById(task.getId());
		TaskData taskData = userTask.getTaskData();
		Content content = taskService.getContentById(taskData.getDocumentContentId());
		Map<String, Object> map =  (Map<String, Object>) ContentMarshallerHelper.unmarshall(content.getContent(), null);
		System.out.println("Self Evaluation Input:");
		System.out.println("  reason -> " + map.get("reason"));
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("performance", "exceeding");
		taskService.complete(task.getId(), "krisv", results);
		
		// john from HR
		tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		task = tasks.get(0);
		System.out.println("'john' completing task " + task.getName() + ": " + task.getDescription());
		taskService.start(task.getId(), "john");
		userTask = taskService.getTaskById(task.getId());
		taskData = userTask.getTaskData();
		content = taskService.getContentById(taskData.getDocumentContentId());
		map =  (Map<String, Object>) ContentMarshallerHelper.unmarshall(content.getContent(), null);
		System.out.println("HR Manager Evaluation Input:");
		System.out.println("  reason -> " + map.get("reason"));
		System.out.println("  performance -> " + map.get("performance"));
		results = new HashMap<String, Object>();
		results.put("performance", "acceptable");
		taskService.complete(task.getId(), "john", results);
		
		// mary from PM
		tasks = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = tasks.get(0);
		System.out.println("'mary' completing task " + task.getName() + ": " + task.getDescription());
		taskService.start(task.getId(), "mary");
		userTask = taskService.getTaskById(task.getId());
		taskData = userTask.getTaskData();
		content = taskService.getContentById(taskData.getDocumentContentId());
		map =  (Map<String, Object>) ContentMarshallerHelper.unmarshall(content.getContent(), null);
		System.out.println("PM Manager Evaluation Input:");
		System.out.println("  reason -> " + map.get("reason"));
		System.out.println("  performance -> " + map.get("performance"));
		results = new HashMap<String, Object>();
		results.put("performance", "outstanding");
		taskService.complete(task.getId(), "mary", results);
		
		System.out.println("Process instance completed");
		
		manager.disposeRuntimeEngine(engine);
		manager.close();
		
		System.exit(0);
	}

	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(emf).userGroupCallback(new JBossUserGroupCallbackImpl("classpath:/usergroups.properties")).knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "org.jbpm.demo:evaluation:1.0");
	}

}