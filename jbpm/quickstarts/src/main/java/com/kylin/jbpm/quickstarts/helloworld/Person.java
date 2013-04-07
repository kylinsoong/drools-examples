package com.kylin.jbpm.quickstarts.helloworld;

public class Person {
	
	private String name;

	public Person(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
