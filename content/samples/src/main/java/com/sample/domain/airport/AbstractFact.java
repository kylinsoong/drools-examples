package com.sample.domain.airport;

public class AbstractFact implements Fact {

    private static final long serialVersionUID = 4212662977868338498L;
    
    private final String id;
    
    public AbstractFact(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
