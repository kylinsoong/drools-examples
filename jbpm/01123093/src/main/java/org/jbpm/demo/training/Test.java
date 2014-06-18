package org.jbpm.demo.training;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		Map<String, User> users = new HashMap<String, User>();
		Map<String, Group> groups = new HashMap<String, Group>();
	        
		Reader reader = new InputStreamReader(JBPM5HumanTaskBasic.class.getResourceAsStream("/mvel/SampleUsers.mvel"));
		users = ( Map<String, User> ) TaskService.eval( reader, new HashMap());
		
		reader = new InputStreamReader(JBPM5HumanTaskBasic.class.getResourceAsStream("/mvel/SampleGroups.mvel"));
		groups = ( Map<String, Group> ) TaskService.eval( reader, new HashMap());
		
		System.out.println(users);
		System.out.println(groups);
		
	}

}
