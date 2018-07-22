package org.kie.examples.phreak.nodes;

public class Order {

    private String id = "000";
    
    private String name = "order";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order    [id=" + id + ", name=" + name + "]";
    }
}
