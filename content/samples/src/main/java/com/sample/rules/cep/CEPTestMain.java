package com.sample.rules.cep;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.domain.cep.FireAlarm;
import com.sample.domain.cep.FireDetected;

public class CEPTestMain {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = kContainer.newKieSession("ksession-cep");
        ksession.setGlobal( "fireAlarm", new FireAlarm() );
        EntryPoint fireDetectionStream = ksession.getEntryPoint("fireDetectionStream");
//        EntryPoint sprinklerDetectionStream = ksession.getEntryPoint("sprinklerDetectionStream");
        
        fireDetectionStream.insert(new FireDetected());
        
        ksession.fireUntilHalt();
        
        System.out.println(fireDetectionStream);
//        System.out.println(sprinklerDetectionStream);
        
        System.out.println(ksession.getGlobal("fireAlarm"));
        
        
        
        ksession.dispose();
    }

}
