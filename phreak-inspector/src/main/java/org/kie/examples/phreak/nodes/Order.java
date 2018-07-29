package org.kie.examples.phreak.nodes;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String id = "000";
    
    private String name = "order";
    
    private int total;
    
    private Customer customer;
    
    private List<OrderLine> orderLines = new ArrayList<>();

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order    [id=" + id + ", name=" + name + "]";
    }
}
