package org.jbpm.demo.approval.test;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;

public class ScriptTaskTest extends TestBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("test.bpmn");
		String recipient = "kylin";
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("recipient", recipient);
        ksession.startProcess("org.jbpm.demo.rewards-test", params);
	}

	public static void main(String[] args) {
		new ScriptTaskTest().test();
	}

}
