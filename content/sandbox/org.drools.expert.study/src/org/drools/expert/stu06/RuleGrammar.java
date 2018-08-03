package org.drools.expert.stu06;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.expert.stu.StuBase;
import org.drools.io.ResourceFactory;

public class RuleGrammar extends StuBase {

	public static void main(String[] args) {
		
		new RuleGrammar().test1();
	}

	private void test1() {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add( ResourceFactory.newClassPathResource( "ruleGrammar.drl", getClass() ), ResourceType.DRL );
		if ( kbuilder.hasErrors() ) {
			System.err.println(kbuilder.getErrors().toString());
		}
	}

}
