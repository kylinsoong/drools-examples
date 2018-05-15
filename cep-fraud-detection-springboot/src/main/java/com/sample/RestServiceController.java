package com.sample;

import static com.sample.Utils.newSessionFromClasspathContainer;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(RestServiceController.class);
	
	private final AtomicLong counter = new AtomicLong();
	
	private KieSession ksession;
	
	public RestServiceController() {
	    
	    logger.info("Init KieSession from classpath container");
	    
        ksession = newSessionFromClasspathContainer("ksession-cep-fraud-detection");
	}

	@RequestMapping("/rest/ping")
    public Success greeting() {
        return new Success(counter.getAndIncrement(), "Success!");
    }
	
	@RequestMapping("/rest/transaction")
	public Transaction addTransaction(@RequestParam(value="userID") String userID, @RequestParam(value="balance") String balance, @RequestParam(value="type") String type) {
		
	    logger.info("/rest/transaction?userID=" + userID + "&balance=" + balance + "&type=" + type);
	    
	    Transaction t = new Transaction(userID, new BigInteger(balance), TransactionType.valueOf(type));
	    
	    String entryPointName = "Credit Card";
	    if(type.equals("CREDIT")) {
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

	 
}
