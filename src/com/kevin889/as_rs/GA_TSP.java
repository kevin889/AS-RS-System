package com.kevin889.as_rs;

/**
 * Created by kevin889 on 16-04-15.
 */
public class GA_TSP {

    private Order tm;

    public GA_TSP(Order tm) {
        this.tm = tm;
        System.out.println(tm.getTour());
    }
}
