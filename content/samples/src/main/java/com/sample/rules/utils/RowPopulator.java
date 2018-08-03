package com.sample.rules.utils;

import com.sample.domain.travel.AvailableRows;

public class RowPopulator {

    public static AvailableRows populateRows(Integer start, Integer end) {
        AvailableRows rows = new AvailableRows();
        return addMoreRows(rows, start, end);
    }

    public static AvailableRows addMoreRows(AvailableRows rows, Integer start, Integer end) {
        for( int i = start ; i <= end ; i ++) {
            rows.getRows().add(i);
        }
        return rows;
    }
}
