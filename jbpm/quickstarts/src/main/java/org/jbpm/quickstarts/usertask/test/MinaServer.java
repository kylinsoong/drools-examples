package org.jbpm.quickstarts.usertask.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 
 * The “MinaServer” class contains a main method and an interface named “IoAcceptor” is used to accept the incoming 
 * connections from the client and that fires the event to the handler. Two filters has been used, the first one is 
 * the “LoggingFilter” which logs all the events and requests and the second one is the “ProtocolCodecFilter” which 
 * is used to convert an incoming ByteBuffer into message POJO.
 * 
 * @author kylin
 *
 */
public class MinaServer {
	
	static {
		ConsoleAppender console = new ConsoleAppender();
		String PATTERN = "%d %-5p [%c] (%t) %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
	}
	
	private static final int PORT = 1234;

	public static void main(String[] args) throws IOException {
		
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		acceptor.setHandler(new MinaServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(PORT));
	}

}
