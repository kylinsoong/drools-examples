package com.sample;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.model.Success;
import com.sample.model.Transaction;
import com.sample.model.TransactionType;

/**
 * 
 * @author kylin
 *
 */
@RestController
public class RestServiceController {
	
	private final AtomicLong counter = new AtomicLong();
	
	private KieSession ksession;
	
	public RestServiceController() {
	    
	    System.out.println("[" + Thread.currentThread().getName() + "] Init RestServiceController");
	    KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        ksession = kContainer.newKieSession("ksession-cep-fraud-detection");
	}

	@RequestMapping("/rest/ping")
    public Success greeting() {
        return new Success(counter.getAndIncrement(), "Success!");
    }
	
	@RequestMapping("/rest/transaction")
	public Transaction addTransaction(@RequestParam(value="balance") String balance, @RequestParam(value="type") String type) {
		
	    System.out.println("[" + Thread.currentThread().getName() + "] /rest/transaction?/balance=" + balance + "&type=" + type);
	    
	    Transaction t = new Transaction(new BigInteger(balance), TransactionType.valueOf(type));
	    
	    String entryPointName = "Credit Card";
	    if(type.equals("CREDIT_CARD")) {
	        //do nothing
	    } else if (type.equals("WITHDRAW")) {
	        entryPointName = "Withdraw";
	    }
	    EntryPoint entryPoint = ksession.getEntryPoint(entryPointName);
        if (entryPoint != null) {
            entryPoint.insert(t);
        }
        ksession.insert(t);
        ksession.fireAllRules();
	    
	    return t;
	}	

	 private static boolean checkErrors(KieContainer container) {

	        Results results = container.verify();
	        boolean hasErrors = results.hasMessages(Level.ERROR);
	        if(hasErrors) {
	            for (Message message : results.getMessages()) {
	                System.err.println(String.format("Line: %1$s / Column: %2$s , Path: %3$s / Error Message: %4$s ", message.getLine(), message.getColumn(), message.getPath(), message.toString()));
	            }
	        }
	        return hasErrors;
	    }
}
