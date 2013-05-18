package org.jbpm.demo.approve.audit;

import java.util.ArrayList;
import java.util.List;

public class AuditTest {
	
	public static void main(String[] args) {
		System.out.println(Util.timestamp());
		
		Audit audit = null;
		AuditDAO dao = AuditDAOFactory.defaultDAO();
		
		audit = new Audit();
		audit.setId(1L);
		audit.setAudit("Test 1");
		dao.addAudit(audit);
		
		audit = new Audit();
		audit.setId(1L);
		audit.setAudit("Test 2");
		dao.addAudit(audit);
		
		audit = new Audit();
		audit.setId(1L);
		audit.setAudit("Test 3");
		dao.addAudit(audit);
		
		audit = new Audit();
		audit.setId(1L);
		audit.setAudit("Test 3");
		dao.addAudit(audit);
		
		
		System.out.println(dao.getAudits());
	}

	public static List<Audit> generateTestAudits() {
		List<Audit> audits = new ArrayList<Audit>();
		
		
		Audit ticket1 = new Audit();
		ticket1.setId(1L);
		ticket1.setAudit("Ticket 1");
		Audit crisv = new Audit();
		crisv.setAudit(Util.timestamp() + "Ticket (id = 1) has been started by krisv");
		ticket1.addAudit(crisv);
		Audit cjohn = new Audit();
		cjohn.setAudit(Util.timestamp() + "Ticket (id = 1) has been approved by john");
		ticket1.addAudit(cjohn);
		Audit cmarry = new Audit();
		cmarry.setAudit(Util.timestamp() + "Ticket (id = 1) has been approved by mary");
		ticket1.addAudit(cmarry);
		Audit cf = new Audit();
		cf.setAudit(Util.timestamp() + "Ticket (id = 1) finished");
		ticket1.addAudit(cf);
		audits.add(ticket1);
		
		Audit ticket2 = new Audit();
		ticket2.setId(2L);
		ticket2.setAudit("Ticket 2");
		Audit jay = new Audit();
		jay.setAudit(Util.timestamp() + "Ticket (id = 2) has been started by jay");
		ticket2.addAudit(jay);
		Audit jjohn = new Audit();
		jjohn.setAudit(Util.timestamp() + "Ticket (id = 2) has been approved by john");
		ticket2.addAudit(jjohn);
		Audit jmarry = new Audit();
		jmarry.setAudit(Util.timestamp() + "Ticket (id = 2) has been approved by mary");
		ticket2.addAudit(jmarry);
		Audit cj = new Audit();
		cj.setAudit(Util.timestamp() + "Ticket (id = 2) finished");
		ticket2.addAudit(cj);
		audits.add(ticket2);
		
		Audit ticket3 = new Audit();
		ticket3.setId(3L);
		ticket3.setAudit("Ticket 3");
		Audit kylin = new Audit();
		kylin.setAudit(Util.timestamp() + "Ticket (id = 3) has been started by kylin");
		ticket3.addAudit(kylin);
		Audit kjohn = new Audit();
		kjohn.setAudit(Util.timestamp() + "Ticket (id = 3) has been approved by john");
		ticket3.addAudit(kjohn);
		Audit kmarry = new Audit();
		kmarry.setAudit(Util.timestamp() + "Ticket (id = 3) has been approved by mary");
		ticket3.addAudit(kmarry);
		Audit ck = new Audit();
		ck.setAudit(Util.timestamp() + "Ticket (id = 3) finished");
		ticket3.addAudit(ck);
		audits.add(ticket3);
		
		return audits;
	}
}
