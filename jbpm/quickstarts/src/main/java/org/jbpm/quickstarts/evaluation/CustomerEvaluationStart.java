package org.jbpm.quickstarts.evaluation;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.Person;
import org.jbpm.quickstarts.QuickStartBase;
import org.jbpm.quickstarts.Request;

public class CustomerEvaluationStart extends QuickStartBase {
	
	private static Integer underAged    = 11;
	private static Integer adultAged    = 25;
	private static Integer richCustomer = 2000; // greater than 999.
	private static Integer poorCutomer  = 2;

	public void test() {
		
		underagedCustomerEvaluationTest();
		
		adultCustomerEvaluationTest();
		
		richCustomerEvaluationTest();
		
		emptyRequestCustomerEvaluationTest();
	}
	
	protected void emptyRequestCustomerEvaluationTest() {

		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/customerEvaluationFinanceRules.drl", "quickstarts/customerEvaluation.bpmn");
				
		Map<String, Object> params = new HashMap<String, Object>();
		
		System.out.println("=============================================");
		System.out.println("= Starting Process Empty Request Test Case. =");
		System.out.println("=============================================");
		
		ksession.startProcess("org.jbpm.quickstarts.customerEvaluation", params);
		
		ksession.fireAllRules();
		ksession.dispose();
	}

	protected void richCustomerEvaluationTest() {
		
		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/customerEvaluationFinanceRules.drl", "quickstarts/customerEvaluation.bpmn");
			
		Person adultEval = getAdultCustomer();
		Request richEval = getRichCustomer();
		ksession.insert(adultEval);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("person", adultEval);
		params.put("request", richEval);
				
		System.out.println("==========================================");
		System.out.println("= Starting Process Rich Adult Test Case. =");
		System.out.println("==========================================");
				
		ksession.startProcess("org.jbpm.quickstarts.customerEvaluation", params);
		
		ksession.fireAllRules();
		ksession.dispose();
	}

	protected void adultCustomerEvaluationTest() {

		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/customerEvaluationFinanceRules.drl", "quickstarts/customerEvaluation.bpmn");
		
		Person adultEval = getAdultCustomer();
		Request poorEval = getPoorCustomer();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("person", adultEval);
		params.put("request", poorEval);
		
		System.out.println("==========================================");
		System.out.println("= Starting Process Poor Adult Test Case. =");
		System.out.println("==========================================");
		
		ksession.insert(adultEval);
		
		ksession.startProcess("org.jbpm.quickstarts.customerEvaluation", params);
		
		ksession.fireAllRules();
		ksession.dispose();
	}

	protected void underagedCustomerEvaluationTest() {

		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/customerEvaluationFinanceRules.drl", "quickstarts/customerEvaluation.bpmn");
				
		Person underagedEval = getUnderagedCustomer();
		Request richEval = getRichCustomer();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("person", underagedEval);
		params.put("request", richEval);
		
		System.out.println("=========================================");
		System.out.println("= Starting Process Underaged Test Case. =");
		System.out.println("=========================================");
		
		ksession.insert(underagedEval);
		
		ksession.startProcess("org.jbpm.quickstarts.customerEvaluation", params);
		
		ksession.fireAllRules();
		
		ksession.dispose();
	}
	
	private Person getUnderagedCustomer() {
		Person person = new Person("kylin", "Kylin Soong");
		person.setAge(underAged);
		return person;
	}
	
	private Person getAdultCustomer() {
		Person person = new Person("kylin", "Kylin Soong");
		person.setAge(adultAged);
		return person;
	}
	
	private Request getRichCustomer() {
		Request request = new Request("1");
		request.setPersonId("kylin");
		request.setAmount(richCustomer);
		return request;
	}
	
	private Request getPoorCustomer() {
		Request request = new Request("1");
		request.setPersonId("kylin");
		request.setAmount(poorCutomer);
		return request;
	}

	public static void main(String[] args) {
		new CustomerEvaluationStart().test();
	}

}
