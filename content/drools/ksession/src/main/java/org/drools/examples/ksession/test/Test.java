package org.drools.examples.ksession.test;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new String(new ParametrosMotor().getChangeSet()));
		
		MotorReglas test = new MotorReglas();
		
		int i = 0;
		while(true) {
			System.out.println("\n" + i++);
			test.getKsession().fireAllRules();
			Thread.currentThread().sleep(10 * 1000);
		}
	}

}
