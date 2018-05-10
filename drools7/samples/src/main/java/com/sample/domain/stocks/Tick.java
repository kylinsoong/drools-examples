package com.sample.domain.stocks;

import java.math.BigDecimal;
import java.util.Date;

public class Tick {

    private String symbol;
    
    private BigDecimal price;
    
    private long shares;
    
    private Date timestamp;

    public Tick() {
        super();
    }

    public Tick(String symbol, BigDecimal price, long shares, Date timestamp) {
        super();
        this.symbol = symbol;
        this.price = price;
        this.shares = shares;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
