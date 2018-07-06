package com.sample.domain.stocks;

import java.math.BigDecimal;

public class StockInstruction {

    public StockInstructionType command;
    
    public String symbol;
    
    public BigDecimal amount;
    
    public long milliseconds;
    
    public int lowVolume;
    
    public int highVolume;

    public StockInstruction(StockInstructionType command, String symbol, BigDecimal amount) {
        super();
        this.command = command;
        this.symbol = symbol;
        this.amount = amount;
    }
    
    public StockInstruction(StockInstructionType command, int seconds) {
        this.command = command;
        this.milliseconds = seconds * 1000;
    }

    public StockInstruction(StockInstructionType command, String symbol, BigDecimal amount, int seconds, String volumeRange) {
        super();
        this.command = command;
        this.symbol = symbol;
        this.amount = amount;
        this.milliseconds = seconds * 1000;
        String[] tokens = volumeRange.split("-");
        if(tokens.length != 2) {
            throw new IllegalArgumentException("Volume not specified correctly, must be separated by dash");
        }
        this.lowVolume = Integer.valueOf(tokens[0]);
        this.highVolume = Integer.valueOf(tokens[1]);
    }
}
