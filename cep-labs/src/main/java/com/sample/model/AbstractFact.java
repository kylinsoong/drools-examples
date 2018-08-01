package com.sample.model;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class AbstractFact implements Fact {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private final String id;
	
	public AbstractFact(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
