package org.drools.compiler.research;

import java.util.Iterator;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.KnowledgeBuilderResult;
import org.drools.builder.KnowledgeBuilderResults;
import org.drools.builder.ResourceType;
import org.drools.builder.ResultSeverity;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsRunner {

	static StatefulKnowledgeSession createKnowledgeSession(String... drls) {
		KnowledgeBase kbase = createKnowledgeBase(drls);	
		return kbase.newStatefulKnowledgeSession();
	}
	
	static KnowledgeBase createKnowledgeBase(String... drls) {

		KnowledgeBuilderConfiguration config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, DroolsRunner.class.getClassLoader());
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config);
        
        for (String drl: drls) {
            kbuilder.add(ResourceFactory.newClassPathResource(drl), ResourceType.DRL);
        }
        
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                throw new RuntimeException("Create KnowledgeBuilder Error," + kbuilder.getErrors().toString());
            }
        }
        
        if(kbuilder.hasResults(ResultSeverity.INFO)){
        	if(kbuilder.getResults(ResultSeverity.INFO).size() > 0) {
        		Iterator<KnowledgeBuilderResult> iterator = kbuilder.getResults().iterator();
        		while(iterator.hasNext()) {
        			KnowledgeBuilderResult result = iterator.next();
        			System.out.println(result.getMessage());
        		}
        			
        	}
        }
        
        return kbuilder.newKnowledgeBase();
    }
	
	static void testSeparatedFile() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("RuleOne.drl", "RuleTwo.drl", "RuleThree.drl");
		Model model = new Model();
		ksession.insert(model);
		ksession.fireAllRules();
		ksession.dispose();
		System.out.println("DONE");
	}
	
	static void testSingleFile() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("RuleFour.drl");
		ksession.fireAllRules();
		ksession.dispose();
		System.out.println("DONE");
	}

	public static void main(String[] args) {
		testSeparatedFile();
		testSingleFile();
	}

}
