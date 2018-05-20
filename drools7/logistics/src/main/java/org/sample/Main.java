package org.sample;

import static org.sample.util.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.sample.models.Booking;
import org.sample.util.FactFactory;

public class Main {
    
    static KieSession newSession() {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        return container.newKieSession("logistics-ksession");
    }
    
    static void assertEqual(Object obj1, Object obj2) {
        if(!obj1.equals(obj2)) {
            throw new RuntimeException("assert failed");
        }
    }
    
    static void assertTrue(boolean result) {
        if(!result) {
            throw new RuntimeException("assert failed");
        }
    }

    public static void main(String[] args) {

    
        
//        testWHL_43510457804020();
        
//        testHasCode();
               
//        testONE_43510238804160();
        
        testONE_43510158804118();
        
    }

    

    static void testWHL_43510457804020() {

        KieSession ksession = newSession();
        
        Booking booking = FactFactory.WHL_43510457804020();
        ksession.insert(booking);
        
        ksession.fireAllRules();
        
        String execpectedShipper = "KUEHNE & NAGEL LIMITED\\r11-16F, BLOCK\n1, LIFE HUB AT DANING\\rOFFICE TOWER,\n1868, GONG HE XIN RD,\\rZHABEI DISTRICT,\nSHANGHAI, CHINA\\rAGENT OF BLUE ANCHOR\nLINE\\rKN REF. 4351-0457-804.020";
        String execpectedConsignee = "KUEHNE + NAGEL S.A.S.\\rREG.DIAN NO.011/RES.08930\n28-10-03\\rCRA 48 NO.10-45, OFICINA 905,\nCC.\\rMONTERREY COLOMBIA NIT:800.039.9961\\rAGENT\nOF BLUE ANCHOR LINE";
        assertEqual(execpectedShipper, booking.getValues().getData().get(0).getSummary().getWfobcShipper());
        assertEqual(execpectedConsignee, booking.getValues().getData().get(0).getSummary().getWfobcConsignee());
        
        ksession.dispose();
    }
    
    static void testHasCode() {


        KieSession ksession = newSession();
        
        Booking booking = FactFactory.ONE_43519188804541();
        ksession.insert(booking);
        
        ksession.fireAllRules();
        
        booking.getValues().getData().forEach(d -> {
            d.getDetail().forEach(de -> {
                assertTrue(de.getWfobccHscode().equals(""));
            });
        });
        
        ksession.dispose();
    }
    
    static void testONE_43510238804160() {


        KieSession ksession = newSession();
        
        Booking booking = FactFactory.ONE_43510238804160();
        ksession.insert(booking);
        
        ksession.fireAllRules();
        
        assertEqual("N:SMTC-NV00299", booking.getValues().getData().get(0).getSummary().getWfobcShipperCode());
       
        ksession.dispose();
    }
    
    static void testONE_43510158804118() {

        KieSession ksession = newSession();
        
        ksession.fireAllRules();
        
        ksession.dispose();
    }

}
