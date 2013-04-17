package org.jbpm.quickstarts.usertask.test;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {
	
	private final Logger logger = Logger.getLogger(MinaClientHandler.class);
	
	private final String values;
	
	private boolean finished;
	
	public MinaClientHandler(String values) {
		this.values = values ;
	}
	
	public boolean isFinished() {
		return finished ;
	}
	
	public void sessionOpened(IoSession session) {
		session.write(values);
	}
	
	public void messageReceived(IoSession session, Object message) {
		logger.info("Message received in the client..");
		logger.info("Message is: " + message.toString());
	}
	
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}

}
