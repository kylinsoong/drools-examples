package com.sample;

import static com.sample.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.models.RuleInput;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-drools-brms/index.html
 * @author kylin
 *
 */
public class Main {
    
//    static {
//        System.setProperty("drools.dump.dir", "/home/kylin/tmp/drools-dump");
//    }
    
    static KieSession newSession() {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        return container.newKieSession("transportation-ksession");
    }
    
    static void assertEqual(Object obj1, Object obj2) {
        if(!obj1.equals(obj2)) {
            throw new RuntimeException("assert failed, [" + obj1 + "] not equals [" + obj2 + "]");
        }
    }
    
    static void assertTrue(boolean result) {
        if(!result) {
            throw new RuntimeException("assert failed");
        }
    }

    public static void main(String[] args) {

        System.out.println("TEST START ...");
        
        testTollFee1();
        testTollFee2();
        testTollFee3();
        testTollFee4();
        testTollFee5();
        testTollFee6();
        testTollFee7();
        testTollFee8();
        testTollFee9();
        testTollFee10();
        
        System.out.println("TEST SUCCESS");
    }

    private static void testTollFee1() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("TAG");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("1");
        input.setDayOfWeek(5);
        input.setTimeOfDay(18);
        input.setPurposeOfUse("A");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 01: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee2() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("TAG");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("1");
        input.setDayOfWeek(5);
        input.setTimeOfDay(7);
        input.setPurposeOfUse("A");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 02: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee3() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("TAG");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("1");
        input.setDayOfWeek(1);
        input.setTimeOfDay(18);
        input.setPurposeOfUse("A");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 03: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee4() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("OCR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("1");
        input.setDayOfWeek(1);
        input.setTimeOfDay(7);
        input.setPurposeOfUse("A");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 04: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee5() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("OCR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("2");
        input.setDayOfWeek(5);
        input.setTimeOfDay(18);
        input.setPurposeOfUse("B");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 05: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee6() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("OCR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("2");
        input.setDayOfWeek(5);
        input.setTimeOfDay(7);
        input.setPurposeOfUse("B");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 06: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee7() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("OCR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("2");
        input.setDayOfWeek(1);
        input.setTimeOfDay(18);
        input.setPurposeOfUse("B");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 07: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee8() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("MIR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("ANNUAL");
        input.setVehicleClass("2");
        input.setDayOfWeek(1);
        input.setTimeOfDay(7);
        input.setPurposeOfUse("B");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 08: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee9() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("MIR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("TEMP");
        input.setVehicleClass("3");
        input.setDayOfWeek(5);
        input.setTimeOfDay(18);
        input.setPurposeOfUse("C");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 09: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }
    
    private static void testTollFee10() {

        KieSession ksession = newSession();
        
        RuleInput input = new RuleInput();
        input.setIdentifyMethod("MIR");
        input.setOperator("201");
        input.setDetectionPointCode("001");
        input.setAccountType("TEMP");
        input.setVehicleClass("3");
        input.setDayOfWeek(1);
        input.setTimeOfDay(7);
        input.setPurposeOfUse("C");
        
        ksession.insert(input);
        
        ksession.fireAllRules();
        
        System.out.println("TEST 10: TollFee = " + input.getTollFee() + ", AdminFee = " + input.getAdminFee() + ", Total = " + input.getTollFee());
        
        ksession.dispose();
        ksession.destroy();
    }

   
    
}
