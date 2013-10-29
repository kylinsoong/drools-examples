package org.jbpm.quickstarts.logging;

import java.util.logging.Logger;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

public class LoggingWorkItemHandler implements WorkItemHandler {
	
	private static final Logger logger = Logger.getLogger(LoggingWorkItemHandler.class.getName());
			
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		logger.info(workItem.toString());
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

}
