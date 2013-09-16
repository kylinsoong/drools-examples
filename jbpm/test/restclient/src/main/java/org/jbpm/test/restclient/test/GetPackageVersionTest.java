package org.jbpm.test.restclient.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class GetPackageVersionTest {

	public static void main(String[] args) {
		
		HttpClient httpclient = new HttpClient();
		
		HttpMethod theMethod =new GetMethod("http://localhost:8080/jboss-brms/rest/packages/org.drools.examples/versions");
		
		try {
            int result = httpclient.executeMethod(theMethod);
            System.out.println("result = " + result);
             System.out.println(theMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            theMethod.releaseConnection();
        }
	}

}
