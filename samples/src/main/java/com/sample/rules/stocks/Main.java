package com.sample.rules.stocks;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        StockFeeder feed = new StockFeeder("src/main/resources/instructions.txt");
        feed.start();
    }

}
