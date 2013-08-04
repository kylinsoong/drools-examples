package org.jbpm.conductor.test;

import java.util.List;

import org.jbpm.task.service.UserGroupCallback;

public class CustomUserGroupCallbackImpl implements UserGroupCallback{

	public boolean existsUser(String userId) {
		return false;
	}

	public boolean existsGroup(String groupId) {
		return false;
	}

	public List<String> getGroupsForUser(String userId, List<String> groupIds, List<String> allExistingGroupIds) {
		return null;
	}

}
