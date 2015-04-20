package com.kevin889.as_rs.algoritme;

import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.technical.Way;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kevin889 on 16-04-15.
 */
public class GA_TSP{

    private Order order;

    //private Map<Product, Product, Integer> route = new HashMap<Product, Product, Integer>();
    private ArrayList<Way> ways = new ArrayList<Way>();

    public GA_TSP(Order order) {
        this.order = order;
        //System.out.println(order.getTour());

        for(int i = 0; i < order.numberOfProducts(); i++){
            for(int b = i + 1; b < order.numberOfProducts(); b++){
                Way way = new Way(order.getProduct(i), order.getProduct(b));
                ways.add(way);
            }
        }

        Collections.sort(this.ways, Way.wayComparator);
        System.out.println(ways);

    }


}
