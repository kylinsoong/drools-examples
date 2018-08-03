package org.jbpm.quickstarts.looping;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class LoopingStart extends QuickStartBase{

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/looping.bpmn");
		ksession.startProcess("org.jbpm.quickstarts.looping");
	}
	
	public static void main(String[] args) {
		new LoopingStart().test();
	}

}
