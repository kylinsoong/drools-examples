package org.jbpm.test.humantask.parameters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.AccessType;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.local.LocalTaskService;


public class SingleTaskNodeAutoMappingTest extends SingleTaskNodeTest {
	
private static final String PROCESS_NAME = "parameters/sample-automap.bpmn";
	
	public SingleTaskNodeAutoMappingTest(String processName) throws Exception {
		super(processName);
	}
	
	private void test() {
		
		LocalTaskService localTaskService = getTaskServiceAndRegisterHumanTaskHandler(ksession);
		
		// Start process
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> initialMyMap = new HashMap<String, Object>();
        initialMyMap.put("var1", "val1");
        initialMyMap.put("var2", "val2");
        params.put("myMap", initialMyMap);
        params.put("myString", "ABC");
        ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello", params);
        System.out.println("Process started ... : kid = " + ksession.getId() + ", pid = " + processInstance.getId());
        
        List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        TaskSummary taskSummary = list.get(0);

        Map<String, Object> map = getContentData(localTaskService, taskSummary);
        
        System.out.println("======== Automapped Map ==========");
        for (Object key : map.keySet()) {
            System.out.println("key = " + key + ", value = " + map.get(key));
        }
        System.out.println("======================================");

        Map<String, Object> myMapForTask = (Map<String, Object>) map.get("myMapForTask");

        System.out.println("John is executing task " + taskSummary.getName());
        localTaskService.start(taskSummary.getId(), "john");

        myMapForTask.put("var1", "new value");
        ContentData contentData = createContentData(myMapForTask, "XYZ");

        localTaskService.complete(taskSummary.getId(), "john", contentData);

        // -----------
        ksession.dispose();
        localTaskService.dispose();
		
	}
	
	protected ContentData createContentData(Map<String, Object> myMapForTask, String myStringForTask) {
        ContentData contentData = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("myMapForTask", myMapForTask);
        results.put("myStringForTask", myStringForTask);
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

	public static void main(String[] args) throws Exception {
		new SingleTaskNodeAutoMappingTest(PROCESS_NAME).test();
	}

}
