package org.jbpm.demo.rest;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class RestEasyClientProcessStart extends RestEasyClientBase {

	public void execute() throws Exception {
		
		String processDefId ="org.brms.test";
		
		String startProcessURL = url + root + "/process/" + processDefId + "/start";
	
		System.out.println("Start Process via " + startProcessURL);
		
		ClientRequest clientRequest = getClientRequestFactory().createRequest(startProcessURL);
		
		ClientResponse<?> responseObj = checkResponse(clientRequest.post());
		
		System.out.println("Done");
	}

	public static void main(String[] args) throws Exception {
		new RestEasyClientProcessStart().execute();
	}

}
