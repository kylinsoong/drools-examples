package com.sample.domain.conflict;

public class Customer {
    
    private String name;
    
    private int age;
    
    private boolean disabilities;
    
    private Room room;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDisabilities() {
        return disabilities;
    }

    public void setDisabilities(boolean disabilities) {
        this.disabilities = disabilities;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", age=" + age + ", disabilities=" + disabilities + ", room=" + room + "]";
    }

}
