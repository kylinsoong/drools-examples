package com.sample.rules.stock;

import com.sample.domain.stocks.Tick;

public class LoggingStockFeedListener implements StockFeedListener {

    @Override
    public void onTick(Tick tick) {

        System.out.println(tick.getTimestamp() + ": " + tick.getSymbol() + ": " + tick.getShares() + "@" + tick.getPrice());
    }

}
