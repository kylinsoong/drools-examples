package org.drools.examples.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDate extends Date {

	private static final long serialVersionUID = -7165358883944608061L;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public SimpleDate(String datestr) {
		try {
			setTime(format.parse(datestr).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Could not parse date (" + datestr + ").", e);
		}
	}

}
