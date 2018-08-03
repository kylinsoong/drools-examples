package org.drools.examples.fact;

import java.lang.reflect.Field;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RemoteFireMain {

	public static void main(String[] args) throws Exception {
		
		
		
		KieServices kServices = KieServices.Factory.get();
		ReleaseId releaseId = kServices.newReleaseId( "com.redhat.gss", "01081954", "1.1-SNAPSHOT" );
		KieContainer kContainer = kServices.newKieContainer( releaseId );
		KieScanner kScanner = kServices.newKieScanner( kContainer );
		kScanner.start( 1000 * 10);
		
		while(true) {
			KieBase kbase = kContainer.getKieBase();
		    KieSession ksession = kbase.newKieSession();
		    
		   Object obj = kbase.getKiePackage("com.redhat.gss").getFactTypes();
		   
		   System.out.println(obj);
		   
//		    FactType factType = kbase.getFactType("com.redhat.gss", "Person");
//		    Object obj = factType.newInstance();
//		    Class<? extends Object> clas = obj.getClass();
//		    Field field = clas.getDeclaredField("name");
//		    field.setAccessible(true);
//		    field.set(obj, "Red Hat");
//		    field = clas.getDeclaredField("age");
//		    field.setAccessible(true);
//		    field.set(obj, 23);
//		    
//		    ksession.insert(obj);
//		    
//	    	ksession.fireAllRules();
//	    	ksession.dispose();
	    	Thread.sleep(15000);
		}
		
	}

}
