package org.jbpm.quickstarts.subprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.quickstarts.QuickStartBase;

public class ReusableSubProcessStart extends QuickStartBase {

	public static void main(String[] args) {
		new ReusableSubProcessStart().test();
	}

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/reusableSubProcess-Parent.bpmn", "quickstarts/reusableSubProcess-Child.bpmn");
		List<String> messages = new ArrayList<String>();
        messages.add("message 1");
        messages.add("message 2");
        messages.add("message 3");
        
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("messages", messages); 
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.reusablesubprocessparent",parameters);
        messages = (List<String>) ((WorkflowProcessInstance)process).getVariable("messages");
        for (String message : messages) {
            System.out.println("Message = "+message);
        }
        ksession.dispose();
	}

}
