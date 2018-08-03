package org.drools.compiler.research;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<NameValue> nameValues = new ArrayList<NameValue>();

	public void add(String name, String value) {
		nameValues.add(new NameValue(name, value));
	}
	public List<NameValue> getNameValues() {
		return nameValues;
	}
}
