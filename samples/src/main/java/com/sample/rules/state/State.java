package com.sample.rules.state;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import static com.sample.rules.state.StateType.*;

public class State {
    
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    private String name;
    
    private int state;
    
    public State() {
        
    }
    
    public State(final String name) {
        this.name = name;
        this.state = NOTRUN;   
    }
    
    public String getName() {
        return this.name ;
    }

    public int getState() {
        return state;
    }

    public void setState(int newState) {
        final int oldState = this.state;
        this.state = newState;
        this.changes.firePropertyChange("state", oldState, newState);
    }
    
    public boolean inState(final String name, final int state) {
        return this.name.equals( name ) && this.state == state;
    }
    
    public String toString() {
        switch ( this.state ) {
        case NOTRUN:
            return this.name + "[" + "NOTRUN" + "]";
        case FINISHED:
        default:
            return this.name + "[" + "FINISHED" + "]";
        }
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener l) {
        this.changes.removePropertyChangeListener(l);
    }

}
