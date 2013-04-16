package org.jbpm.quickstarts.subprocess;

import java.util.ArrayList;

public class RiskyAccounts {
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private String riskStatement = null;
    
    public RiskyAccounts() {
    	    	
    }
    
    public RiskyAccounts(String riskStatement) {
    	this.riskStatement = riskStatement;
    	
    }
    
    public String getRiskStatement() {
    	
    	return this.riskStatement;
    }
    
    public void add(Account acc) {
    	accounts.add(acc);
    }
    public void listRiskyAccounts() {
    	for (Account acc : accounts)
    		System.out.println(acc);
    }

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (Account acc : accounts) {
			sb.append("  " + acc + "");
		}
		
		return sb.toString();
	}
}