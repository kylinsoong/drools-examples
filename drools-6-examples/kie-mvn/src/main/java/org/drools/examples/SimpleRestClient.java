package org.drools.examples;

import java.net.MalformedURLException;
import java.net.URL;

import org.kie.services.client.api.RemoteRestRuntimeFactory;


public class SimpleRestClient {

	public static void main(String[] args) throws MalformedURLException {

		String deploymentId = "org.brms:HelloWorld:1.0";
        URL appUrl = new URL("http://10.66.218.46:8080/business-central/");
        String user = "admin";
        String password = "password1!";
        
        RemoteRestRuntimeFactory restSessionFactory = new RemoteRestRuntimeFactory(deploymentId, appUrl, user, password);
	}

}
