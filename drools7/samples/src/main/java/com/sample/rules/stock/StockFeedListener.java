package com.sample.rules.stock;

import com.sample.domain.stocks.Tick;

public interface StockFeedListener {
    
    public void onTick(Tick tick);

}
