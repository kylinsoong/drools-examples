package org.jbpm.quickstarts.subprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.quickstarts.QuickStartBase;

public class MultipleNodeInstanceProcessStart extends QuickStartBase {

	public static void main(String[] args) {
		new MultipleNodeInstanceProcessStart().test();
	}

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/multipleNodeInstanceProcess.bpmn");
		
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
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.multiplenodeinstanceprocess",parameters);
        List errorList = (List) ((WorkflowProcessInstance)process).getVariable("errorList");
        System.out.println("Error List: " + errorList);
	}

}
