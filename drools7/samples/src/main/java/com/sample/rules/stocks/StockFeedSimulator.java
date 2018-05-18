package com.sample.rules.stocks;

import static com.sample.rules.utils.RulesUtils.checkErrors;

import java.io.IOException;
import java.math.BigDecimal;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

public class StockFeedSimulator {

    private static final BigDecimal buyTolerance = new BigDecimal("0.75");
    
    private static final BigDecimal sellTolerance = new BigDecimal("0.50");
    
    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        if(checkErrors(kContainer)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        KieSession ksession = kContainer.newKieSession("ksession-stocks");
        EntryPoint ep = ksession.getEntryPoint("stock feed");
        ksession.setGlobal("buyTolerance", buyTolerance);
        ksession.setGlobal("sellTolerance", sellTolerance);
        
        StockFeeder feed = new StockFeeder("src/main/resources/instructions.txt");
        feed.addListener(new LoggingStockFeedListener());
        feed.addListener(new RulesStockFeedListener(ep));
        
        new Thread(() -> {
            ksession.fireUntilHalt();
        }).start();
        
        try {
            feed.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ksession.halt();
            ksession.dispose();
        }
        
        
     
    }

}
