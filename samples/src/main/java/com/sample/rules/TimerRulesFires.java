package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.calendars.*;

public class TimerRulesFires {

    public static void main(String[] args) throws InterruptedException {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-calendars");
        ksession.insert(new Light("on"));
        ksession.fireAllRules();
        
//        Thread.sleep(1000 * 60);
        
//        while(true) {
//            
//            
//            
//        }
        
//        ksession.dispose();
    }

}
