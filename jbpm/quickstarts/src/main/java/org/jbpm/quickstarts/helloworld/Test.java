package org.jbpm.quickstarts.helloworld;

import org.drools.runtime.process.ProcessContext;
import org.jbpm.quickstarts.HelloService;
import org.jbpm.quickstarts.Person;

public class Test {

	public static void main(String[] args) {
		Person person = new Person("Kylin Soong");
		HelloService.getInstance().sayHello(person.getName());
		person.setName("Kobe Bryant");
		
		ProcessContext kcontext = null ;
		kcontext.getKnowledgeRuntime().setGlobal("person", person);
		kcontext.setVariable("person", person);
	}

}
