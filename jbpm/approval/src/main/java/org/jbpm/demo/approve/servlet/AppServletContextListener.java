package org.jbpm.demo.approve.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.jbpm.demo.approval.audit.AuditDAOFactory;
 
@WebListener
public class AppServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(AppServletContextListener.class);
	
	public void contextDestroyed(ServletContextEvent event) {
		
		logger.info(AuditDAOFactory.defaultDAO().destory());
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info(AuditDAOFactory.defaultDAO().ping());
	}

}
