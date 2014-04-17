package org.jbpm.quickstarts.multipleinstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class MultipleNodeInstanceProcessStart {

	public static final void main(String[] args) {
		try {
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			
			List<Number> numbers = new ArrayList<Number>();
	        numbers.add(2);
	        numbers.add(4);
	        numbers.add(56);
	        numbers.add(7);
	        numbers.add(10);
	        numbers.add(13);
	        
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("numberList", numbers);
	        parameters.put("errorList", new ArrayList());
	        ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.multiplenodeinstanceprocess", parameters);
		
	        List errorList = (List) ((WorkflowProcessInstance)process).getVariable("errorList");
	        System.out.println("Odd List: " + errorList);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("multipleinstance/multipleNodeInstanceProcess.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
}
