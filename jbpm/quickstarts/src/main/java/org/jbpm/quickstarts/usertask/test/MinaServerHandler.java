package org.jbpm.quickstarts.usertask.test;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 
 * “sessionOpened” is called when the session is opened and it is used to set the session idle time
 * “receiveMessage” is used to receive the message sent by the client
 * “sessionIdle” is used to close the session when it was idle for 10 secs
 * “exceptionCaught” is used to close the session when an exception occured
 * 
 * @author kylin
 *
 */
public class MinaServerHandler extends IoHandlerAdapter {
	
	private final Logger logger = Logger.getLogger(MinaServerHandler.class);

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		session.setAttribute("Values: ");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.info("Disconnecting the idle.");
		session.close(true);
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("Message received in the server..");
		logger.info("Message is: " + message.toString());
	}
	
	

}
