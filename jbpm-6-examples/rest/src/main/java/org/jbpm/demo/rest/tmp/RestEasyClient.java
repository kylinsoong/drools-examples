package org.jbpm.demo.rest.tmp;

import java.net.URL;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.api.RestRequestHelper;
import org.kie.services.client.serialization.jaxb.impl.JaxbCommandsRequest;

public class RestEasyClient {
	
	static String deploymentId = "org.kie.example:project1:1.0.0-SNAPSHOT";
	
	static String url = "http://localhost:8080/business-central/rest";
	
	static String root = "/runtime/" + deploymentId;
	
	
	static String user = "kylin";
	static String password = "password1!";

	public static void main(String[] args) throws Exception {
		
		System.out.println("JBoss BPM Suite 6 RestEasy Client");
		
		new RestEasyClient().client();
	}

	private void client() throws Exception {
		
		URL restBaseUrl = new URL(url);
		
		ClientRequestFactory requestFactory = RestRequestHelper.createRequestFactory(restBaseUrl, user, password);
			
		startProcess(requestFactory, "org.brms.test");
		
//		getProcessInstance(requestFactory, 4);
			
	}
	
	void getProcessInstance(ClientRequestFactory requestFactory, int processInstanceId) throws Exception {
		
		String process_instance_procInstId = url + root + "/process/instance/" + processInstanceId;
		
		System.out.println("get ProcessInstance via " + process_instance_procInstId);
		
		ClientRequest restRequest = requestFactory.createRequest(process_instance_procInstId);
		ClientResponse<?> responseObj = checkResponse(restRequest.get());
		System.out.println(responseObj.getEntity());
	}

	void execute(ClientRequestFactory requestFactory) throws Exception {
		
		String execute = url + root + "/execute";
		
		System.out.println("Execute via " + execute);
		
		JaxbCommandsRequest request = new JaxbCommandsRequest();
		
		ClientResponse<?> responseObj = checkResponse(requestFactory.createRequest(execute).post());
		
		System.out.println("DONE");
		
	}

	void startProcess(ClientRequestFactory requestFactory, String processDefId) throws Exception {
		
		String startProcessURL = url + root + "/process/" + processDefId + "/start";
		
		System.out.println("Start Process via " + startProcessURL);
		
		ClientResponse<?> responseObj = requestFactory.createRequest(startProcessURL).post();
		
		checkResponse(responseObj);
		
		System.out.println("DONE");
	}

	private ClientResponse<?> checkResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();
        if (status != 200) {
           throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }

}
