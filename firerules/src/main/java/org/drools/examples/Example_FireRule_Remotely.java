package org.drools.examples;


import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.redhat.service.Person;

public class Example_FireRule_Remotely {

    public static void main(String[] args) {

        Person person = new Person();
        person.setFirstName("Anton");
        person.setLastName("RedHat");
        person.setHourlyRate(11);
        person.setWage(20);
        
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId( "com.redhat.service", "helloworld", "1.0.0" );
        KieContainer kContainer = kieServices.newKieContainer(releaseId);
        KieBase kbase = kContainer.getKieBase("testBase");
        KieSession ksession = kbase.newKieSession();
        ksession.insert(person);
        ksession.fireAllRules();
        ksession.dispose();
    }

}
