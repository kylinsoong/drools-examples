package com.sample.rules.stocks;

import com.sample.domain.stocks.Tick;

public interface StockFeedListener {
    
    public void onTick(Tick tick);

}
