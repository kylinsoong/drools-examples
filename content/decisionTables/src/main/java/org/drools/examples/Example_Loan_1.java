package org.drools.examples;

import org.drools.examples.model.FinancialInfo;
import org.drools.examples.model.XPressProduct;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_Loan_1 {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	KieSession kSession = kContainer.newKieSession("ksession-loantables");
    	
    	FinancialInfo financialInfo = new FinancialInfo();
    	financialInfo.setNMI(16000.0);
    	financialInfo.setExistingEMIAmount(5000.0);
    	    	
    	XPressProduct xPressProduct = new XPressProduct();
    	xPressProduct.setLoanScehme(XPressProduct.Xpress_Credit);
    	
    	kSession.insert(financialInfo);
    	kSession.insert(xPressProduct);
    	kSession.fireAllRules();
        kSession.dispose();
    	
    	
	}

}
