package org.guvnor.test.project2;

import org.guvnor.test.project1.Project1;

public class Project2 {

	public static String getPrompt() {
		return "Prompt From Project2";
	}
	
	public static String getProjects() {
		return Project1.getPrompt();
	}

}
