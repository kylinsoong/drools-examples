package com.sample.travel.domain;

import java.util.ArrayList;
import java.util.List;

public class AvailableRows {

    private List<Integer> rows = new ArrayList<>();

    public List<Integer> getRows() {
        return rows;
    }

    public void setRows(List<Integer> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "AvailableRows [rows=" + rows + "]";
    }
    
    
    
}
