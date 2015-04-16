package com.kevin889.as_rs;

import java.sql.SQLException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Magazijn {

    public static void main(String[] args){
        try {
            SQLHandler sqlh = new SQLHandler("root", "root", "asrs", 3306, "localhost");
            new TourScreen(sqlh);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
