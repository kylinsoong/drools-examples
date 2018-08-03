package org.kie.examples.phreak.nodes;

import static org.kie.examples.phreak.RulesUtils.checkErrors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.drools.devguide.phreakinspector.model.PhreakInspector;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Accumulate {
    
    public static void main(String[] args) throws IOException {
        
        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieBase kieBase = container.getKieBase("accumulateKBase");
        PhreakInspector inspector = new PhreakInspector();
        InputStream in = inspector.fromKieBase(kieBase);
        System.out.println(IOUtils.toString(in));
        
        KieSession ksession = kieBase.newKieSession();
        
        Customer c = new Customer();
        Order o1 = new Order();
        o1.setCustomer(c);
        Order o2 = new Order();
        o2.setCustomer(c);
        
        ksession.insert(c);
        ksession.insert(o1);
        ksession.insert(o2);
    
        ksession.fireAllRules();
        
        ksession.dispose();
    }

}
