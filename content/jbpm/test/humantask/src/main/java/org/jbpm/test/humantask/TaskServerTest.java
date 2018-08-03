package org.jbpm.test.humantask;

import org.jbpm.task.service.hornetq.HornetQTaskServer;
import org.jbpm.task.service.mina.MinaTaskServer;

public class TaskServerTest extends HumanTaskBase {
	
	private HornetQTaskServer hornetQServer;
	private MinaTaskServer minaTaskServer;
	
	public TaskServerTest() throws Exception {
		setUp();
	}
	
	protected void testHorneQTaskServer() throws Exception {
		hornetQServer = new HornetQTaskServer(taskService, 10101);
		Thread t = new Thread(hornetQServer);
		t.start();
		Thread.sleep(5000);
		hornetQServer.stop();
	}
	
	protected void testMinaTaskServer() throws Exception {
		minaTaskServer = new MinaTaskServer(taskService);
		Thread t = new Thread(minaTaskServer);
		t.start();
		Thread.sleep(5000);
		minaTaskServer.stop();
	}

	public static void main(String[] args) throws Exception {
		
//		new TaskServerTest().testHorneQTaskServer();
		
		new TaskServerTest().testMinaTaskServer();
	}

}
