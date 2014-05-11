package org.jbpm.demo.rest;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.serialization.jaxb.impl.process.JaxbProcessInstanceResponse;

public class RestEasyClientGetProcessInstance extends RestEasyClientBase {

	public void execute() throws Exception {
		
		int processInstanceId = 1;

		String process_instance_procInstId = url + root + "/process/instance/" + processInstanceId;
		
		System.out.println("get ProcessInstance via " + process_instance_procInstId);
		
		ClientRequest restRequest = getClientRequestFactory().createRequest(process_instance_procInstId);
		
		ClientResponse<?> responseObj = checkResponse(restRequest.get());
		
		JaxbProcessInstanceResponse response = responseObj.getEntity(JaxbProcessInstanceResponse.class);
		
		System.out.println(response);
	}

	public static void main(String[] args) throws Exception {
		new RestEasyClientGetProcessInstance().execute();
	}

}
