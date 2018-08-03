package org.drools.examples;

import org.drools.examples.model.Account;
import org.drools.examples.model.AllocatedCashflow;
import org.drools.examples.model.Cashflow;
import org.drools.examples.model.SimpleDate;
import org.drools.examples.model.TypedCashflow;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_Banking {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules-banking");
        
        Number[] numbers = new Number[] {new Integer(3), new Integer(1), new Integer(4), new Integer(1), new Integer(5)};
        for(int i = 0 ; i < numbers.length ; i ++) {
        	kSession.insert(numbers[i]);
        }
        
        Cashflow[] cashflows = new Cashflow[]{
        		new Cashflow(new SimpleDate("01/12/2017"), 300.00),
                new Cashflow(new SimpleDate("05/01/2017"), 100.00),
                new Cashflow(new SimpleDate("11/07/2017"), 500.00),
                new Cashflow(new SimpleDate("07/01/2017"), 800.00),
                new Cashflow(new SimpleDate("02/10/2017"), 400.00)
        };
        for(int i = 0 ; i < cashflows.length ; i ++) {
        	kSession.insert(cashflows[i]);
        }
        
        TypedCashflow[] typedCashflows = new TypedCashflow[]{
        		new TypedCashflow(new SimpleDate("01/12/2017"), 300.00, TypedCashflow.CREDIT),
                new TypedCashflow(new SimpleDate("05/01/2017"), 100.00, TypedCashflow.CREDIT),
                new TypedCashflow(new SimpleDate("11/07/2017"), 500.00, TypedCashflow.CREDIT),
                new TypedCashflow(new SimpleDate("07/01/2017"), 800.00, TypedCashflow.DEBIT),
                new TypedCashflow(new SimpleDate("02/10/2017"), 400.00, TypedCashflow.DEBIT)
        };
        for(int i = 0 ; i < typedCashflows.length ; i ++) {
        	kSession.insert(typedCashflows[i]);
        }
        
        Account acc1 = new Account(1);
        Account acc2 = new Account(2);
        AllocatedCashflow[] allocatedCashflows = new AllocatedCashflow[] {
        		new AllocatedCashflow(acc1,new SimpleDate("01/01/2017"), 300.00, TypedCashflow.CREDIT),
        		new AllocatedCashflow(acc1,new SimpleDate("05/02/2017"), 100.00, TypedCashflow.CREDIT),
                new AllocatedCashflow(acc2,new SimpleDate("11/03/2017"), 500.00, TypedCashflow.CREDIT),
                new AllocatedCashflow(acc1,new SimpleDate("07/02/2017"), 800.00, TypedCashflow.DEBIT),
                new AllocatedCashflow(acc2,new SimpleDate("02/03/2017"), 400.00, TypedCashflow.DEBIT),
                new AllocatedCashflow(acc1,new SimpleDate("01/04/2017"), 200.00, TypedCashflow.CREDIT),
                new AllocatedCashflow(acc1,new SimpleDate("05/04/2017"), 300.00, TypedCashflow.CREDIT),
                new AllocatedCashflow(acc2,new SimpleDate("11/05/2017"), 700.00, TypedCashflow.CREDIT),
                new AllocatedCashflow(acc1,new SimpleDate("07/05/2017"), 900.00, TypedCashflow.DEBIT),
                new AllocatedCashflow(acc2,new SimpleDate("02/05/2017"), 100.00, TypedCashflow.DEBIT) 
        };
        for(int i = 0 ; i < allocatedCashflows.length ; i ++) {
        	kSession.insert(allocatedCashflows[i]);
        }
        
        kSession.fireAllRules();
        kSession.dispose();
	}

}
