package com.sample.domain.airport;

import java.util.Date;

public interface Event extends Fact {

    public abstract Date getTimestamp();
}
