package com.sample.travel.domain;

import java.math.BigDecimal;

public class Fee {
    
    private BigDecimal amount;

    public Fee(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Fee [amount=" + amount + "]";
    }

    
}
