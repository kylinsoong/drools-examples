package org.jbpm.quickstarts.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.quickstarts.QuickStartBase;

public class EscalationEventStart extends QuickStartBase{

	public static void main(String[] args) {
		new EscalationEventStart().test();
	}

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/escalationEventProcess.bpmn");
		List<String> errorList = new ArrayList<String>();
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("inputData", null);
        parameters.put("errorList", errorList) ;
        ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.escalationeventprocess", parameters);
        errorList = (List<String> )((WorkflowProcessInstance)process).getVariable("errorList");
        System.out.println("Error List: " + errorList);
	}

}
