package org.jbpm.conductor.util;

import java.util.Properties;



public class PropertyLoader {

	public static Properties getProperties() {
	    Properties properties = new Properties();
		try {
			properties.load(PropertyLoader.class.getResourceAsStream("/jBPM.properties"));
		} catch (Throwable t) {
			// do nothing, use defaults
		}
		return properties;
	}
	
	public static Properties getProperties(String name) {
	    Properties properties = new Properties();
		try {
			properties.load(PropertyLoader.class.getResourceAsStream(name));
		} catch (Throwable t) {
			// do nothing, use defaults
		}
		return properties;
	}
}
