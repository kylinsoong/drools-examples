package org.jbpm.test.humantask.test.task.service;

import org.jbpm.task.service.hornetq.HornetQTaskServer;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.test.humantask.test.BaseTest;
import org.junit.Before;
import org.junit.Test;

public class TaskServerTest extends BaseTest {

	private HornetQTaskServer hornetQServer;
	
	private MinaTaskServer minaTaskServer;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testHorneQTaskServer() throws Exception {
		hornetQServer = new HornetQTaskServer(taskService, 10101);
		Thread t = new Thread(hornetQServer);
		t.start();
		Thread.sleep(5000);
		hornetQServer.stop();
	}
	
	@Test
	public void testMinaTaskServer() throws Exception {
		minaTaskServer = new MinaTaskServer(taskService);
		Thread t = new Thread(minaTaskServer);
		t.start();
		Thread.sleep(5000);
		minaTaskServer.stop();
	}
	
}
