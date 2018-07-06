package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.junit.After;
import org.junit.Before;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestBase {
    
    protected KieSession ksession;
    
    private String id;
    
    public TestBase(String id) {
        super();
        this.id = id;
    }
    
    @Before
    public void setup() {
        
        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        ksession = container.newKieSession(id);
    }
    
    
    @After
    public void tearDown() {
        if(null != ksession)
            ksession.dispose();
    }

}
