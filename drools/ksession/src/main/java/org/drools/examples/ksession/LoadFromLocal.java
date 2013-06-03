package org.drools.examples.ksession;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class LoadFromLocal {
	
	private static StatefulKnowledgeSession readKnowledgeSession(String name) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(name),ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase.newStatefulKnowledgeSession();
	}

	public static void main(String[] args) throws Exception {
		
		StatefulKnowledgeSession ksession = readKnowledgeSession("KnowledgeAgentUsage.drl");
		ksession.fireAllRules();
		ksession.dispose();
		
	}

}
