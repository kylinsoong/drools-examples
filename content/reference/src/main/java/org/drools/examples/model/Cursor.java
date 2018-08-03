package org.drools.examples.model;

public class Cursor {

	private int cur = 0 ;
	
	public Cursor() {}
	
	public Cursor(int cur) {
		this.cur = cur;
	}

	public int getCur() {
		return cur;
	}

	public void setCur(int cur) {
		this.cur = cur;
	}

	@Override
	public String toString() {
		return "Cursor [" + cur + "]";
	}
	
	
}
