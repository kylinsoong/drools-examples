package org.jbpm.quickstarts.logging;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;

public class LoggingStart extends QuickStartBase{

	
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/logging.bpmn");
		ksession.getWorkItemManager().registerWorkItemHandler("Log", new LoggingWorkItemHandler());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msg", "Test Logging Message");
		ksession.startProcess("org.jbpm.quickstarts.logging", params);
	}
	
	public static void main(String[] args) {
		new LoggingStart().test();
	}

}
