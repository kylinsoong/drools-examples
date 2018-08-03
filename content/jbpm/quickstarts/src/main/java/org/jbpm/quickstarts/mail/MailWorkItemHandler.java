package org.jbpm.quickstarts.mail;

import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class MailWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Map<String, Object> map = workItem.getParameters();
		System.out.println("To: " + map.get("To"));
		System.out.println("From: " + map.get("From"));
		System.out.println("Subject: " + map.get("Subject"));
		System.out.println("Body: " + map.get("Body"));
		//TODO use javax.mail send email
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

}
