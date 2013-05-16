package org.jbpm.demo.rewards.ejb;

public interface ProcessRemote {

	public long startProcess(String recipient) throws Exception;
}
