package org.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.helloworld.Person;


public class Example_FireRule_Locally {

    public static void main(String[] args) {

        Person person = new Person();
        person.setFirstName("Anton");
        person.setLastName("RedHat");
        person.setHourlyRate(11);
        person.setWage(20);
        
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieSession ksession = kContainer.newKieSession("test-ksession");
        ksession.insert(person);
        ksession.fireAllRules();
        ksession.dispose();
    }

}
