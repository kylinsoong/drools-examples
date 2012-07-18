package com.kylin.brms.jbpm.humantask;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;

public class HumanTaskExecutor {

	public static void main(String[] args) {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("jbpm/humanTaskTest.bpmn"), ResourceType.BPMN2);
		if(kbuilder.hasErrors()) {
			if (kbuilder.getErrors().size() > 0) {
				for (KnowledgeBuilderError error : kbuilder.getErrors()) {
					System.err.println(error.toString());
				}
			}
		}
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		ksession.getWorkItemManager().registerWorkItemHandler("HumanTask", new WSHumanTaskHandler());

		
		ProcessInstance processInstance = ksession.startProcess("com.kylin.brms.jbpm.humantask");
	}

}
