package org.jbpm.demo.rest;

import java.net.URL;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.api.RestRequestHelper;


public abstract class RestEasyClientBase {
	
	String deploymentId = "org.kie.example:project1:1.0.0-SNAPSHOT";
	
	String ip = "localhost";
	
	String port = "8080";
	
	String url = "http://" + ip + ":" + port + "/business-central/rest";
	
	String root = "/runtime/" + deploymentId;
	
	String user = "kylin";
	
	String password = "password1!";

	public RestEasyClientBase(String deploymentId, String ip, String port, String user, String password) {
		this.deploymentId = deploymentId;
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	
	public RestEasyClientBase() {
		
	}
	
	public String root() {
		return root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientRequestFactory getClientRequestFactory() throws Exception {	
		return RestRequestHelper.createRequestFactory(new URL(url), user, password);
	}
	
	public ClientRequestFactory getClientRequestFactory(String user, String password) throws Exception {	
		setUser(user);
		setPassword(password);
		return RestRequestHelper.createRequestFactory(new URL(url), user, password);
	}
	
	public abstract void execute() throws Exception;
	
	ClientResponse<?> checkResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();
        if (status != 200) {
           throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }
}
