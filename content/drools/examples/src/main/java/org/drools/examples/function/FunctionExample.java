package org.drools.examples.function;

import org.drools.examples.ExampleBase;
import org.drools.runtime.StatefulKnowledgeSession;

public class FunctionExample extends ExampleBase {

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSession("examples/function.drl", "examples/00835175.drl");
		ksession.fireAllRules();
	}

	public static void main(String[] args) {
		new FunctionExample().test();
	}

}
