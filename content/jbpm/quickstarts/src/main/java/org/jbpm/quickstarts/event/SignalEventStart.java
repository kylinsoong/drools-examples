package org.jbpm.quickstarts.event;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class SignalEventStart extends QuickStartBase {

	public static void main(String[] args) {
		new SignalEventStart().test();
	}

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/singleEventProcessWithData.bpmn");
		ksession.startProcess("org.jbpm.quickstarts.singleeventprocesswithdata");
		ksession.signalEvent("payment", 90);
		ksession.dispose();
	}

}
