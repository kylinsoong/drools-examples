package com.sample.rules;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;

public class RulesUtils {

    public  static boolean checkErrors(KieContainer container) {

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
