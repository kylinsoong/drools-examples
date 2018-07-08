package org.kie.examples.phreak.domain;

import java.io.Serializable;

/**
 * A personal bank account with a balance.
 */
public class Account implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5981531505489463802L;

    private final long id;

    private final Person owner;

    private int balance = 0;

    private Category category = Category.REGULAR;

    public Account(final long id, final Person owner) {
        this.id = id;
        this.owner = owner;
    }

    public Person getOwner() {
        return this.owner;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Account id=%d, owner=%s, balance:=%d", this.id, this.owner.getName(), this.balance);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (29 * hash) + (int) (this.id ^ (this.id >>> 32));
        hash = (29 * hash) + this.owner.hashCode();
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!this.owner.equals(other.owner)) {
            return false;
        }
        return true;
    }

    /**
     * The account category.
     */
    public enum Category {
        REGULAR, BRONZE, SILVER, GOLD
    }
}
