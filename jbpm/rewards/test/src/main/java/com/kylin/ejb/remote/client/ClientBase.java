package com.kylin.ejb.remote.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class ClientBase {
	
	static String PROCESS_JNDI = null;
	static String RASK_JNDI = null;
	
	static {
		InputStream in= null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jndiName.properties");
			Properties prop = new Properties();
			prop.load(in);
			PROCESS_JNDI = prop.getProperty("processRemote");
			RASK_JNDI = prop.getProperty("taskRemote");
		} catch (IOException e) {
			throw new RuntimeException("Can not load jndiName.properties from class path", e);
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException("Can not close stream", e);
				}
				in = null;
			}
		}
	}

	public Context getContext() throws NamingException {

		return new InitialContext();
	}

	public abstract void test() throws Exception;

	protected void prompt(Object msg) {
		System.out.println("\n  " + msg);
		stop(1000L);
	}

	protected void stop(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}