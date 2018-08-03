package org.jbpm.quickstarts.rule;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class RuleTaskStart extends QuickStartBase {

	public static void main(String[] args) {
		new RuleTaskStart().test();
	}

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/ruletaskprocess-rule.drl", "quickstarts/ruletaskprocess.bpmn");
		ksession.startProcess("org.jbpm.quickstarts.ruletaskprocess");
		ksession.fireAllRules();
		ksession.dispose();
	}

}
