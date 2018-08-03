package org.drools.examples.model;

public class Customer {

	private int id;
	
	private String name;
	
	private String discription;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", discription=" + discription + "]";
	}

}
