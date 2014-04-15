package org.jbpm.demo.rewards.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Audit implements Serializable, Comparable<Audit> {

	private static final long serialVersionUID = -4087405899687609543L;

	private Long id;
	
	private String audit;
	
	public Audit() {
		super();
	}

	public Audit(Long id, String audit) {
		super();
		this.id = id;
		this.audit = audit;
	}

	private List<Audit> audits = new ArrayList<Audit>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public List<Audit> getAudits() {
		return audits;
	}
	
	public void addAudit(Audit audit) {
		audits.add(audit);
	}

	public String toString() {
		return "Audit [id=" + id + ", audit=" + audit + ", audits=" + audits + "]";
	}

	public int compareTo(Audit audit) {
		
		return id.compareTo(audit.getId());
	}

	
}
