package org.drools.examples.fact;

import java.lang.reflect.Field;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class LocalFireMain {
	
	static {
		System.setProperty("drools.dump.dir", "/home/kylin/tmp");
	}

	public static void main(String[] args) throws Exception {

		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
	    KieBase kbase = kContainer.getKieBase("defaultKieBase");
	    KieSession ksession = kbase.newKieSession();
	    
	    FactType factType = kbase.getFactType("org.kie.example", "Person");
	    Object obj = factType.newInstance();
	    Class<? extends Object> clas = obj.getClass();
	    Field field = clas.getDeclaredField("name");
	    field.setAccessible(true);
	    field.set(obj, "Kylin Soong");
	    field = clas.getDeclaredField("age");
	    field.setAccessible(true);
	    field.set(obj, 28);
	    
	    ksession.insert(obj);
	    
    	ksession.fireAllRules();
    	ksession.dispose();
	}

}
