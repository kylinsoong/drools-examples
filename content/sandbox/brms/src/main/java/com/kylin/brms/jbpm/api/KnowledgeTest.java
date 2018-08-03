package com.kylin.brms.jbpm.api;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

/**
 * A knowledge base needs to contain all of the process definitions, or references to the process definitions, 
 * that the session might need to execute. 
 * 
 * Use a knowledge builder to load the processes from the required resources (for example, the classpath or file system) 
 * and then create a new knowledge base from the knowledge builder
 *
 * A knowledge base can be shared across sessions and usually is only created once at the start of the application. 
 * 
 * A knowledge bases can be changed dynamically, allowing processes to be added or removed at runtime. 
 * 
 */
public class KnowledgeTest {

	public static void main(String[] args) {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("jbpm/HellWorld.bpmn"), ResourceType.BPMN2);
		if(kbuilder.hasErrors()) {
			if (kbuilder.getErrors().size() > 0) {
				for (KnowledgeBuilderError error : kbuilder.getErrors()) {
					System.err.println(error.toString());
				}
			}
		}
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		
	}
}
