package com.kylin.jbpm.quickstarts;

import org.kie.KnowledgeBase;
import org.kie.KnowledgeBaseFactory;
import org.kie.builder.KnowledgeBuilder;
import org.kie.builder.KnowledgeBuilderFactory;
import org.kie.builder.ResourceType;
import org.kie.io.ResourceFactory;
import org.kie.runtime.KnowledgeSessionConfiguration;
import org.kie.runtime.StatefulKnowledgeSession;

public class ServiceBase {

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
	
	protected StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase) {
		
	    StatefulKnowledgeSession result;
        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        
        result = kbase.newStatefulKnowledgeSession();
        
		return result;
	}
}
