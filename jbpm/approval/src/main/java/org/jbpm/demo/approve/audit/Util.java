package org.jbpm.demo.approve.audit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String timestamp() {
		return new SimpleDateFormat("(MM/dd/yy HH:mm:ss) ").format(new Date());
	}
}
