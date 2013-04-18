package org.jbpm.quickstarts.usertask;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;
import org.jbpm.quickstarts.usertask.util.QuickStartHelper;
import org.jbpm.test.JBPMHelper;

public class UserTaskStart extends QuickStartBase {

	public void test() {
		KnowledgeBase kbase = createKnowledgeBase("quickstarts/helloworldUserTask.bpmn");
		StatefulKnowledgeSession ksession = JBPMHelper.newStatefulKnowledgeSession(kbase);
	}

	public static void main(String[] args) {
		QuickStartHelper.startTaskService();
		new UserTaskStart().test();
	}

}
