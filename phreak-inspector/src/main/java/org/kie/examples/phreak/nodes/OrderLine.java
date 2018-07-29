package org.kie.examples.phreak.nodes;

public class OrderLine {

    private OrderItem item;
    
    private int quantity;

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }    
}
