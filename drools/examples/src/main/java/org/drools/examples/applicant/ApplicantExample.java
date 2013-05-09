package org.drools.examples.applicant;

import org.drools.examples.ExampleBase;
import org.drools.runtime.StatelessKnowledgeSession;

public class ApplicantExample extends ExampleBase {

	public void test() {
		StatelessKnowledgeSession ksession = createStatelessKnowledgeSession("examples/applicant.drl");
		Applicant applicant = new Applicant( "Mr John Smith", 16 );
		ksession.execute(applicant);
		System.out.println(applicant);
	}

	public static void main(String[] args) {
		new ApplicantExample().test();
	}

}
