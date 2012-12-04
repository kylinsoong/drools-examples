package com.kylin.jbpm.quickstarts.helloworld;

import java.util.HashMap;
import java.util.Map;

import org.kie.runtime.StatefulKnowledgeSession;

import com.kylin.jbpm.quickstarts.ServiceBase;


public class HelloWorldServiceStart extends ServiceBase{
	
	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("helloworld/helloworld.bpmn");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("person", new Person("Kylin Soong"));
		ksession.startProcess("com.kylin.jbpm.quickstart.helloworld", params);
	}

	public static void main(String[] args) {
		new HelloWorldServiceStart().test();
	}

}
