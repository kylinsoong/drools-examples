package org.drools.examples.ksession;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class PackageVersionViaAPI {

	static KnowledgeAgent kagent;

	private static StatefulKnowledgeSession readKnowledgeSession(String changeset) throws Exception {
		kagent = KnowledgeAgentFactory.newKnowledgeAgent("kagent");    
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		kagent.applyChangeSet(ResourceFactory.newClassPathResource(changeset));
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}

	public static void main(String[] args) throws Exception {

		StatefulKnowledgeSession ksession = readKnowledgeSession("changeset.xml");
		
		KnowledgeBase kbase = ksession.getKnowledgeBase();
		KnowledgePackage pkg = kbase.getKnowledgePackages().iterator().next();
		System.out.println(pkg.getName());
		
		
		ksession.dispose();
		kagent.dispose();
		
		Runtime.getRuntime().exit(0);
	}
}
