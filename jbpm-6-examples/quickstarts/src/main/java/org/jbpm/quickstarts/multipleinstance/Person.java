package org.jbpm.quickstarts.multipleinstance;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 6133459397269109930L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person(String id) {
		super();
		this.id = id;
	}

	public String toString() {
		return "Person [id=" + id + "]";
	}

}
