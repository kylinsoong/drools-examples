package com.sample.domain.complex;

public class OrderItem {

    private String orderId;
    
    private String itemStatus;
    
    private Double value;
    
    private Double cost;
    
    private Double price;
    
    private Order order;

    public OrderItem() {
        super();
    }

    

    public OrderItem(String orderId, Double value, Double cost, Double price, Order order) {
        super();
        this.orderId = orderId;
        this.value = value;
        this.cost = cost;
        this.price = price;
        this.order = order;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem [orderId=" + orderId + ", itemStatus=" + itemStatus + ", value=" + value + "]";
    }
}
