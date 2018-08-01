package com.sample.model;

import java.io.Serializable;


/**
 * Locations within the airport where bags are scanned.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public enum Location implements Serializable {

	CHECK_IN("check-in"), SORTING("sorting"), STAGING("staging"), LOADING("loading");
	
	private String location;
	
	private Location(String location) {
		this.setLocation(location);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
