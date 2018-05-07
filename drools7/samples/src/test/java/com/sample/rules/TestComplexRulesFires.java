package com.sample.rules;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.sample.domain.complex.Address;
import com.sample.domain.complex.Bus;
import com.sample.domain.complex.Cheese;
import com.sample.domain.complex.Company;
import com.sample.domain.complex.Customer;
import com.sample.domain.complex.DentalCare;
import com.sample.domain.complex.Employee;
import com.sample.domain.complex.HealthCare;
import com.sample.domain.complex.Mouse;
import com.sample.domain.complex.Observation;
import com.sample.domain.complex.Order;
import com.sample.domain.complex.OrderItem;
import com.sample.domain.complex.Person;

public class TestComplexRulesFires extends TestBase {

    public TestComplexRulesFires() {
        super("ksession-complexrules");
    }
    
    @Test
    public void testAnd() {
        Cheese cheese = new Cheese();
        cheese.setType("Cheddar");
        Person person = new Person();
        person.setFavoriteCheese("Cheddar");
        ksession.insert(person);
        ksession.insert(cheese);
        ksession.fireAllRules();
    }
    
    @Test
    public void testOr_1() {
        Person person = new Person();
        person.setFavoriteCheese("Swiss");
        ksession.insert(person);
        ksession.fireAllRules();
    }
    
    @Test
    public void testOr_2() {
        Person person = new Person();
        person.setFavoriteCheese("Cheddar");
        Mouse m = new Mouse();
        m.setFavoriteCheese("Swiss");
        ksession.insert(person);
        ksession.insert(m);
        ksession.fireAllRules();
    }
    
    @Test
    public void testEval() {
        Observation ob1 = new Observation();
        ob1.setVal(10);
        ob1.setPer(200);
        Observation ob2 = new Observation();
        ob2.setVal(20);
        ob2.setPer(120);
        ksession.insert(ob1);
        ksession.insert(ob2);
        ksession.fireAllRules();
    }
    
    @Test
    public void testExists() {
        String customerID = "CA10080";
        String orderID = "OR98002";
        Customer customer = new Customer();
        Order order = new Order();
        OrderItem item = new OrderItem();
        customer.setCustomerId(customerID);
        order.setCustomerId(customerID);
        order.setOrderId(orderID);
        item.setOrderId(orderID);
        item.setItemStatus("out_of_stock");
        ksession.insert(customer);
        ksession.insert(order);
        ksession.insert(item);
        ksession.fireAllRules();
        
        assertEquals(order.getStatus(), item.getItemStatus());
    }
    
    @Test
    public void testNot() {
        String customerID = "CA10080";
        String orderID = "OR98002";
        Customer customer = new Customer();
        Order order = new Order();
        customer.setCustomerId(customerID);
        order.setCustomerId(customerID);
        order.setOrderId(orderID);
        
        ksession.insert(customer);
        ksession.insert(order);
        ksession.fireAllRules();
        assertEquals(order.getStatus(), "items in stock");
    }
    
    @Test
    public void testFrom1() {
        Person person = new Person();
        Address address = new Address();
        address.setZipcode("100010");
        person.setAddress(address);
        ksession.insert(person);
        ksession.fireAllRules();
    }
    
    @Test
    public void testFrom2() {
        List<OrderItem> items = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setValue(120.0);
        OrderItem item2 = new OrderItem();
        item2.setValue(160.0);
        OrderItem item3 = new OrderItem();
        item3.setValue(87.0);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        Order order = new Order();
        order.setOrderId("OR100");
        ksession.insert(order);
        order.setItems(items);
        ksession.insert(order);
        ksession.fireAllRules();
        order.getItems().forEach(e -> {System.out.println(e.getValue());});
    } 
    
    @Test
    public void testForall() {
        ksession.insert(new Bus("english", "red"));
        ksession.insert(new Bus("english", "red"));
        ksession.fireAllRules();
    }
    
    @Test
    public void testForall_1() {
        ksession.insert(new Bus("english", "red"));
        ksession.insert(new Bus("english", "blue"));
        ksession.fireAllRules();
    }
    
    @Test
    public void testForall_2() {
        Employee employee = new Employee();
        HealthCare healthCare = new HealthCare();
        healthCare.setEmployee(employee);
        DentalCare dentalCare = new DentalCare();
        dentalCare.setEmployee(employee);
        
        Employee employee1 = new Employee();
        HealthCare healthCare1 = new HealthCare();
        healthCare1.setEmployee(employee1);
        DentalCare dentalCare1 = new DentalCare();
//        dentalCare1.setEmployee(employee1);
        
        ksession.insert(employee);
        ksession.insert(healthCare);
        ksession.insert(dentalCare);
        ksession.insert(employee1);
        ksession.insert(healthCare1);
        ksession.insert(dentalCare1);
        ksession.insert(new Bus("english", "red"));
        ksession.insert(new Bus("english", "blue"));
        
        ksession.fireAllRules();
    }
    
    @Test
    public void testForall_3() {
        Employee employee = new Employee();
        HealthCare healthCare = new HealthCare();
        healthCare.setEmployee(employee);
        DentalCare dentalCare = new DentalCare();
        dentalCare.setEmployee(employee);
        
        Employee employee1 = new Employee();
        HealthCare healthCare1 = new HealthCare();
        healthCare1.setEmployee(employee1);
        DentalCare dentalCare1 = new DentalCare();
        dentalCare1.setEmployee(employee1);
        
        ksession.insert(employee);
        ksession.insert(healthCare);
        ksession.insert(dentalCare);
        ksession.insert(employee1);
        ksession.insert(healthCare1);
        ksession.insert(dentalCare1);
        ksession.insert(new Bus("english", "red"));
        ksession.insert(new Bus("english", "blue"));
        
        ksession.fireAllRules();
    }
    
    @Test
    public void testCollect_1() {

        ksession.setGlobal( "results", new ArrayList<Cheese>());
        
        List<Cheese> list = new ArrayList<>();
        list.add(new Cheese("Cheddar"));
        list.add(new Cheese("Swiss"));
        list.add(new Cheese("stilton"));
        list.add(new Cheese("stilton"));
        
        ksession.insert(list);
        ksession.insert(list.get(0));
        ksession.insert(list.get(1));
        ksession.insert(list.get(2));
        ksession.insert(list.get(3));
        
        ksession.fireAllRules();
        @SuppressWarnings("unchecked")
        ArrayList<Cheese> results = (ArrayList<Cheese>) ksession.getGlobal("results");
        assertEquals(2, results.size());
    }
    
    @Test
    public void testCollect_2() {

        ksession.setGlobal( "results", new ArrayList<Cheese>());
        
        List<Cheese> list = new ArrayList<>();
        list.add(new Cheese("Cheddar"));
        list.add(new Cheese("Swiss"));
        list.add(new Cheese("stilton"));
        list.add(new Cheese("stilton"));
        
        Person person = new Person("Bob", "stilton");
        
        ksession.insert(list);
        ksession.insert(list.get(0));
        ksession.insert(list.get(1));
        ksession.insert(list.get(2));
        ksession.insert(list.get(3));
        ksession.insert(person);
        
        ksession.fireAllRules();
        @SuppressWarnings("unchecked")
        ArrayList<Cheese> results = (ArrayList<Cheese>) ksession.getGlobal("results");
        assertEquals(2, results.size());
    }
    
    @Test
    public void testCollect_3() {
        ksession.setGlobal( "results", new ArrayList<Person>());
        Company company = new Company();
        company.setName("Red Hat");
        
        List<Person> list = new LinkedList<>();
        list.add(new Person("Kim", "F", 2));
        list.add(new Person("Mary", "F", 2));
        list.add(new Person("Lily", "F", 2));
        list.add(new Person("Bob", "M", 2));
        list.add(new Person("Dang", "M", 2));
        company.setPeople(list);
        
        ksession.insert(company);

        ksession.fireAllRules();
        
        @SuppressWarnings("unchecked")
        ArrayList<Person> results = (ArrayList<Person>) ksession.getGlobal("results");
        assertEquals(1, results.size());
    }
    
    @Test
    public void testAccumulate_1() {
        Order order = new Order();
        OrderItem item1 = new OrderItem();
        item1.setValue(120.0);
        item1.setOrder(order);
        OrderItem item2 = new OrderItem();
        item2.setValue(160.0);
        item2.setOrder(order);
        OrderItem item3 = new OrderItem();
        item3.setValue(87.0);
        item3.setOrder(order);
        
        ksession.insert(order);
        ksession.insert(item1);
        ksession.insert(item2);
        ksession.insert(item3);

        ksession.fireAllRules();
    }
    
    @Test
    public void testAccumulate_2() {
        
    }
    
    @Test
    public void testAccumulate_3() {
        
    }

}
