package org.drools.examples.fact;

import java.lang.reflect.Field;
import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.type.FactType;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


/**
 * Hello world!
 *
 */
public class SupportCase01080393 {
	public void fireRule() {

		KieServices kServices = KieServices.Factory.get();
		ReleaseId releaseId = kServices.newReleaseId("com.westconnect", "RoutingTest", "1.4-SNAPSHOT");
		KieContainer kContainer = kServices.newKieContainer(releaseId);
		KieScanner kScanner = kServices.newKieScanner(kContainer);
		KieSession kSession = null;

		try {
			while (true) {
				kScanner.scanNow();

				KieBase base = kContainer.getKieBase();
				kSession = base.newKieSession();

				kSession.addEventListener(new DefaultAgendaEventListener() {
					public void afterMatchFired(AfterMatchFiredEvent event) {
						System.out.println("###### Fired: " + event.getMatch().getRule().getName());
					}
				});
			
			FactType ft = base.getFactType("com.westconnect.routingtest", "RoutingObj");

			Object obj = null;
			try {
				
				/* After creating a new build of the rule project JAR, this next line throws a NPE 
				   because the "definedClass" field is null, and so there is no Class object to 
				   use to create the new instance. */
				obj = ft.newInstance();
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			Class<? extends Object> c = obj.getClass();
		    Field inf = c.getDeclaredField("balance");
		    inf.setAccessible(true);
		    inf.set(obj, new Integer(5010));
			
			kSession.insert(obj);
			
		    kSession.fireAllRules();
		    		    
		    Collection<? extends Object> objects = kSession.getObjects();
		    
		    Object obj2 = null;
		    for (Object o : objects) {
		    	System.out.println(o.toString());
		    	if("com.westconnect.routingtest.RoutingObj".equalsIgnoreCase(o.getClass().getName())) {
		    		obj2 = o;
		    		break;
		    	}
		    }

			Class<? extends Object> c2 = obj2.getClass();
		    Field of2 = c2.getDeclaredField("routingType");
		    of2.setAccessible(true);
		    System.out.println(of2.get(obj2));
		    
		    System.out.println("***************");
		    Thread.sleep(15000);
		}

		} catch (Exception e ) {

			e.printStackTrace();

		} finally {
			if ( kSession != null ) {
				kSession.dispose();
			}

		}
	}

	public static void main(String[] args) {
		SupportCase01080393 a = new SupportCase01080393();
		a.fireRule();
	}
}
