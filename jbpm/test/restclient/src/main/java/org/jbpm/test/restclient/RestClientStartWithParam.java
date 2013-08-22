package org.jbpm.test.restclient;

import java.util.HashMap;
import java.util.Map;

import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessDefinitionRefWrapper;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestClientStartWithParam extends RestClientSimple{

	public RestClientStartWithParam(String username, String password) {
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
		
		// start a process instance with parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("employee", "kylin");
        params.put("reason", "theReason");
        
//        startProcessWithParameters(definitionRef, params);
        
        ProcessInstanceRef processInstanceRef = startProcessWithParameters(definitionRef, params);
        System.out.println("Id: " + processInstanceRef.getId());
        System.out.println("DefinitionId: " + processInstanceRef.getDefinitionId());
		
	}
	
	protected ProcessInstanceRef startProcessWithParameters(ProcessDefinitionRef definitionRef, Map<String, String> params) throws Exception {
		String newInstanceUrl = BASE_URL + "process/definition/" + definitionRef.getId() + "/complete";
		String dataFromService = getDataFromService(newInstanceUrl, "POST", params, true);
        System.out.println(dataFromService);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ProcessInstanceRef processInstanceRef = gson.fromJson(dataFromService, ProcessInstanceRef.class);
        return processInstanceRef;
	}

	public static void main(String[] args) throws Exception {
		new RestClientStartWithParam("admin", "admin").test();
	}

}
