package com.sample.rules.stock;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.sample.domain.stocks.StockInstruction;
import com.sample.domain.stocks.StockInstructionType;
import com.sample.domain.stocks.Tick;

public class StockFeeder {
    
    private List<StockFeedListener> listeners = new ArrayList<>();
    
    private String instructionFile;
    
    private List<StockInstruction> instructions = new ArrayList<>();
    
    private Map<String, BigDecimal> stocks = Collections.synchronizedMap(new HashMap<>());
    
    private AtomicInteger threadsActive = new AtomicInteger(0);
    
    public StockFeeder(String instructionFile) {
        this.instructionFile = instructionFile;
    }
    
    public void addListener(StockFeedListener listener) {
        listeners.add(listener);
    }
    
    public void start() {
        readInstructions();
        stocks.clear();
        instructions.forEach(i -> fireInstruction(i));
        while(!isFinished()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean isFinished() {
        return threadsActive.get() == 0 ? true : false;
    }

    private void fireInstruction(StockInstruction instruction) {
        
        switch(instruction.command) {
        case SET_PRICE:
            stocks.put(instruction.symbol, instruction.amount);
            publish(new Tick(instruction.symbol, instruction.amount, 100, new Date()));
            break;
        case INCREASE_PRICE:
        case DECREASE_PRICE:
            new Thread(new Generator(instruction)).start();
            threadsActive.getAndIncrement();
            break;
        case PAUSE:
            try {
                Thread.sleep(instruction.milliseconds);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            break;
        }
    }

    private void publish(Tick tick) {
        listeners.forEach(l -> l.onTick(tick));
    }

    private void readInstructions()  {

        this.instructions.clear();
//        FileInputStream fis = new FileInputStream(this.instructionFile);
//        try(BufferedReader br = new BufferedReader()) {
//            
//        }
        
    }
    
    private class Generator implements Runnable {
        
        long stopTime;
        StockInstruction instruction;
        
        Generator(StockInstruction instruction) {
            this.instruction = instruction;
            this.stopTime = System.currentTimeMillis() + instruction.milliseconds;
        }

        @Override
        public void run() {
            while(System.currentTimeMillis() < stopTime) {
                BigDecimal currentPrice = stocks.get(instruction.symbol);
                if(currentPrice == null) {
                    System.err.println("Cannot increment stock, " + instruction.symbol + ", no price set");
                }
                
                BigDecimal newPrice = (instruction.command == StockInstructionType.INCREASE_PRICE) ? currentPrice.add(instruction.amount) : currentPrice.subtract(instruction.amount);
                stocks.put(instruction.symbol, newPrice);
                int volume = new Random(System.currentTimeMillis()).nextInt(instruction.highVolume - instruction.lowVolume) + instruction.lowVolume;
                publish(new Tick(instruction.symbol, newPrice, volume, new Date()));
                try {
                    int sleepTime = new Random(System.currentTimeMillis()).nextInt(7000) + 3000;
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
                threadsActive.getAndDecrement();
            }
        }
        
    }
    
}
