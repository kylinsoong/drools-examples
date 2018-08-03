package org.jbpm.test.process;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.test.JbpmJUnitTestCase;

/**
 * This is a sample file to test a process.
 */
public class ProcessTestMain extends JbpmJUnitTestCase {

	public ProcessTestMain(){
		super(true);
		setPersistence(true);
	}
	
	public void testProcess() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("sample2.bpmn");
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
		
		System.out.println(processInstance.getId());
		System.out.println(ksession.getProcessInstance(processInstance.getId()));
		
		
		// check whether the process instance has completed successfully
		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Hello", "EndProcess");
//		Object obj = getVariableValue("marketingStrategy",processInstance.getId(),ksession);
		
//		WorkflowProcessInstance workflowProcessInstance = (WorkflowProcessInstance) processInstance;
//		
//		System.out.println(ksession.getProcessInstances());
//		
//		System.out.println(workflowProcessInstance.getVariable("marketingStrategy"));
		
		//System.out.println("value is "+obj);
	}
	
	public static void main(String[] args) {
		new ProcessTestMain().testProcess();
	}

}