package org.drools.examples.applicant;

public class Applicant {

	private String name;
	
	private int age;
	
	private boolean valid = true;
	
	public Applicant(String name, int age) {
		this.age = age ;
		this.name = name ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String toString() {
		if(valid) {
			return name + " is valid applicant";
		} else {
			return name + " is invalid applicant";
		}
	}

}
