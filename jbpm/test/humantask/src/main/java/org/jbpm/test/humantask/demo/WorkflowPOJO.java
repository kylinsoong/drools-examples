package org.jbpm.test.humantask.demo;

import java.io.Serializable;

public class WorkflowPOJO implements Serializable{

	private static final long serialVersionUID = -376824630734431264L;

	private String id;
	
	private String name;

	public WorkflowPOJO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public WorkflowPOJO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "WorkflowPOJO [id=" + id + ", name=" + name + "]";
	}
}
