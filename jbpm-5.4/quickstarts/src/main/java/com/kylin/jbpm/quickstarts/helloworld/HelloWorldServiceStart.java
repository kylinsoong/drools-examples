package com.kylin.jbpm.quickstarts.helloworld;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;

public class HelloWorldServiceStart {

	public static void main(String[] args) {

		StatefulKnowledgeSession ksession = createKnowledgeSession("test.bpmn");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("person", new Person("krisv"));
		ksession.startProcess("org.jbpm.examples.quickstarts.script", params);

	}

}
