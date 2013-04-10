package org.jbpm.quickstarts.logging;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.instance.impl.demo.SystemOutWorkItemHandler;
import org.jbpm.quickstarts.QuickStartBase;

public class LoggingStart extends QuickStartBase{

	
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/logging.bpmn");
		ksession.getWorkItemManager().registerWorkItemHandler("Log", new SystemOutWorkItemHandler());
		ksession.startProcess("org.jbpm.quickstarts.logging");
	}
	
	public static void main(String[] args) {
		new LoggingStart().test();
	}

}
