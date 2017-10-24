package org.drools.examples.model;

import java.util.Date;

public class AllocatedCashflow extends TypedCashflow {
	
	private Account account;
	
	public AllocatedCashflow() {
		
	}
	
	public AllocatedCashflow(Account account, Date date, double amount, int type) {
		super(date, amount, type);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String toString() {
        return "AllocatedCashflow[" + "account=" + account + ",date=" + getDate() +  ",type=" + (getType() == CREDIT ? "Credit" : "Debit") +  ",amount=" + getAmount() + "]";
}

}
