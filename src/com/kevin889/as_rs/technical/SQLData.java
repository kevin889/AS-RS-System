package com.kevin889.as_rs.technical;

import com.kevin889.as_rs.core.Order;
import com.kevin889.as_rs.core.Product;
import com.kevin889.as_rs.visual.TourScreen;
import sun.java2d.pipe.TextPipe;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kevin889 on 17-04-15.
 */
public class SQLData {

    private SQLHandler sqlh;
    private XMLData xmld;
    private Order order;
    private TourScreen ts;
    private ProductsTableModel dtm;


    public SQLData(TourScreen ts) {
        this.ts = ts;
        this.sqlh = ts.getSQLHandler();
        this.xmld = ts.getXmlData();
        this.order = ts.getOrder();
        this.dtm = ts.getDtm();
    }

    /**
     * Hier worden de product id's uit het xml bestand gekoppeld aan een product met het zelfde id uit de database.
     * Deze producten worden dan als klasses aangemaakt
     */
    public void createProducts(){
        for(int i = 0; i < xmld.getProductNrs().size(); i++){
            int count = 0;
            try {
                ResultSet rs = sqlh.getData("*", "products", "id = " + xmld.getProductNrs().get(i));
                while (rs.next()) {
                    Product product = new Product(rs.getString("name"), new Integer(rs.getString("x")), new Integer(rs.getString("y")), new Integer(rs.getString("size")));
                    order.addProduct(product);
                    dtm.addRow(new Object[]{rs.getString("id"), rs.getString("name")});
                    count++;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            if(count == 0){
                System.out.println("[Error] Product met ID: "+xmld.getProductNrs().get(i)+" is niet gevonden in de database.");
                JOptionPane.showMessageDialog(ts,
                        "Product met ID: " + xmld.getProductNrs().get(i) + " is niet gevonden in de database, en is dus niet toegevoegd aan de order.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }


        }

    }
}
