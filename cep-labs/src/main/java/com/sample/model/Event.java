package com.sample.model;

import java.util.Date;

/**
 * Base interface for all our <code>events</code>
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface Event extends Fact {
	
	public abstract Date getTimestamp();

}
