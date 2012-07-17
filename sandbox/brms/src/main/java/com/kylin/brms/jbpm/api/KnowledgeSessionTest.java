package com.kylin.brms.jbpm.api;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.ruleflow.core.RuleFlowProcess;

/**
 * 
 * Sessions are created to interact with the process engine and execute the processes: start new processes, 
 * signal events. 
 *
 */
public class KnowledgeSessionTest {

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
		
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ProcessInstance processInstance = ksession.startProcess("com.kylin.brms");
		
		RuleFlowProcess process = (RuleFlowProcess) processInstance.getProcess();
		System.out.println("process id: " + process.getId());
	}

}
