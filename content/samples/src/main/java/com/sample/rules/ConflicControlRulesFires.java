package com.sample.rules;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.domain.conflict.Customer;
import com.sample.domain.conflict.Room;

public class ConflicControlRulesFires {

    public static void main(String[] args) {

        KieServices services = KieServices.Factory.get();
        KieContainer container = services.getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        KieSession ksession = container.newKieSession("ksession-conflict");
        
        ksession.insert(createCustomer(20, "john", true));
        ksession.insert(createCustomer(70, "mark", false));
        
        Room room = new Room();
        room.setAccessible(true);
        room.setNumber(10);
        ksession.insert(room);
        
        ksession.fireAllRules();
        System.out.println("The Knowledge Session has the following " + ksession.getObjects().size() + " facts:");
        ksession.getObjects().forEach(f -> {
            System.out.println("   * " + f);
        });
        
        
        ksession.dispose();
    }

    private static Customer createCustomer(int i, String name, boolean b) {

        Customer c = new Customer();
        c.setAge(i);
        c.setName(name);
        c.setDisabilities(b);
        return c;
    }

}
