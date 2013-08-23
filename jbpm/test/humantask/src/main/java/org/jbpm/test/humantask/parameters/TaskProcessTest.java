package org.jbpm.test.humantask.parameters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.humantask.HumanTaskBase;

public class TaskProcessTest extends HumanTaskBase{
	
	private static final String PROCESS_NAME = "parameters/hellotask.bpmn";
	
	public TaskProcessTest(String process) throws Exception {
		super(process);
		setUp();
	}
	
	private void test() {
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);

		// start a new process instance
        ProcessInstance pi = ksession.startProcess("org.jbpm.test.hellotask");
        System.out.println("Process started ... : kid = " + ksession.getId() + ", pid = " + pi.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        System.out.println("The number of tasks for mary = " + list.size());
        
        TaskSummary taskSummary = list.get(0);
        Task task = localTaskService.getTask(taskSummary.getId());
        
        System.out.println("mary start task");
        localTaskService.start(task.getId(), "mary");
        
        System.out.println("mary complete task");
        Map<String, String> formParams = new HashMap<String, String>();
        formParams.put("Explanation-", "very good");
        formParams.put("Outcome-", "Approved");
        localTaskService.complete(task.getId(), "mary", createContentData("very good", "Approved"));
        
        ksession.dispose();
        localTaskService.dispose();
	}
	
	protected ContentData createContentData(String explanation, String reason) {
		ContentData contentData = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Explanation-", explanation);
        results.put("Outcome-", reason);
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(results);
            out.close();
            contentData = new ContentData();
            contentData.setContent(bos.toByteArray());
            contentData.setAccessType(AccessType.Inline);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentData;
	}
	
	private LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }

	public static void main(String[] args) throws Exception {
		new TaskProcessTest(PROCESS_NAME).test();;
	}

}
