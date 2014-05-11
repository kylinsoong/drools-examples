package org.jbpm.demo.rest;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class RestEasyClientTaskStart extends RestEasyClientBase {

public void execute() throws Exception {
		
		long taskId = 1;
		
		String oper = "start";
		
		setRoot("/task/" + taskId + "/" + oper);
		
		String path = url + root ;
		
		System.out.println("Start Task via " + path);
		
		ClientRequest clientRequest = getClientRequestFactory().createRequest(path);
		
		ClientResponse<?> responseObj = checkResponse(clientRequest.post());
		
		System.out.println("Done");

	}

	public static void main(String[] args) throws Exception {
		new RestEasyClientTaskStart().execute();
	}

}
