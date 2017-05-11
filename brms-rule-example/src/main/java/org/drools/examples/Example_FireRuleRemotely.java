package org.drools.examples;

import java.util.Collection;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

import com.redhat.myproject.Person;

public class Example_FireRuleRemotely {

    public static void main(String[] args) {

        Person person = new Person();
        person.setFirstName("Anton");
        person.setLastName("RedHat");
        person.setHourlyRate(11);
        person.setWage(20);
        
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId( "com.redhat", "MyProject", "1.0.0-SNAPSHOT" );
        KieContainer kContainer = kieServices.newKieContainer(releaseId);
        
        Collection<String> kbases = kContainer.getKieBaseNames();
        kbases.forEach(name -> System.out.println(name));
    }

}
