package com.wkj03.as_rs;

import com.wkj03.as_rs.technical.SQLHandler;
import com.wkj03.as_rs.visual.TourScreen;

import java.sql.SQLException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Magazijn {

    //DEV_MODE laadt automatisch een xml bestand en start vervolgens de algoritmes
    public static boolean DEV_MODE = false;

    public static void main(String[] args) throws Exception{
        try {
            //Connect met de mysql server
            SQLHandler sqlh = new SQLHandler("root", "root", "asrs", 3306, "localhost");
            //Start de applicatie
            new TourScreen(sqlh);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
