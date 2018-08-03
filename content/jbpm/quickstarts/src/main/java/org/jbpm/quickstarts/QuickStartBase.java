package org.jbpm.quickstarts;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;


public abstract class QuickStartBase {

	protected StatefulKnowledgeSession createKnowledgeSession(String... process) {
		KnowledgeBase kbase = createKnowledgeBase(process);
		return createKnowledgeSession(kbase);
	}
	
	protected KnowledgeBase createKnowledgeBase(String... process) {
		
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        for (String p: process) {
            kbuilder.add(ResourceFactory.newClassPathResource(p), ResourceType.BPMN2);
        }
        
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                throw new RuntimeException("Create KnowledgeBuilder Error," + kbuilder.getErrors().toString());
            }
        }
        return kbuilder.newKnowledgeBase();
    }
	
	protected StatefulKnowledgeSession createKnowledgeSessionWithDrl(String drl,  String... process) {
		KnowledgeBase kbase = createKnowledgeBaseWithDrl(drl, process);
		return createKnowledgeSession(kbase);
	}
	
	protected KnowledgeBase createKnowledgeBaseWithDrl(String drl, String... process) {
		
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        for (String p: process) {
            kbuilder.add(ResourceFactory.newClassPathResource(p), ResourceType.BPMN2);
        }
        
        if(drl != null) {
        	kbuilder.add(ResourceFactory.newClassPathResource(drl), ResourceType.DRL);
        }
        
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                throw new RuntimeException("Create KnowledgeBuilder Error," + kbuilder.getErrors().toString());
            }
        }
        return kbuilder.newKnowledgeBase();
    }
	
	protected StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase) {
		
	    StatefulKnowledgeSession result;
        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        
        result = kbase.newStatefulKnowledgeSession();
        
		return result;
	}
	
	public abstract void test();
}
