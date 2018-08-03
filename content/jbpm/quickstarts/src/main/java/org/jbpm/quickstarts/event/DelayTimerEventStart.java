package org.jbpm.quickstarts.event;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.quickstarts.QuickStartBase;

public class DelayTimerEventStart extends QuickStartBase {

	public static void main(String[] args) {
		new DelayTimerEventStart().test();
	}

	@SuppressWarnings("static-access")
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/delayTimerEventProcess.bpmn");
		ProcessInstance process = ksession.startProcess("org.jbpm.quickstarts.delaytimereventprocess");
		try {
			Thread.currentThread().sleep(6000);
		} catch (InterruptedException e) {	
		}
		Long timerExecutionTime = (Long) ((WorkflowProcessInstance)process).getVariable("timerExecutionTime");
		System.out.println("The Process Execute Time: " + timerExecutionTime);
		ksession.dispose();
	}

}
