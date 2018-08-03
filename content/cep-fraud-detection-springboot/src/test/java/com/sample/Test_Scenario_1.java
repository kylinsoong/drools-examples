package com.sample;

import static com.sample.Utils.*;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.model.Transaction;
import com.sample.model.TransactionType;

/**
 * More than 3 transactions in less than 5 seconds, the transaction will be denied.
 * 
 * @author kylin
 *
 */
public class Test_Scenario_1 {

	public static void main(String[] args) {

	    KieSession ksession = newSessionFromClasspathContainer("ksession-cep-fraud-detection");
   	
     	for(int i = 0 ; i < 10; i ++) {
     	    String userID ;
     	   if(i % 2 == 0) {
     	      userID = "USE1001";
     	   } else {
     	      userID = "USE1002";
     	   }
     	  fireRules(ksession, form(userID, 10, TransactionType.CREDIT));
     	}
     	
    
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
