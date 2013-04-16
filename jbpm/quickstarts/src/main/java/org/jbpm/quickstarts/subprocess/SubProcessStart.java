package org.jbpm.quickstarts.subprocess;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.quickstarts.QuickStartBase;


public class SubProcessStart extends QuickStartBase{

	public void test() {
		StatefulKnowledgeSession ksession = createKnowledgeSessionWithDrl("quickstarts/subprocess-enoughMoney.drl", "quickstarts/subprocess-main.bpmn", "quickstarts/subprocess-sub.bpmn");
		
		// Set the Global variable
		RiskyAccounts risky = new RiskyAccounts();
		ksession.setGlobal( "risky", risky );
		
		// Fire the enoughMoney rule
		ksession.insert(new Account(-10, "Kylin Soong"));
		ksession.fireAllRules();
		
		// Start the Main Process
		ksession.startProcess("org.jbpm.quickstarts.subprocess-main");
	}
	
	public static void main(String[] args) {
		new SubProcessStart().test();
	}

}
