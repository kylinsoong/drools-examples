package com.sample;

import static com.sample.utils.RulesUtils.checkErrors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.models.Booking;
import com.sample.models.CountryCode;
import com.sample.utils.FactFactory;

public class Main {
    
    static {
        System.setProperty("drools.dump.dir", "/home/kylin/tmp/drools-dump");
    }
    
    static KieSession newSession() {
        KieContainer container = KieServices.Factory.get().getKieClasspathContainer();
        if(checkErrors(container)) {
            throw new IllegalArgumentException("Rule Errors.");
        }
        
        return container.newKieSession("logistics-ksession");
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
        
        testWHL_43510457804020();  
        testHasCode();
        testONE_43510238804160();       
        testONE_43510158804118();
//        testONE_43519188804541();
//        testPortMapping();
//        testPortMapping_1();
//        testONE_43510375803063();
//        testONE_43510375803063_2();
//        testONE_43510375803063_3(); 
//        testONE_43510238804160_2();     
//        testONE_43510238804160_3();
//        testONE_43510238804160_4();
        
        System.out.println("TEST SUCCESS");
    }

    /**
     * A.如果卸货港或目的港是美国、加拿大港口的，HS CODE必须是8位数字。若订舱托书中品名内的HS CODE超过8位，请系统自动截取前8位填写到指定栏位，若不满8未，则末尾自动以“0”补齐后填写到指定栏位
     */
    static void testONE_43510375803063_2() {

        KieSession ksession = newSession();
        
        Booking b1 = FactFactory.ONE_43510375803063();
        
        ksession.insert(b1);
        
        ksession.fireAllRules();
        
        b1.getValues().getData().get(0).getDetail().forEach(d -> {
            assertEqual(8, d.getWfobccHscode().length());
//            System.out.println(d.getWfobccHscode());
        });
        
     
        
        ksession.dispose();
    }
    
    /**
     * B.如果卸货港或目的港是非美国、加拿大港口的，HS CODE必须是6位数字。若订舱托书中品名内的HS CODE超过6位，请系统自动截取前6位填写到指定栏位，若不满6未，则末尾自动以“0”补齐后填写到指定栏位；
     */
    static void testONE_43510375803063_3() {

        KieSession ksession = newSession();
        
        Booking b2 = FactFactory.IAL_43510139804040();
        
//        ksession.insert(b1);
        ksession.insert(b2);
        
        ksession.fireAllRules();
        
      
        
        b2.getValues().getData().get(0).getDetail().forEach(d -> {
           assertEqual(6, d.getWfobccHscode().length());
//            System.out.println(d.getWfobccHscode());
        });
        
        
        ksession.dispose();
    }

    static void testPortMapping() {

        KieSession ksession = newSession();
        
        CountryCode load = new CountryCode("SHANGHAI");
        CountryCode discharge = new CountryCode("LONG BEACH,CA");
        ksession.insert(load);
        ksession.insert(discharge);
        
        ksession.fireAllRules();
        
        assertEqual("CHN", load.getCode());
        assertEqual("USA", discharge.getCode());
        
        ksession.dispose();
    }
    
    static void testPortMapping_1() {

        KieSession ksession = newSession();
        
        CountryCode discharge = new CountryCode("BUENAVENTURA");
        ksession.insert(discharge);
        
        ksession.fireAllRules();
        
        assertEqual("COL", discharge.getCode());
        
        ksession.dispose();
    }

    /**
     * 7.如果订舱的托书中，其卸货港或目的港是美国线、加拿大港口，且收发通开头都是“KUEHNE”，请在导入托书的同时，在备注里中添加：
     *   SCAC CODE:BANQ
     *   ACI CODE:8041
     * 8.如果订舱的托书中，其卸货港或目的港是美国线、加拿大港口，但收发通开头都不是“KUEHNE”，请在导入托书的同时，在备注里中添加：
     *   CARRIER FILLING  
     */
    static void testONE_43510375803063() {

        KieSession ksession = newSession();
        
        Booking b1 = FactFactory.ONE_43510375803063();
        Booking b2 = FactFactory.ONE_43519188804541();
        
        ksession.insert(b1);
        ksession.insert(b2);
        
        ksession.fireAllRules();
        
        String expected1 = "CONTRACT NUMBER: FIX-RIC5076704_EC\\rOB/L NUMBER:\nSCAC CODE:BANQ\nACI CODE:8041";
        String expected2 = "CONTRACT NUMBER: SC0119919\\rOB/L NUMBER:\\r4-27 ONE PS5\nCARRIER FILLING";
        assertEqual(expected1, b1.getValues().getData().get(0).getSummary().getWfobcBookingRemark());
        assertEqual(expected2, b2.getValues().getData().get(0).getSummary().getWfobcBookingRemark());

        ksession.dispose();
    }

    static void testONE_43519188804541() {

        KieSession ksession = newSession();
        
        Booking booking = FactFactory.ONE_43519188804541();
        
        ksession.insert(booking);
        
        ksession.fireAllRules();
        
        booking.getValues().getData().get(0).getDetail().forEach(d -> {
            assertEqual("PACKAGE", d.getWfobccPcs());
        });
        
        ksession.dispose();
        
    }

    static void testONE_43510158804118() {

        KieSession ksession = newSession();
        
        Booking booking1 = FactFactory.ONE_43510158804118();
        Booking booking2 = FactFactory.ONE_43510238804160();
        ksession.insert(booking1);
        ksession.insert(booking2);
        ksession.fireAllRules();
        
        assertEqual(booking1.getValues().getData().get(0).getSummary().getWfobcLoadPort(), booking1.getValues().getData().get(0).getSummary().getWfobcPaymentPlace());
        assertEqual(booking2.getValues().getData().get(0).getSummary().getWfobcDischargePort(), booking2.getValues().getData().get(0).getSummary().getWfobcPaymentPlace());
        
        String expectedTerm1 = "CY/CY\nFREIGT PREPAID AT SHANGHAI";
        String expectedTerm2 = "CY/CY\nFREIGT COLLECT AT ROTTERDAM";
        
        assertEqual(expectedTerm1, booking1.getValues().getData().get(0).getSummary().getWfobcTransportTerm());
        assertEqual(expectedTerm2, booking2.getValues().getData().get(0).getSummary().getWfobcTransportTerm());
        
        ksession.dispose();
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
        booking.getValues().getData().get(0).getSummary().setWfobcVesselCompany("MATSON");
        
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
    
    /**
     * 1. 所有SHIPPER开头是“KUEHNE”的，在【发货人代码】栏位内填入：N:SMTC-NV00299
     * 2.所有SHIPPER开头不是“KUEHNE”的，在【发货人代码】栏位内填入：B
     */
    static void testONE_43510238804160_2() {


        KieSession ksession = newSession();
        
        Booking b1 = FactFactory.ONE_43510238804160();
        Booking b2 = FactFactory.ONE_43519188804541();
        ksession.insert(b1.getValues().getData().get(0).getSummary());
        ksession.insert(b2.getValues().getData().get(0).getSummary());
        
        ksession.fireAllRules();
        
        assertEqual("N:SMTC-NV00299", b1.getValues().getData().get(0).getSummary().getWfobcShipperCode());
//        assertEqual(null, b2.getValues().getData().get(0).getSummary().getWfobcShipperCode());
       
        ksession.dispose();
    }
    
    /**
     * 1. 所有SHIPPER开头是“KUEHNE”的，在【发货人代码】栏位内填入：N:SMTC-NV00299
     */
    static void testONE_43510238804160_3() {


        KieSession ksession = newSession();
        
        Booking b1 = FactFactory.ONE_43510238804160();
        ksession.insert(b1);
        
        ksession.fireAllRules();
        
//        System.out.println(b1.getValues().getData().get(0).getSummary().getWfobcShipperCode());
//        System.out.println(b2.getValues().getData().get(0).getSummary().getWfobcShipperCode());
        
        assertEqual("N:SMTC-NV00299", b1.getValues().getData().get(0).getSummary().getWfobcShipperCode());
//        assertEqual("B", b2.getValues().getData().get(0).getSummary().getWfobcShipperCode());
       
        ksession.dispose();
    }
    
    /**
     * 2.所有SHIPPER开头不是“KUEHNE”的，在【发货人代码】栏位内填入：B
     */
    static void testONE_43510238804160_4() {


        KieSession ksession = newSession();
        
        Booking b2 = FactFactory.ONE_43519188804541();
        ksession.insert(b2);
        
        ksession.fireAllRules();
                
        assertEqual("B", b2.getValues().getData().get(0).getSummary().getWfobcShipperCode());
       
        ksession.dispose();
    }
    
}
