package org.jbpm.quickstarts.usertask.test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 
 * In the “MinaClient” class the “IoConnector” interface is used to communicate with the server and that fires the event to the handler. 
 * Like the server part, The same “LoggingFilter” and “ProtocolCodecFilter” has been used. An interface named “ConnectFuture” is used to 
 * windup the asynchronous connection requests.
 * 
 * @author kylin
 *
 */
public class MinaClient {
	
	private static final int PORT = 1234;

	public static void main(String[] args) {
		
		IoConnector connector = new NioSocketConnector();
		
		connector.getSessionConfig().setReadBufferSize(2048);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		connector.setHandler(new MinaClientHandler("Hello Server.."));
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", PORT));
		future.awaitUninterruptibly();
		if (!future.isConnected()){
			return;
		}
		
		IoSession session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
		System.out.println("After Writing");
	}

}
