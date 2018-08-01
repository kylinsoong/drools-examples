package com.sample.model;

import java.io.Serializable;

/**
 * Base interface for all our <code>facts</code>
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface Fact extends Serializable {

	public abstract String getId();
	
}
