package com.sample;

import static com.sample.Utils.form;
import static com.sample.Utils.newSessionFromClasspathContainer;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.model.Transaction;
import com.sample.model.TransactionType;

/**
 * transaction amount is more than twice the average amount of the last 4 transactions, the transaction will be denied.
 * 
 * @author kylin
 *
 */
public class Test_Scenario_2 {

	public static void main(String[] args) throws InterruptedException {

	    KieSession ksession = newSessionFromClasspathContainer("ksession-cep-fraud-detection");
   	
     	for(int i = 0 ; i < 4; i ++) {
     	   fireRules(ksession, form("USE1001" + i, 10, TransactionType.CREDIT));
     	}
     	
     	fireRules(ksession, form("USE1001", 40, TransactionType.CREDIT));
//     	
//     	Thread.sleep(5 * 1000);
//     	
//     	fireRules(ksession, form("USE1001", 130, TransactionType.CREDIT));
     	
//     	for(int i = 0 ; i < 3; i ++) {
//            fireRules(ksession, form("USE1001", 130, TransactionType.CREDIT));
//         }
     
     	ksession.dispose();

	}
	
	private static void fireRules(KieSession ksession, Transaction transaction) {
        EntryPoint entryPoint = ksession.getEntryPoint("Credit Card");
        if (entryPoint != null) {
            entryPoint.insert(transaction);
        }
        ksession.insert(transaction);
        ksession.fireAllRules();
        if(transaction.isDenied()) {
            System.out.println(transaction + " Denied: " + transaction.getDeniedCause());
        } else {
            System.out.println(transaction + " Accept");
        }
        
    }
	


}
