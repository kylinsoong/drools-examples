package org.kie.examples.phreak.domain;

import java.io.Serializable;

/**
 * A transaction representing transfer from <em>source</em> account to
 * <em>target</em> account.
 */
public class Transaction implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5235880904355609967L;

    private final Account source;

    private final Account target;

    private final long amount;

    public Transaction(final Account source, final Account target, final long amount) {
        if ((source == null) || (target == null)) {
            throw new IllegalArgumentException("source and target accounts must not be null");
        }

        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public Account getSource() {
        return this.source;
    }

    public Account getTarget() {
        return this.target;
    }

    public long getAmount() {
        return this.amount;
    }

    public boolean isRelatedToAccount(final Account account) {
        return this.source.equals(account) || this.target.equals(account);
    }

}
