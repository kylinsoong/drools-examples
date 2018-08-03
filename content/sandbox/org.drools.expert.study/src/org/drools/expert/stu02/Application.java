package org.drools.expert.stu02;

import java.util.Date;

public class Application {

	private Date dateApplied;
	
	private boolean valid = true;

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
