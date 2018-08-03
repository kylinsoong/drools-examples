package org.drools.examples;

import org.drools.examples.ksession.fire.FireProcess;

public class App {

	public static void main(String[] args) {
		System.out.println("\nDrools 6.x fire rules");
		
		FireProcess.fireRulesOnRemoteServer();
	}

}
