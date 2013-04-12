package org.jbpm.quickstarts.helloworld;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.Person;
import org.jbpm.quickstarts.QuickStartBase;

public class HelloWorldScriptTaskStart extends QuickStartBase{
	
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("quickstarts/helloworldScriptTask.bpmn");
//		Boolean subProcessComplete = Boolean.FALSE;
//		ksession.setGlobal("subProcessComplete", subProcessComplete);
//		Person globalV = new Person("Soong");
//		String globalV = "Test";
//		ksession.setGlobal("globalV", globalV);
		ksession.startProcess("org.jbpm.quickstarts.helloworldScript");
	}

	public static void main(String[] args) {
		new HelloWorldScriptTaskStart().test();
	}

}
