package org.jbpm.demo.rewards.audit;

import java.util.List;

public interface AuditDAO {
	
	public abstract List<Audit> getAudits();
	
	public abstract List<Audit> getAudits(int pageNum, int pageSize);
	
	public abstract void addAudit(Audit audit);
	
	public abstract void addAudit(Long id, String msg);
	
	public abstract String destory();
	
	public abstract long size();
	
	public abstract String ping();
	
	public static class Factory {
		
		private static AuditDAO INSTANCE;
		
		static {
			try {
				INSTANCE = new AuditDAOInfinispan();
			} catch (Exception e) {
				throw new RuntimeException("Unable to instance AuditDAO", e);
			}
		}
		
		public static AuditDAO get() {
			return INSTANCE ;
		}
	}
}
