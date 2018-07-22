package com.sample;

import static com.sample.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-drools-brms/index.html
 * @author kylin
 *
 */
public class Main {
    
    static {
        System.setProperty("drools.dump.dir", "/home/kylin/tmp/drools-dump");
    }
    
    static KieSession newSession() {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        return container.newKieSession("logistics-ksession");
    }
    
    static void assertEqual(Object obj1, Object obj2) {
        if(!obj1.equals(obj2)) {
            throw new RuntimeException("assert failed, [" + obj1 + "] not equals [" + obj2 + "]");
        }
    }
    
    static void assertTrue(boolean result) {
        if(!result) {
            throw new RuntimeException("assert failed");
        }
    }

    public static void main(String[] args) {

        System.out.println("TEST START ...");
        
       
        
        System.out.println("TEST SUCCESS");
    }

   
    
}
