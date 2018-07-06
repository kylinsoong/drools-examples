package com.sample.domain.airport;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BagTag implements Serializable {

    private static final long serialVersionUID = -3833287418475140594L;
    
    private final String id;
    
    public BagTag() {
        this(UUID.randomUUID().toString());
    }
    
    public BagTag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).toString();
    }

}
