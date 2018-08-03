package org.jbpm.quickstarts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAll {


	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss").format( new Date(System.currentTimeMillis())));
	}

}
