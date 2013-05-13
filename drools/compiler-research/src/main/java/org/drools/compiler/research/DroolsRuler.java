package org.drools.compiler.research;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsRuler {

	private KnowledgeBuilder knowledgeBuilder;

	public DroolsRuler() {
		KnowledgeBuilderConfiguration config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, this.getClass().getClassLoader());
		knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config);
	}
	
	public void compile(List<Resource> drlResources) throws Exception {
		for (Resource resource : drlResources) {
			knowledgeBuilder.add(resource, ResourceType.DRL);
		}
	}
	
	public void run(Model model) {
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
		StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
		session.insert(model);
		session.fireAllRules();
		session.dispose();
	}
	
	public KnowledgeBuilder getKnowledgeBuilder() {
		return knowledgeBuilder;
	}

}
