package com.kevin889.as_rs.core;

import java.util.ArrayList;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Order {

    private int orderNumber;
    private String date;
    private Customer customer;

    public Order(int orderNumber, String date, Customer customer){
        this.orderNumber = orderNumber;
        this.date = date;
        this.customer = customer;
        System.out.println("<"+this.date+"> Order: " + orderNumber + " created.");
    }

    private ArrayList<Product> products = new ArrayList<Product>();

    public void addProduct(Product product) { products.add(product); }

    public Product getProduct(int index){ return products.get(index); }

    public int getOrderNumber(){ return orderNumber; }

    public String getDate() { return date; }

    public int numberOfProducts() { return products.size(); }

    public ArrayList getTour(){ return products; }


}
