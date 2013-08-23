package org.jbpm.test.humantask.parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.test.humantask.HumanTaskBase;

public class SingleTaskNodeTest extends HumanTaskBase {
	
	private static final String PROCESS_NAME = "parameters/sample.bpmn";
	
	public SingleTaskNodeTest(String processName) throws Exception {
		super(processName);
		setUp();
	}

	private void test() {
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		// Start process
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> initialMyMap = new HashMap<String, Object>();
        initialMyMap.put("var1", "val1");
        initialMyMap.put("var2", "val2");
        params.put("myMap", initialMyMap);
        ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello", params);
        System.out.println("Process started ... : kid = " + ksession.getId() + ", pid = " + processInstance.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary taskSummary = list.get(0);
        
        Map<String, Object> myMapForTask = getContentData(localTaskService, taskSummary);
        
        System.out.println("======== myMap which mapped to 'Content' ==========");
        for (Object key : myMapForTask.keySet()) {
            System.out.println("key = " + key + ", value = " + myMapForTask.get(key));
        }
        System.out.println("======================================");
        
        System.out.println("John is executing task " + taskSummary.getName());
        localTaskService.start(taskSummary.getId(), "john");

        myMapForTask.put("var1", "new value");
        ContentData contentData = createContentData(myMapForTask);

        localTaskService.complete(taskSummary.getId(), "john", contentData);
        
        // -----------
        ksession.dispose();
        localTaskService.dispose();
	}
	
	protected ContentData createContentData(Map<String, Object> myMapForTask) {
        ContentData contentData = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("myMapForTask", myMapForTask);
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
	
	protected Map<String, Object> getContentData(LocalTaskService localTaskService, TaskSummary taskSummary) {
        Task task = localTaskService.getTask(taskSummary.getId());
        Content content = localTaskService.getContent(task.getTaskData().getDocumentContentId());
        ByteArrayInputStream bis = new ByteArrayInputStream(content.getContent());
        ObjectInputStream in;
        Map<String, Object> result = null;
        try {
            in = new ObjectInputStream(bis);
            Object obj = (Object) in.readObject();
            System.out.println("reading... " + obj);
            result = (Map<String, Object>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
	}
	
	protected LocalTaskService getTaskServiceAndRegisterHumanTaskHandler(StatefulKnowledgeSession ksession) {
        LocalTaskService localTaskService = new LocalTaskService(taskService);

        SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        return localTaskService;
    }
	
	public static void main(String[] args) throws Exception {
		new SingleTaskNodeTest(PROCESS_NAME).test();;
	}

}
