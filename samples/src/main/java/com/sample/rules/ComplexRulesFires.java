package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.complex.*;

public class ComplexRulesFires {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = kContainer.newKieSession("ksession-complexrules");
     
        Order order = new Order();
        OrderItem item1 = new OrderItem("001", 120.0, 80.0, 120.0, order);
        OrderItem item2 = new OrderItem("002", 160.0, 110.0, 160.0, order);
        OrderItem item3 = new OrderItem("003", 87.0, 69.0, 87.0, order);
        
        ksession.insert(order);
        ksession.insert(item1);
        ksession.insert(item2);
        ksession.insert(item3);

        ksession.fireAllRules();
       
        ksession.dispose();
    }

}
