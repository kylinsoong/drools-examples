package org.jbpm.demo.approval.audit;

import java.util.List;

public interface AuditDAO {
	
	public abstract List<Audit> getAudits();
	
	public abstract List<Audit> getAudits(int pageNum, int pageSize);
	
	public abstract void addAudit(Audit audit);
	
	public abstract void addAudit(Long id, String msg);
	
	public abstract String destory();
	
	public abstract String ping();
	
}
