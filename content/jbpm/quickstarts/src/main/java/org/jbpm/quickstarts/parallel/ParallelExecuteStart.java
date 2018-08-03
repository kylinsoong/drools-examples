package org.jbpm.quickstarts.parallel;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class ParallelExecuteStart extends QuickStartBase{

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/parallelExecuteProcess.bpmn");
		ksession.startProcess("org.jbpm.quickstarts.parallelExecuteProcess");
	}
	
	public static void main(String[] args) {
		new ParallelExecuteStart().test();
	}

}
