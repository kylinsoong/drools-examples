package com.sample.domain.complex;

import java.util.List;

public class Company {
    
    private String name;
    
    private List<Person> people;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
    

}
