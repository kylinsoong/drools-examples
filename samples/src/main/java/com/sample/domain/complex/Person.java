package com.sample.domain.complex;

public class Person {

    private String favoriteCheese;
    
    private Address address;
    
    private String name;
    
    private String likes;
    
    private String gender;
    
    private Integer children;
    
    public Person() {
        
    }

    public Person(String name, String likes) {
        super();
        this.name = name;
        this.likes = likes;
    }

    public Person(String name, String gender, Integer children) {
        super();
        this.name = name;
        this.gender = gender;
        this.children = children;
    }

    public String getFavoriteCheese() {
        return favoriteCheese;
    }

    public void setFavoriteCheese(String favoriteCheese) {
        this.favoriteCheese = favoriteCheese;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }
    
    
}
