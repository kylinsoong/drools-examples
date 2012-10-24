package org.drools.expert.stu;

public class StuBase {

	public void sleep() {
		try {
			System.out.println("\nSleep 3 seconds...\n");
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		System.out.println("\nTest Paused");
	}
}
