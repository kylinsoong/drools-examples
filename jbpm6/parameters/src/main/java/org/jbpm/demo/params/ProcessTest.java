package org.jbpm.demo.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.TaskSummary;

public class ProcessTest extends JbpmJUnitBaseTestCase{

	public ProcessTest() {
		super(true, true);
	}
	
	@Test
	public void testEvaluationProcess() {
		RuntimeManager manager = createRuntimeManager("org/jbpm/demo/parameters.bpmn");
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		// start a new process instance
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "krisv");
		params.put("age", 35);
		params.put("content", "Yearly performance evaluation");
		ksession.startProcess("org.jbpm.demo.parameters", params);
        System.out.println("Process started ...");
        
		// complete Self Evaluation
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
		assertEquals(1, tasks.size());
		TaskSummary task = tasks.get(0);
		System.out.println("'krisv' completing task " + task.getName() + ": " + task.getDescription());
		taskService.start(task.getId(), "krisv");
		
		Task userTask = taskService.getTaskById(task.getId());
		TaskData taskData = userTask.getTaskData();
		Content content = taskService.getContentById(taskData.getDocumentContentId());
		Map<String, Object> map =  (Map<String, Object>) ContentMarshallerHelper.unmarshall(content.getContent(), null);
		assertEquals(map.get("input_name"), "krisv");
		assertEquals(map.get("input_age"), 35);
		assertEquals(map.get("input_content"), "Yearly performance evaluation");
		
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("output_name", "Kylin");
		results.put("output_age", 29);
		results.put("output_content", "performance evaluation finish");
		taskService.complete(task.getId(), "krisv", results);

		System.out.println("Process instance completed");
		
		manager.disposeRuntimeEngine(engine);
		manager.close();
	}

}
