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

        ArrayList<Product> tour = new ArrayList<Product>();
//        ArrayList<Double> distsArr = new ArrayList<Double>();
        for(int i = 0; i < this.order.numberOfProducts(); i++){
            tour.add(order.getProduct(i));
//             .add(order.getProduct(i).getDistance(0,0));
        }
//        int startIndex = distsArr.indexOf(Collections.max(distsArr));
//        finalRoute.add(order.getProduct(startIndex));
//        tour.remove(startIndex);

        for(int i = 0; i < this.order.numberOfProducts(); i++){
            if(i == 0){
                finalRoute.add(order.getProduct(0));
                tour.remove(0);
            } else {
                ArrayList<Double> tempArr = new ArrayList<Double>();
                for(int b = 0; b < tour.size(); b++){
                    tempArr.add(finalRoute.get(i - 1).getDistance(tour.get(b)));
                }
                int minIndex = tempArr.indexOf(Collections.min(tempArr));
                System.out.println("Min:"+minIndex);
                finalRoute.add(tour.get(minIndex));
                tour.remove(minIndex);
            }
        }
        this.tp.setRoute(finalRoute);
    }

    static ArrayList permute(ArrayList<Product> arr, int k){
        ArrayList<ArrayList> a = new ArrayList();
        for(int i = k; i < arr.size(); i++){
            if (k == arr.size() -1){
                System.out.println(Arrays.toString(arr.toArray()));
            }

            Collections.swap(arr, i, k);
            permute(arr, k+1);
            Collections.swap(arr, k, i);
            a.add(arr);
        }
        return a;
    }

}
