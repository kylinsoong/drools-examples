package org.jbpm.test.restclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.jboss.bpm.console.client.model.ProcessDefinitionRef;
import org.jboss.bpm.console.client.model.ProcessDefinitionRefWrapper;
import org.jboss.bpm.console.client.model.ProcessInstanceRef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * This Test class need 
 *   1. BRMS 5.3.1 running, which supply Rest service 
 *   2. 'hello.bpmn' and 'hellotask.bpmn' be add to BRMS console, and 'org.jbpm.test' build successful in BRMS console
 *   3. 'BRMS_HOME/server/default/deploy/business-central-server.war/WEB-INF/lib/gwt-console-rpc-2.3.8.Final.jar'be added to classpath
 * 
 * @author kylin
 * 
 */
public class RestClientSimple {

	protected static final String BASE_URL = "http://localhost:8080/business-central-server/rs/";
	protected static final String AUTH_URL = BASE_URL + "identity/secure/j_security_check";
	protected final String username;
	protected final String password;
	
	protected static String PROCESS_ID = "org.jbpm.test.hello";

	public RestClientSimple(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	private void test() throws Exception {
		
		// get process definitions
		ProcessDefinitionRefWrapper processDefinitionWrapper = getProcessDefinitions();
		
		// pick up 'org.jbpm.test.hello'
		ProcessDefinitionRef definitionRef = null;
		for (ProcessDefinitionRef ref : processDefinitionWrapper.getDefinitions()) {
			if(ref.getId().equals(PROCESS_ID)) {
				definitionRef = ref;
				break;
			}
        }
		
		// if 'org.jbpm.test.hello' can not be found, applicaiton return
		if (definitionRef == null) {
            System.out.println(PROCESS_ID + " doesn't exist");
            return;
        }
		
		// start process
		ProcessInstanceRef processInstanceRef = startProcess(definitionRef);
        System.out.println("Id: " + processInstanceRef.getId());
        System.out.println("DefinitionId: " + processInstanceRef.getDefinitionId());

	}
	
	protected ProcessInstanceRef startProcess(ProcessDefinitionRef definitionRef) throws Exception {
		String newInstanceUrl = BASE_URL + "process/definition/" + definitionRef.getId() + "/new_instance";
		String dataFromService = getDataFromService(newInstanceUrl, "POST");
        System.out.println(dataFromService);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ProcessInstanceRef processInstanceRef = gson.fromJson(dataFromService, ProcessInstanceRef.class);
        return processInstanceRef;
	}

	protected ProcessDefinitionRefWrapper getProcessDefinitions() throws Exception {
		String newInstanceUrl = BASE_URL + "process/definitions";
		String dataFromService = getDataFromService(newInstanceUrl, "GET");
        System.out.println(dataFromService);
        
        Gson gson = new Gson();
        ProcessDefinitionRefWrapper wrapper = gson.fromJson(dataFromService, ProcessDefinitionRefWrapper.class);
        for (ProcessDefinitionRef ref : wrapper.getDefinitions()) {
            System.out.println("process ID is: " + ref.getId());
        }
		
		return wrapper;
	}
	
	protected String getDataFromService(String urlpath, String method) throws Exception {
        // no params
        return getDataFromService(urlpath, method, null, false);
    }

	protected String getDataFromService(String urlpath, String method, Map<String, String> params, boolean multipart) throws Exception {
		
		HttpClient httpclient = new HttpClient();
		
		HttpMethod theMethod = null;
        StringBuffer sb = new StringBuffer();
        
        if ("GET".equalsIgnoreCase(method)) {
            theMethod = new GetMethod(urlpath);
        } else if ("POST".equalsIgnoreCase(method)) {
        	theMethod = new PostMethod(urlpath);
        	if (params != null) {
        		if (multipart){
        			List<Part> parts = new ArrayList<Part>();
        			for (String key : params.keySet()) {
                        parts.add(new StringPart(key, params.get(key)));
                    }
        			((PostMethod) theMethod).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), theMethod.getParams()));
        		} else {
        			List<NameValuePair> NameValuePairList = new ArrayList<NameValuePair>();
                    for (String key : params.keySet()) {
                        NameValuePairList.add(new NameValuePair(key, params.get(key)));
                    }
                    ((PostMethod) theMethod).setRequestBody(NameValuePairList.toArray(new NameValuePair[0]));
        		}
        	}
        }
        
        if (username != null && password != null) {
        	try {
                int result = httpclient.executeMethod(theMethod);
                System.out.println("result = " + result);
                // System.out.println(theMethod.getResponseBodyAsString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                theMethod.releaseConnection();
            }
        	
        	PostMethod authMethod = new PostMethod(AUTH_URL);
        	NameValuePair[] data = { new NameValuePair("j_username", username), new NameValuePair("j_password", password) };
            authMethod.setRequestBody(data);
            try {
                int result = httpclient.executeMethod(authMethod);
                System.out.println("result = " + result);
                // System.out.println(theMethod.getResponseBodyAsString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                authMethod.releaseConnection();
            }
        }
		
        try {
            int result = httpclient.executeMethod(theMethod);
            System.out.println("result = " + result);
            sb.append(theMethod.getResponseBodyAsString());
            String rawResult = sb.toString();
            return rawResult;

        } catch (Exception e) {
            throw e;
        } finally {
            theMethod.releaseConnection();
        }
        
	}

	public static void main(String[] args) throws Exception {
		
		new RestClientSimple("admin", "admin").test();
		
	}

	
}
