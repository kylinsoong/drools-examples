package org.jbpm.demo.approval.audit;

public class AuditDAOFactory {
	private static AuditDAO dao = null;
    
    public static AuditDAO defaultDAO() {
            if(null == dao) {
                    dao = new AuditDAOInfinispan();
            }
            return dao;
    }
}
