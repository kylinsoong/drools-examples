package org.jbpm.demo.approval.ejb;

import javax.ejb.Local;

@Local
public interface ProcessServiceLocal {
	public long startProcess(String recipient, String ticketname) throws Exception;
}
