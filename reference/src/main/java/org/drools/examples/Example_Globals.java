package org.drools.examples;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Example_Globals {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules-globals");
        List<String> list = new ArrayList<>();
        kSession.setGlobal( "myGlobalList", list );
        kSession.fireAllRules();
        kSession.dispose();
        
        System.out.println(list);
	}

}
