package org.drools.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import com.sample.helloworld.Person;

import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;


public class Example_FireRule_KieServer {

    public static void main(String[] args) {
        
        Person person = new Person();
        person.setFirstName("Anton");
        person.setLastName("RedHat");
        person.setHourlyRate(11);
        person.setWage(20);

        
        String url = "http://localhost:8080/kie-server/services/rest/server";
        String username = "kylin";
        String password = "password1!";
        String container = "helloworld_1.0.0";
        String session = "test-ksession";
        
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
        Set<Class<?>> allClasses = new HashSet<Class<?>>();
        allClasses.add(Person.class);
        config.addExtraClasses(allClasses);
        
        KieServicesClient client  = KieServicesFactory.newKieServicesClient(config);
        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
        
        Command<?>[] commands = {
                CommandFactory.newInsert(person, "person"),
                CommandFactory.newFireAllRules("fire-identifier"),
                CommandFactory.newGetObjects("objects"),
                CommandFactory.newDispose()
        };
        
   
        
        BatchExecutionCommand batchCommand = KieServices.Factory.get().getCommands().newBatchExecution(Arrays.asList(commands), session);
        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(container, batchCommand);
        System.out.println(response.getResult().getValue("fire-identifier"));
    }

}
