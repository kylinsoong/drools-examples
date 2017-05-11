package org.drools.examples;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.redhat.myproject.Person;

public class Example_FireRuleLocally {

    public static void main(String[] args) {

        Person person = new Person();
        person.setFirstName("Anton");
        person.setLastName("RedHat");
        person.setHourlyRate(11);
        person.setWage(20);
        
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kbase = kContainer.getKieBase("myBase");
        KieSession ksession = kbase.newKieSession();
        ksession.insert(person);
        ksession.fireAllRules();
        ksession.dispose();
    }

}
