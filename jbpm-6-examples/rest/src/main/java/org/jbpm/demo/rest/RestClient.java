package org.jbpm.demo.rest;

import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.kie.api.task.model.Task;
import org.kie.services.client.serialization.jaxb.impl.task.JaxbTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

	protected static Logger logger = LoggerFactory.getLogger(RestClient.class);
	
	static String url = "http://10.66.218.46:8080/business-central/";
	static String userId = "admin";
	static String password = "password1!";
	
	public static Task getTaskInstanceInfo(long taskId) throws Exception {
	    URL address = new URL(url + "/task/" + taskId);
	    ClientRequest restRequest = createRequest(address);
	       
	    ClientResponse<JaxbTaskResponse> responseObj = restRequest.get(JaxbTaskResponse.class);
//	    ClientResponse<InputStream> taskResponse = responseObj.get(InputStream.class);
//	    JAXBContext jaxbTaskContext = JAXBContext.newInstance(JaxbTaskResponse.class);
//	    StreamSource source  = new StreamSource(taskResponse.getEntity());
//	    return jaxbTaskContext.createUnmarshaller().unmarshal(source, JaxbTaskResponse.class).getValue();
	    return null;
	}

	private static ClientRequest createRequest(URL address) {
	    return getClientRequestFactory().createRequest(address.toExternalForm());
	    }

	private static ClientRequestFactory getClientRequestFactory() {
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST,
	    AuthScope.ANY_PORT, AuthScope.ANY_REALM), new UsernamePasswordCredentials(userId, password));
	    ClientExecutor clientExecutor = new ApacheHttpClient4Executor(httpClient);
	    return new ClientRequestFactory(clientExecutor, ResteasyProviderFactory.getInstance());
	}

	public static void main(String[] args) throws Exception {
		
		getTaskInstanceInfo(1);
		
	}	
}
