package org.jbpm.conductor.test.orchestrator;

import java.util.Iterator;

import org.jbpm.task.Group;
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.UserInfo;

public class CustomUserInfo implements UserInfo {

	public String getDisplayName(OrganizationalEntity entity) {
		return null;
	}

	public Iterator<OrganizationalEntity> getMembersForGroup(Group group) {
		return null;
	}

	public boolean hasEmail(Group group) {
		return false;
	}

	public String getEmailForEntity(OrganizationalEntity entity) {
		return null;
	}

	public String getLanguageForEntity(OrganizationalEntity entity) {
		return null;
	}

}
