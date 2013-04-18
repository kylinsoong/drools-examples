package org.jbpm.quickstarts.usertask.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {
		
	private final String values;
	
	private boolean finished;
	
	public MinaClientHandler(String values) {
		this.values = values ;
	}
	
	public boolean isFinished() {
		return finished ;
	}
	
	public void sessionOpened(IoSession session) {
		Log.log("Session opened in client, session ID: " + session.getId());
		session.write(values);
	}
	
	public void messageReceived(IoSession session, Object message) {
		Log.log("Message received in the client");
		Log.log("Message is: " + message.toString());
		Log.log("key -> " + session.getAttribute("key"));
	}
	
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}

}
