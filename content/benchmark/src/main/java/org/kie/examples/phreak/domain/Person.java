package org.kie.examples.phreak.domain;

import java.io.Serializable;

/**
 * Sample fact describing a person.
 */
public class Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5249249840244756199L;
    private String name;

    public Person(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (17 * hash) + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
