package org.jbpm.demo.rest.tmp;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.services.client.api.RestRequestHelper;
import org.kie.services.client.api.command.RemoteRuntimeEngine;


public class SimpleRestClient {

	public static void main(String[] args) throws Exception {

		String deploymentId = "org.brms:restTest:1.0";
        URL restBaseUrl = new URL("http://10.66.218.46:8080/business-central/");
        String user = "admin";
        String password = "password1!";
        
        RemoteRestRuntimeFactory remoteRestRuntimeFactory = new RemoteRestRuntimeFactory(deploymentId, restBaseUrl, user, password);
		RemoteRuntimeEngine engine = remoteRestRuntimeFactory.newRuntimeEngine();
		
//		System.out.println(remoteRestRuntimeFactory.newRuntimeEngine().getKieSession().getKieBase().getProcesses());
		
        
        String baseUrl = "http://10.66.218.46:8080/business-central/rest/";
        
        ClientRequestFactory requestFactory = RestRequestHelper.createRequestFactory(restBaseUrl, user, password);
       
        String processInstance = baseUrl + "runtime/" + deploymentId + "/process/instance/1";
        System.out.println(processInstance);
        
        ClientRequest restRequest = requestFactory.createRequest(processInstance);
        
        ClientResponse<?> responseObj = checkResponse(restRequest.get());
        
        System.out.println(responseObj);
        
        
	}
	
	private static ClientResponse<?> checkResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();
        if (status != 200) {
           throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }

}
