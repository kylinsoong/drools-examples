package org.jbpm.test.restclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessDefinitionRefWrapper;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;
import org.jboss.bpm.console.client.model.TaskRef;
import org.jboss.bpm.console.client.model.TaskRefWrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestClientTask extends RestClientSimple {

	protected static String PROCESS_ID = "org.jbpm.test.hellotask";

	public RestClientTask(String username, String password) {
		super(username, password);
	}

	private void test() throws Exception {

		// get process definitions
		ProcessDefinitionRefWrapper processDefinitionWrapper = getProcessDefinitions();

		// pick up 'org.jbpm.test.hello'
		ProcessDefinitionRef definitionRef = null;
		for (ProcessDefinitionRef ref : processDefinitionWrapper.getDefinitions()) {
			if (ref.getId().equals(PROCESS_ID)) {
				definitionRef = ref;
				break;
			}
		}

		// if 'org.jbpm.test.hello' can not be found, applicaiton return
		if (definitionRef == null) {
			System.out.println(PROCESS_ID + " doesn't exist");
			return;
		}
		
		ProcessInstanceRef processInstanceRef = startProcess(definitionRef);
        System.out.println("Id: " + processInstanceRef.getId());
        System.out.println("DefinitionId: " + processInstanceRef.getDefinitionId());
        
		// get task list for mary
        List<TaskRef> taskList = getTaskListForPotentialOwner("mary");
        for (TaskRef task : taskList) {
            // claim a task
            // client.claimTask(client, task.getId(), "mary");

            // start and complete a task with parameters
            Map<String, String> formParams = new HashMap<String, String>();
            formParams.put("Explanation-", "very good");
            formParams.put("Outcome-", "Approved");
            completeTask(task.getId(), formParams);
        }

	}
	
	private void completeTask(long taskId, Map<String, String> params) throws Exception {
		String completeTaskUrl = BASE_URL + "form/task/" + taskId + "/complete";
		String dataFromService = getDataFromService(completeTaskUrl, "POST", params, true);
		System.out.println(dataFromService);

		return;
	}

	private List<TaskRef> getTaskListForPotentialOwner( String actorId) throws Exception {
        String getTaskListUrl = BASE_URL + "tasks/" + actorId + "/participation";
        String dataFromService = getDataFromService(getTaskListUrl, "GET");
        System.out.println(dataFromService);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        TaskRefWrapper wrapper = gson.fromJson(dataFromService, TaskRefWrapper.class);

        List<TaskRef> taskList = wrapper.getTasks();

        return taskList;
    }

	public static void main(String[] args) throws Exception {
		new RestClientTask("mary", "mary").test();
	}

}
