package org.drools.examples;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;


public abstract class ExampleBase {

	protected StatefulKnowledgeSession createKnowledgeSession(String... drls) {
		KnowledgeBase kbase = createKnowledgeBase(drls);
		return createKnowledgeSession(kbase);
	}
	
	protected StatelessKnowledgeSession createStatelessKnowledgeSession(String... drls) {
		KnowledgeBase kbase = createKnowledgeBase(drls);
		return createStatelessKnowledgeSession(kbase);
	}
	
	protected KnowledgeBase createKnowledgeBase(String... drls) {
		
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        for (String drl: drls) {
            kbuilder.add(ResourceFactory.newClassPathResource(drl), ResourceType.DRL);
        }
        
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                throw new RuntimeException("Create KnowledgeBuilder Error," + kbuilder.getErrors().toString());
            }
        }
        return kbuilder.newKnowledgeBase();
    }
	
	protected StatelessKnowledgeSession createStatelessKnowledgeSession(KnowledgeBase kbase) {
		
		StatelessKnowledgeSession result;
        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        
        result = kbase.newStatelessKnowledgeSession();
        
		return result;
	}
	
	protected StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase) {
		
	    StatefulKnowledgeSession result;
        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        
        result = kbase.newStatefulKnowledgeSession();
        
		return result;
	}
	
	public abstract void test();
}
