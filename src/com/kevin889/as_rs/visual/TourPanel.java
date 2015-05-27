package com.kevin889.as_rs.visual;

import com.kevin889.as_rs.Magazijn;
import com.kevin889.as_rs.algoritme.GA_BPP;
import com.kevin889.as_rs.algoritme.GA_TSP;
import com.kevin889.as_rs.core.Bin;
import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import com.kevin889.as_rs.technical.ArduConnect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kevin889 on 16-04-15.
 */
public class TourPanel extends JPanel {

    private Order order;

    public static final int PWIDTH = 620;
    public static final int PHEIGHT = 500;

    private int xBox = 5;
    private int yBox = 5;

    private int boxWidth;
    private int boxHeight;

    private int padding = 10;
    private int lineHeight = 12;

    private GA_TSP tsp;
    private GA_BPP bpp;

    private ArrayList<Product> route = new ArrayList<Product>();

    /**
     * Maakt een teken panel aan, en berekent de dimensies aan de hand van de hoeveelheid vakken
     */
    public TourPanel(){
        setBackground(Color.BLACK);
        boxWidth = (PWIDTH - (2 * padding)) / xBox;
        boxHeight = (PHEIGHT - (2 * padding)) / yBox;
    }

    /**
     * Laad de order en repaint het panel bij het selecteren van een XML bestand
     * @param order
     */
    public void init(Order order){
        this.order = order;
        super.repaint();

        if(Magazijn.DEV_MODE) start();

    }

    /**
     * tekent het magazijn, de inhoud en de route.
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Set background to black
        g.setColor(Color.BLACK);
        g.fillRect(0,0,620,500);

        //Draw grid
        g.setColor(Color.green);
        for(int x = 0; x < xBox; x++){
            for(int y = 0; y < yBox; y++){
                g.drawRect(x * boxWidth + padding, y * boxHeight + padding, boxWidth, boxHeight);
            }
        }


        if(order == null) return;

        for(int i = 0; i < order.numberOfProducts(); i++){
            g.setColor(Color.WHITE);

            //Draw Product box
            int productX = order.getProduct(i).getX() * boxWidth + (padding * 2);
            //int productY = order.getProduct(i).getY() * boxHeight + (padding * 2);
            int productY = (PHEIGHT - order.getProduct(i).getY() * boxHeight) - boxHeight;
            int productWidth = boxWidth - (padding * 2);
            int productHeight = boxHeight - padding;
            g.fillRect(productX, productY, productWidth, productHeight);

            //Draw product info strings
            g.setColor(Color.BLACK);

            //Draw product name
            int productNameX = order.getProduct(i).getX() * boxWidth + (padding * 3);
            int productNameY = PHEIGHT - (order.getProduct(i).getY() * boxHeight) - boxHeight + (padding * 2);
            g.drawString(order.getProduct(i).getName(), productNameX, productNameY);

            //Draw product size
            String size;
            if(order.getProduct(i).getSize() == 1){
                size = "Klein";
            }else{
                size = "Groot";
            }
            int sizeX = order.getProduct(i).getX() * boxWidth + (padding * 3);
            int sizeY = PHEIGHT - (order.getProduct(i).getY() * boxHeight) - boxHeight + (padding * 2) + lineHeight;
            g.drawString(size, sizeX, sizeY);

            if(route == null) return;
            g.setColor(Color.RED);
            for(int c = 0; c < route.size(); c++){

                if(c < route.size() - 1) {
                    int x1 = (route.get(c).getX() * boxWidth) + (padding) + (boxWidth / 2);
                    int y1 = PHEIGHT - (route.get(c).getY() * boxHeight) - (boxHeight / 2);
                    int x2 = (route.get(c + 1).getX() * boxWidth) + (padding) + (boxWidth / 2);
                    int y2 = PHEIGHT - (route.get(c + 1).getY() * boxHeight) - (boxHeight / 2);
                    g.drawLine(x1, y1, x2, y2);
                }
            }

        }

    }

    /**
     * Start de algoritmes
     */
    public void start(){

        //Start TSP
        tsp = new GA_TSP(order, this);

        //Start BPP
        bpp = new GA_BPP(order, tsp.getFinalRoute());

        String total = "";

        //Genereert data om naar de arduino te sturen
        for(int i = 0; i < tsp.getFinalRoute().size(); i++){
            total += (tsp.getFinalRoute().get(i).getX()) + "," + (tsp.getFinalRoute().get(i).getY()) + "," + bpp.getPosition().get(i) + ";";
        }

        //Maakt een arduino connectie aan
        ArduConnect connection = new ArduConnect();
        if(connection.initialize()){
            //Verstuurt data naar arduino
            connection.sendData(total);
        }
        connection.close();

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException ie){
        }

        System.out.println(total);
    }

    /**
     * Stopt de algoritmes
     */
    public void stop(){

    }

    /**
     * De route wordt toegevoegd aan het tekenpanel
     * @param p
     */
    public void setRoute(ArrayList<Product> p){
        this.route = p;
        super.repaint();
    }


}
