package com.kevin889.as_rs.core;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Product {

    private String name;
    private int x, y ,size;
    private int connects;
    private boolean visited;

    public Product(){
        this(null, 0, 0, 0);
    }

    public Product(String name, int x, int y, int size) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
        this.connects = 0;
        this.visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() { return name; }

    public int getSize() { return size;}

    public void disconnect(){
        this.connects--;
    }

    public void connect(){
        this.connects++;
    }

    public int getConnections(){
        return this.connects;
    }

    public void resetConnection(){
        this.connects = 0;
    }

    public double getDistance(Product city){
        int xDist = Math.abs(getX() - city.getX());
        int yDist = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));

        return distance;
    }

    @Override
    public String toString() {
        return "["+ getName() +"][" + getX() + ", " + getY() + "][size=" + getSize() + "]";
    }

}
