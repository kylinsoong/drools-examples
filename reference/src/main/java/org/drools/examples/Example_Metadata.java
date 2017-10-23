package org.drools.examples;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.definition.type.FactType;

public class Example_Metadata {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kbase = kContainer.getKieBase("rules-metadata");
        KieSession kSession = kbase.newKieSession();
        
        FactType factType = kbase.getFactType("org.drools.examples.metadata", "GoldenCustomer");
        Object obj = factType.newInstance();
        factType.set(obj, "id", 101);
        factType.set(obj, "name", "Bob");
        factType.set(obj, "discription", "this is a discription");
        factType.set(obj, "priority", 1);
        kSession.insert(obj);
        
        kSession.fireAllRules();
        kSession.dispose();
	}

}
