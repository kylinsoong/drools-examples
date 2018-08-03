package org.drools.demo.rest;



import static org.drools.demo.rest.Constants.authorizationHeader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;

import com.sample.models.CountryCode;

public class FireRulesClient {
    
    static String USER = "kylin";
    static String COLON = ":";
    static String PASSWORD = "password1!";
    static String authorizationHeader = "Basic " + Base64Utility.encode((USER + COLON + PASSWORD).getBytes());
    
    static final String URL = "http://172.30.254.157:8080/kie-server/services/rest/server/containers/instances/logistics-freight-management_1.0.1";

    public static void main(String[] args) throws IOException {
        
        Command<?>[] commands = {
                CommandFactory.newInsert(new CountryCode("SHANGHAI"), "contrycode"),
                CommandFactory.newFireAllRules(),
                CommandFactory.newGetObjects("objects"),
                CommandFactory.newDispose()
        }; 
        
        Command<?> batchExecution = CommandFactory.newBatchExecution(Arrays.asList(commands), "default-stateless-ksession");
        
        WebClient wc = WebClient.create(URL);
        wc.header("Authorization", authorizationHeader);
        wc.header("accept", "application/json");
        wc.header("content-type", "application/json");
        Response resp = wc.post("{\"lookup\":\"default-stateless-ksession\",\"commands\":[{\"insert\":{\"object\":{\"com.sample.models.CountryCode\":{\"port\":\"SHANGHAI\"}},\"out-identifier\":\"contrycode\"}},{\"fire-all-rules\":{}},{\"get-objects\":{\"out-identifier\":\"objects\"}},{\"dispose\":{}}]}");
        System.out.println(IOUtils.toString((InputStream) resp.getEntity()));
        wc.close();
        
    }

}
