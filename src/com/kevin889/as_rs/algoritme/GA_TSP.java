package com.kevin889.as_rs.algoritme;

import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import com.kevin889.as_rs.visual.TourPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by kevin889 on 16-04-15.
 */
public class GA_TSP{

    private Order order;
    private TourPanel tp;

    private ArrayList<Product> finalRoute = new ArrayList<Product>();
    private Product start;
    private Product stop;

    public GA_TSP(Order order, TourPanel tp) {
        this.order = order;
        this.tp = tp;

        //De producten uit de order wordt gecopieerd naar een nieuwe arraylist
        ArrayList<Product> tour = new ArrayList<Product>();
        for(int i = 0; i < this.order.numberOfProducts(); i++){
            tour.add(order.getProduct(i));
        }

        //Laad alle producten
        for(int i = 0; i < this.order.numberOfProducts(); i++){
            if(i == 0){
                //Set het begin van de route
                finalRoute.add(order.getProduct(0));
                tour.remove(0);
            } else {
                ArrayList<Double> tempArr = new ArrayList<Double>();
                //Kijkt welke van de overgebleven producten het dichtst bij het huidige product ligt.
                for(int b = 0; b < tour.size(); b++){
                    tempArr.add(finalRoute.get(i - 1).getDistance(tour.get(b)));
                }
                int minIndex = tempArr.indexOf(Collections.min(tempArr));
                //System.out.println("Min:"+minIndex);
                finalRoute.add(tour.get(minIndex));
                tour.remove(minIndex);
            }
        }
        this.tp.setRoute(finalRoute);
    }

    /**
     * Geeft de uiteindelijke route in een arraylist
     * @return
     */
    public ArrayList<Product> getFinalRoute(){
        return finalRoute;
    }

}
