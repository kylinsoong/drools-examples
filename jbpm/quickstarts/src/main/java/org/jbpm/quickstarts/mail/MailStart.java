package org.jbpm.quickstarts.mail;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class MailStart extends QuickStartBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/mail.bpmn");
		ksession.getWorkItemManager().registerWorkItemHandler("Email", new MailWorkItemHandler());
		ksession.startProcess("org.jbpm.quickstarts.mail");
	}

	public static void main(String[] args) {
		new MailStart().test();
	}

}
