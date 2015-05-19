package com.kevin889.as_rs.core;

import java.util.Comparator;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Product{

    private String name;
    private int x, y ,size;

    public Product(String name, int x, int y, int size) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() { return name; }

    public int getSize() { return size;}

    public double getDistance(Product city){
        int xDist = Math.abs(getX() - city.getX());
        int yDist = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));

        return distance;
    }

    public double getDistance(int x, int y) {
        int xDist = Math.abs(getX() - x);
        int yDist = Math.abs(getY() - y);
        double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));

        return distance;
    }

}
