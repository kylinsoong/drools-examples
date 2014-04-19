package org.jbpm.quickstarts.multipleinstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class LoopSubprocess {

	public static final void main(String[] args) {
		try {
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			
			List<String> list = new ArrayList<String>();
			for(int i = 1 ; i < 6 ; i ++){
				list.add("1000" + i);
			}
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("list", list);
	        ksession.startProcess("com.sample.bpmn", parameters);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("multipleinstance/loopprocess.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
}
