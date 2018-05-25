package com.sample.cxf;

import static com.sample.cxf.Constants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;

import com.sample.models.CountryCode;

/**
 * Fire logistics freight rules via CXF REST Client.
 * @author kylin
 *
 */
public class CXFRESTClient {

    public static void main(String[] args) {

        WebClient wc = WebClient.create(URL_LOGISTICS_FREIGHT_MANAGEMENT_1_0_1);
        wc.header("Authorization", authorizationHeader);
        wc.header("accept", "application/json");
        wc.header("content-type", "application/json");
        
        Arrays.asList(commands).forEach(c -> {
            System.out.println("\nSend fire rules request to BRM Rest Service:\n<------\n" + URL_LOGISTICS_FREIGHT_MANAGEMENT_1_0_1 );
            System.out.println(wc.getHeaders());
            System.out.println(c + "\n------>");
            stop(1000);
            Response resp = wc.post(c);
            if(resp.getStatus() != 200) {
                throw new RuntimeException("BRM Fire rules error");
            } else {
                try {
                    System.out.println("\nFire rules request executed SUCCESS, BRM service sesponse:" );
                    stop(2000);
                    System.out.println(IOUtils.toString((InputStream) resp.getEntity()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            stop(2000);
            
        });
    }

    private static void stop(long time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    static void formJsonString() {

        Command<?>[] commands = {
                CommandFactory.newInsert(new CountryCode("SHANGHAI"), "contrycode"),
                CommandFactory.newFireAllRules(),
                CommandFactory.newGetObjects("objects"),
                CommandFactory.newDispose()
        }; 
        
        Command<?> batchExecution = CommandFactory.newBatchExecution(Arrays.asList(commands), "default-stateless-ksession");
        
        //TODO-- convert batchExecution to json body
        
    }

}
