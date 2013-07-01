/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.test.lifecycle.workitem;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.jbpm.task.*;
import org.jbpm.task.service.ContentData;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;

public class SyncDeadlineEnabledWSHumanTaskHandler extends SyncWSHumanTaskHandler {

    private TaskService client;
    private WorkItemManager manager = null;
    
    private KnowledgeRuntime session;

    public SyncDeadlineEnabledWSHumanTaskHandler(TaskService client, KnowledgeRuntime session) {
        super(client, session);
        this.client = client;
        this.session = session;
    }

    
    
    public void setSession(KnowledgeRuntime session) {
        this.session = session;
    }

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        if (this.session == null) {
	    	if (this.manager == null) {
	            this.manager = manager;
	        } else {
	            if (this.manager != manager) {
	                throw new IllegalArgumentException(
	                        "This WSHumanTaskHandler can only be used for one WorkItemManager");
	            }
	        }
        }
        connect();
        Task task = new Task();
        String taskName = (String) workItem.getParameter("TaskName"); 
        if (taskName != null) {
            List<I18NText> names = new ArrayList<I18NText>();
            names.add(new I18NText("en-UK", taskName));
            task.setNames(names);
        }
        String comment = (String) workItem.getParameter("Comment");
        if (comment != null) {
            List<I18NText> descriptions = new ArrayList<I18NText>();
            descriptions.add(new I18NText("en-UK", comment));
            task.setDescriptions(descriptions);
            List<I18NText> subjects = new ArrayList<I18NText>();
            subjects.add(new I18NText("en-UK", comment));
            task.setSubjects(subjects);
        }
        String priorityString = (String) workItem.getParameter("Priority");
        int priority = 0;
        if (priorityString != null) {
            try {
                priority = new Integer(priorityString);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        task.setPriority(priority);

        TaskData taskData = new TaskData();
        taskData.setWorkItemId(workItem.getId());
        taskData.setProcessInstanceId(workItem.getProcessInstanceId());
        if(session != null && session.getProcessInstance(workItem.getProcessInstanceId()) != null) {
			taskData.setProcessId(session.getProcessInstance(workItem.getProcessInstanceId()).getProcess().getId());
		}
        if(session != null && (session instanceof StatefulKnowledgeSession)) { 
        	taskData.setProcessSessionId( ((StatefulKnowledgeSession) session).getId() );
        }
        taskData.setSkipable(!"false".equals(workItem.getParameter("Skippable")));
        //Sub Task Data
        Long parentId = (Long) workItem.getParameter("ParentId");
        if (parentId != null) {
            taskData.setParentId(parentId);
        }

        String subTaskStrategiesCommaSeparated = (String) workItem.getParameter("SubTaskStrategies");
        if (subTaskStrategiesCommaSeparated != null && !subTaskStrategiesCommaSeparated.equals("")) {
            String[] subTaskStrategies = subTaskStrategiesCommaSeparated.split(",");
            List<SubTasksStrategy> strategies = new ArrayList<SubTasksStrategy>();
            for (String subTaskStrategyString : subTaskStrategies) {
                SubTasksStrategy subTaskStrategy = SubTasksStrategyFactory.newStrategy(subTaskStrategyString);
                strategies.add(subTaskStrategy);
            }
            task.setSubTaskStrategies(strategies);
        }

        PeopleAssignments assignments = new PeopleAssignments();
        List<OrganizationalEntity> potentialOwners = new ArrayList<OrganizationalEntity>();

        String actorId = (String) workItem.getParameter("ActorId");
        if (actorId != null && actorId.trim().length() > 0) {
            String[] actorIds = actorId.split(",");
            for (String id : actorIds) {
                potentialOwners.add(new User(id.trim()));
            }
            //Set the first user as creator ID??? hmmm might be wrong
            if (potentialOwners.size() > 0) {
                taskData.setCreatedBy((User) potentialOwners.get(0));
            }
        }

        String groupId = (String) workItem.getParameter("GroupId");
        if (groupId != null && groupId.trim().length() > 0) {
            String[] groupIds = groupId.split(",");
            for (String id : groupIds) {
                potentialOwners.add(new Group(id.trim()));
            }
        }

        assignments.setPotentialOwners(potentialOwners);
        List<OrganizationalEntity> businessAdministrators = new ArrayList<OrganizationalEntity>();
        businessAdministrators.add(new User("Administrator"));
        assignments.setBusinessAdministrators(businessAdministrators);
        task.setPeopleAssignments(assignments);

        task.setTaskData(taskData);

        ContentData content = null;
        Object contentObject = workItem.getParameter("Content");
        if (contentObject == null) {
            contentObject = workItem.getParameters();
        }
        if (contentObject != null) {
            if (contentObject instanceof String){
                //DefaultEscalatedDeadlineHandler will try to create a String
                //from content.getContent() -> BUG in jBPM!
                content = new ContentData();
                content.setContent(((String)contentObject).getBytes());
                content.setAccessType(AccessType.Inline);
            }else{
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out;
                try {
                    out = new ObjectOutputStream(bos);
                    out.writeObject(contentObject);
                    out.close();
                    content = new ContentData();
                    content.setContent(bos.toByteArray());
                    content.setAccessType(AccessType.Inline);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //Deadlines
        String deadlineParametersPrefix = "Deadline_";

        //Is any deadline defined?
        Long deadlineTime = (Long) workItem.getParameter(deadlineParametersPrefix+"Time");
        List<Escalation> escalations = new ArrayList<Escalation>();
        if (deadlineTime != null){
            String deadlineNotificationRecipients = (String) workItem.getParameter(deadlineParametersPrefix+"Notification_Recipients");
            String deadlineReassignment = (String) workItem.getParameter(deadlineParametersPrefix+"Reassignment_Potential_Owners");
            
            if (deadlineReassignment == null && deadlineNotificationRecipients == null){
                throw new IllegalStateException("\""+taskName+" defines a Deadline time but it doesn't define any notification recipient or reassignment potential owner");
            }
            
            Deadline deadline = new Deadline();
            deadline.setEscalated(false);
            deadline.setDate(new Date(System.currentTimeMillis()+deadlineTime));
            
            Escalation escalation = new Escalation();
            
            escalation.setName(taskName+"_escalation_"+workItem.getId());
            
            //Reassignments 
            if (deadlineReassignment != null){
                List<Reassignment> reassignments = new ArrayList<Reassignment>();
                List<OrganizationalEntity> reassignmentsEntities = new ArrayList<OrganizationalEntity>();
                
                Reassignment reassignment = new Reassignment();
                String[] deadlineReassignments = deadlineReassignment.trim().split(",");
                for (String r : deadlineReassignments) {
                    reassignmentsEntities.add(new User(r.trim()));
                }
                reassignment.setPotentialOwners(reassignmentsEntities);
                reassignments.add(reassignment);
                escalation.setReassignments(reassignments);
            }
            
            //Notifications 
            if (deadlineNotificationRecipients != null){
                List<Notification> notifications = new ArrayList<Notification>();
                List<OrganizationalEntity> recipientsEntities = new ArrayList<OrganizationalEntity>();
                
                EmailNotification notification = new EmailNotification();
                
                //Recipients
                String[] notificationRecipients = deadlineNotificationRecipients.trim().split(",");
                for (String r : notificationRecipients) {
                    recipientsEntities.add(new User(r.trim()));
                }
                notification.setRecipients(recipientsEntities);
                
                //Business Administrators
                notification.setBusinessAdministrators(businessAdministrators);
                
                //Email Header
                Map<Language,EmailNotificationHeader> emailHeaders = new HashMap<Language, EmailNotificationHeader>();
                EmailNotificationHeader emailHeader = new EmailNotificationHeader();
                emailHeader.setLanguage("en-UK");
                emailHeader.setFrom("taskNotification@system.com");
                emailHeader.setSubject(taskName+" reaches its deadline");
                emailHeader.setBody(taskName+" reaches its deadline. Now it is your responsability!");
                emailHeaders.put(new Language("en-UK"), emailHeader);
                notification.setEmailHeaders(emailHeaders);
                
                notifications.add(notification);
                escalation.setNotifications(notifications);
                escalations.add(escalation);
            }
            
            Deadlines deadlines = new Deadlines();
            deadline.setEscalations(escalations);
            
            List<Deadline> dls = new ArrayList<Deadline>();
            dls.add(deadline);
            
            deadlines.setStartDeadlines(dls);
            task.setDeadlines(deadlines);
            
        }
        
        client.addTask(task, content);
    }

    
}
