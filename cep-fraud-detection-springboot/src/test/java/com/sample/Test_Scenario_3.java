package com.sample;

import static com.sample.Utils.newSessionFromClasspathContainer;

import java.math.BigInteger;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.model.Transaction;
import com.sample.model.TransactionType;

/**
 * A withdrawal transaction is not allowed less than 10 seconds after a new transaction
 * 
 * @author kylin
 *
 */
public class Test_Scenario_3 {

	public static void main(String[] args) throws InterruptedException {

	    KieSession ksession = newSessionFromClasspathContainer("ksession-cep-fraud-detection");
   	
     	for(int i = 0 ; i < 1; i ++) {
     		EntryPoint entryPoint = ksession.getEntryPoint("Credit Card");
     		Transaction transaction = getTransaction();
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
     	
     	Thread.sleep(3 * 1000);
     	
     	for(int i = 0 ; i < 1 ; i ++) {
     		EntryPoint entryPoint = ksession.getEntryPoint("Withdraw");
     		Transaction transaction = getWithdraw();
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
     		
     	ksession.dispose();
     	ksession.destroy();
	}
	
	static Transaction getTransaction() {
		return new Transaction("USE1001", BigInteger.valueOf(10), TransactionType.CREDIT);
	}
	
	static Transaction getWithdraw() {
		return new Transaction("USE1001", BigInteger.valueOf(10), TransactionType.WITHDRAW);
	}


}
