package org.kie.examples.phreak.nodes;

public class Customer {
    
    private int age;
    
    private Category category;
    
    public Customer() {}

    public Customer(int age, Category category) {
        super();
        this.age = age;
        this.category = category;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Customer [age=" + age + ", category=" + category + "]";
    }

}
