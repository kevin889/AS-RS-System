package com.kevin889.as_rs.technical;

import com.kevin889.as_rs.core.Product;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by kevin889 on 20-04-15.
 */
public class Way  implements Comparable<Way>{

    private Product from;
    private Product to;
    private double distance;

    public Way(Product from, Product to) {
        this.from = from;
        this.to = to;
        this.distance = from.getDistance(to);
    }

    public Product getFrom() {
        return from;
    }

    public Product getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public void connect(){
        from.connect();;
        to.connect();
    }

    public void disconnect(){
        from.disconnect();
        to.disconnect();
    }

    @Override
    public String toString() {
        return "van " + from.getName() + "("+from.getConnections()+") naar: " + to.getName()+"("+to.getConnections()+") ["+distance+"]\n";
    }

    public static Comparator<Way> wayComparator = new Comparator<Way>() {
        @Override
        public int compare(Way way1, Way way2) {
            return Double.compare(way1.getDistance(), way2.getDistance());
        }
    };

    @Override
    public int compareTo(Way o) {
        return 0;
    }

}
