package org.kie.examples.phreak;

import static org.kie.examples.phreak.RulesUtils.checkErrors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.drools.devguide.phreakinspector.model.PhreakInspector;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {

    public static void main(String[] args) throws IOException {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieBase kieBase = container.getKieBase("laziness3KBase");
        PhreakInspector inspector = new PhreakInspector();
        InputStream in = inspector.fromKieBase(kieBase);
        System.out.println(IOUtils.toString(in));
        
        
//        KieSession ksession = container.newKieSession("laziness3KSession");
//        
//        DataGenerator generator = new DataGenerator(3);
//        generator.fillWithTestFacts();
//        generator.insertInto(ksession);
//        
//        ksession.fireAllRules();
//        
//        ksession.dispose();
    }

}
