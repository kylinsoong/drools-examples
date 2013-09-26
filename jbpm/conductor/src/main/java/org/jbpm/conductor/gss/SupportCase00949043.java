package org.jbpm.conductor.gss;


import java.util.Properties;

import org.jbpm.conductor.orchestrator.JBPMHumanTaskService;
import org.jbpm.conductor.util.DataSourceHelper;
import org.jbpm.conductor.util.PropertyLoader;


public class SupportCase00949043 {

	public static void main(String[] args) throws Exception {
		
		Properties parameters = PropertyLoader.getProperties();
		
		DataSourceHelper.newInstance().setupDataSource(parameters);
		
		JBPMHumanTaskService humanTaskService = new JBPMHumanTaskService(parameters);
		humanTaskService.init();
		
		
	}
	


}
