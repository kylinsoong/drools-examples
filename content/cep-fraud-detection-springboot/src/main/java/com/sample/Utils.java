package com.sample;

import java.math.BigInteger;

import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.model.Transaction;
import com.sample.model.TransactionType;

public class Utils {
    
    public static KieSession newSessionFromClasspathContainer(String name) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        return kContainer.newKieSession(name);
    }
    
    public static boolean checkErrors(KieContainer container) {

        Results results = container.verify();
        boolean hasErrors = results.hasMessages(Level.ERROR);
        if(hasErrors) {
            for (Message message : results.getMessages()) {
                System.err.println(String.format("Line: %1$s / Column: %2$s , Path: %3$s / Error Message: %4$s ", message.getLine(), message.getColumn(), message.getPath(), message.toString()));
            }
        }
        return hasErrors;
    }
    
    public static Transaction form(String userID, long value, TransactionType type) {
        return new Transaction(userID, BigInteger.valueOf(value), type);
    }

}
