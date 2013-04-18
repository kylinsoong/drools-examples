package org.jbpm.quickstarts.usertask.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	public static void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS").format(new Date()) + " -> " + msg);
	}
	
	public static void main(String[ ] args) {
		log("test");
	}

}
