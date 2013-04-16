package org.jbpm.quickstarts.subprocess;

public class Account {
    private long money;
    private String name;

        // getters and setters omitted for brevity

    public Account(long money, String name) {
		super();
		this.money = money;
		this.name = name;
	}


	@Override
    public String toString() {
        return "[money=" + money + ", name=" + name + "]";
    }

    
    public void setName(String name) {
        this.name = name;
    }


    public void setMoney(long money) {
        this.money = money;
    }
    
    public String getName() {
        return this.name;
    }


    public long getMoney() {
    	return this.money;
    }
    
    

    /*
    public Account() {
    
    }
    */

}