package org.jbpm.demo.rewards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

public class ProcessTest extends JbpmJUnitBaseTestCase{

	public ProcessTest() {
		super(true, true);
	}
	
	@Test
	public void testRewardsProcess() {
		RuntimeManager manager = createRuntimeManager("org/jbpm/demo/rewards.bpmn");
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("recipient", "kylin");
        ksession.startProcess("org.jbpm.demo.rewards", params);
        
		// let john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);

		// let mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = list.get(0);
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);

		manager.disposeRuntimeEngine(engine);
	}

}
