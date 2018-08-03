package org.drools.examples.rest;

import org.jboss.resteasy.client.ClientRequest;


public class RestClient {

	public static void main(String[] args) {
		
		ClientRequest request = new ClientRequest("http://localhost:8080/jboss-brms/rest/packages/org.drools.examples/versions");
		request = request.accept("");
		System.out.println(request.getBody());
	}

}
