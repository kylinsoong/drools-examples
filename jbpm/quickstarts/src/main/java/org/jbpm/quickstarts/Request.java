package org.jbpm.quickstarts;

public class Request {
	
	private String id;
	private String personId;
	private Integer amount;
	private boolean valid;
	private String invalidReason;
	private boolean canceled;

	public Request() {
		
	}
	
	public Request(String id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setInvalid(String reason) {
		this.valid = false;
		this.invalidReason = reason;
	}

	public boolean isValid() {
		return valid;
	}
	
	public void setValid() {
		this.valid = true;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Request) {
			return this.id.equals(((Request) o).id);
		}
		return false;
	}
	
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", personId=" + personId + ", amount="
				+ amount + ", valid=" + valid + ", invalidReason="
				+ invalidReason + ", canceled=" + canceled + "]";
	}

}
