package org.drools.examples.model;

public class Account {
	
	private long   accountNo;
	
	private double balance = 0;
	
	public Account() {
		
	}
	
	public Account(long accountNo) {
		this.accountNo = accountNo;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", balance=" + balance + "]";
	}

}
