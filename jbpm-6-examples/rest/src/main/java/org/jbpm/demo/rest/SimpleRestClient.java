package org.jbpm.demo.rest;

import java.net.MalformedURLException;
import java.net.URL;

import org.kie.api.runtime.KieSession;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;


public class SimpleRestClient {

	public static void main(String[] args) throws MalformedURLException {

		String deploymentId = "org.kie.example:project1:1.0.0-SNAPSHOT";
        URL appUrl = new URL("http://10.66.218.46:8080/business-central/");
        String user = "admin";
        String password = "password1!";
        
        RemoteRestRuntimeFactory restSessionFactory = new RemoteRestRuntimeFactory(deploymentId, appUrl, user, password);
        RemoteRuntimeEngine engine = restSessionFactory.newRuntimeEngine();
        KieSession ksession = engine.getKieSession();
        ksession.insert(null);
        ksession.fireAllRules();
	}

}
