package com.kevin889.as_rs.algoritme;

import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import com.kevin889.as_rs.technical.Way;
import com.kevin889.as_rs.visual.TourPanel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kevin889 on 16-04-15.
 */
public class GA_TSP{

    private Order order;
    private TourPanel tp;

    private ArrayList<Way> ways = new ArrayList<Way>();
    private ArrayList<Way> finalRoute = new ArrayList<Way>();
    private Product start;
    private Product stop;

    public GA_TSP(Order order, TourPanel tp) {
        this.order = order;
        this.tp = tp;
        //System.out.println(order.getTour());

        for(int i = 0; i < order.numberOfProducts(); i++){
            for(int b = i + 1; b < order.numberOfProducts(); b++){
                Way way = new Way(order.getProduct(i), order.getProduct(b));
                ways.add(way);
            }
        }

        Collections.sort(this.ways, Way.wayComparator);

        start = getLongestDistance(ways).getFrom();
        stop = getLongestDistance(ways).getTo();
        System.out.println("Start="+start+" | stop="+stop);

        Product current = start;

        for(int i = 0; i < order.numberOfProducts(); i++){
            System.out.println("product: "+i);
            for(int b = 0; b < ways.size(); b++){
                if(i == 0){
                    if(ways.get(b).getFrom().equals(current) && current.getConnections() == 0){
                        finalRoute.add(ways.get(b));
                        ways.get(b).getFrom().connect();
                        ways.get(b).getTo().connect();
                        start.connect();
                    }
                }
//                else{
//                    System.out.println("Volgende moet vanaf " + finalRoute.get(i - 1).getTo() + " komen. ("+finalRoute.get(finalRoute.size() - 1).getTo()+")");
//                    if(ways.get(b).getFrom().equals(finalRoute.get(i - 1).getTo())){
//                        System.out.println("JA");
//                        finalRoute.add(ways.get(b));
//                        ways.get(b).getFrom().connect();
//                        ways.get(b).getTo().connect();
//                    }
//                }
            }

        }

//        for(int i = 0; i < ways.size(); i++){
//            if(i == 0){
//                for(int b = 0; b < ways.size(); b++){
//                    if(ways.get(b).getFrom().equals(start) && start.getConnections() == 0 && ways.get(b).getTo().getConnections() == 0){
//                        System.out.println("FIRST");
//                        finalRoute.add(ways.get(b));
//                        ways.get(b).getFrom().connect();
//                        ways.get(b).getTo().connect();
//                        start.connect();
//                    }
//                }
//            }else if(finalRoute.size() < order.numberOfProducts()){
//                //TODO: Fix this shizzle!
//                for(int b = 0; b < ways.size(); b++){
//                    if(!ways.get(i).getTo().equals(ways.get(b).getFrom()) && !ways.get(i).getTo().equals(stop) && !ways.get(b).getFrom().equals(start) && ways.get(b).getFrom().getConnections() == 1){
//                        System.out.println("MIDDLE");
//                        finalRoute.add(ways.get(b));
//                        ways.get(b).getFrom().connect();
//                        ways.get(b).getTo().connect();
//                    }
//                }
//            } else {
//                for(int b = 0; b < ways.size(); b++){
//                    if(ways.get(b).getTo().equals(stop) && stop.getConnections() == 0 && ways.get(b).getTo().getConnections() == 0){
//                        System.out.println("LAST from:"+ways.get(b).getFrom().getName()+", to:"+stop);
//                        finalRoute.add(ways.get(b));
//                        ways.get(b).getFrom().connect();
//                        ways.get(b).getTo().connect();
//                        stop.connect();
//                    }
//                }
//                if(ways.get(i).getFrom().getConnections() < 2 && ways.get(i).getTo().getConnections() == 0){
//                    finalRoute.add(ways.get(i));
//                    ways.get(i).getFrom().connect();
//                    ways.get(i).getTo().connect();
//                }
//            }
//            Product equals = null;
//            if(!finalRoute.isEmpty()){
//                equals = finalRoute.get(finalRoute.size() - 1).getTo();
//            }else {
//                equals = start;
//            }
//            if(ways.get(i).getFrom().equals(equals)){
//                System.out.println(equals);
//                finalRoute.add(ways.get(i));
//                ways.get(i).getFrom().connect();
//                ways.get(i).getTo().connect();
//            }
//            if(ways.get(i).getFrom().getConnections() < 2 && ways.get(i).getTo().getConnections() == 0 &&!ways.get(i).getFrom().equals(start) && !ways.get(i).getTo().equals(stop)){
//                finalRoute.add(ways.get(i));
//                ways.get(i).getFrom().connect();
//                ways.get(i).getTo().connect();
//            }
//        }
//
//        int totalDistance = 0;
//        for(int i = 0; i < finalRoute.size(); i++){
//            //System.out.println(finalRoute.get(i));
//            if(i == finalRoute.size() - 1){
//                start = finalRoute.get(i).getFrom();
//                stop = finalRoute.get(i).getTo();
//                finalRoute.get(i).disconnect();
//                finalRoute.remove(i);
//                System.out.println("Start="+start.getName()+" Stop="+stop.getName());
//            }else {
//                System.out.println(finalRoute.get(i));
//                totalDistance += finalRoute.get(i).getDistance();
//            }
//        }
//        //System.out.println("Totale afstand="+totalDistance);
        tp.setRoute(ways);
    }

    private void resetConnections(){
        for(int i = 0; i < finalRoute.size(); i++){
            finalRoute.get(i).disconnect();
        }
    }

    public Way getLongestDistance(ArrayList<Way> route){
        double longest = 0;
        Way way = null;
        for(int i = 0; i < route.size(); i++){
            if(route.get(i).getDistance() > longest){
                way = route.get(i);
            }
        }
        return way;
    }
}
