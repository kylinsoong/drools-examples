package org.drools.examples;

import org.drools.runtime.StatefulKnowledgeSession;

public class HelloWorldExample extends ExampleBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("examples/helloworld.drl");
		ksession.fireAllRules();
	}

	public static void main(String[] args) {
		new HelloWorldExample().test();
	}

}
