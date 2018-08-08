package com.sample;

import static com.sample.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.models.Customer;
import com.sample.models.FinancialInfo;

public class Main {
    
    static KieSession newSession() {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        return container.newKieSession("loanApprovalSession");
    }

    public static void main(String[] args) {

        KieSession ksession = newSession();
        
        FinancialInfo f = new FinancialInfo();
        f.setIsXBSalaryAccount("false");
        f.setRGScore(4);
        f.setNoOfXpressCreditTopups(0);
        
        Customer c = new Customer();
        c.setAge(24);
        
        ksession.insert(f);
        ksession.insert(c);
        ksession.fireAllRules();

        ksession.dispose();
        
        
    }

}
