package org.jbpm.quickstarts.evaluation;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;

public class EvaluationStart extends QuickStartBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/evaluation.bpmn");
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new WSHumanTaskHandler());
		ksession.startProcess("org.jbpm.quickstarts.evaluation");
	}

	public static void main(String[] args) {
		new EvaluationStart().test();
	}

}
