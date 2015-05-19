package com.kevin889.as_rs;

import com.kevin889.as_rs.technical.ArduConnect;
import com.kevin889.as_rs.technical.SQLHandler;
import com.kevin889.as_rs.visual.TourScreen;

import java.sql.SQLException;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Magazijn {

    public static boolean DEV_MODE = false;

    public static void main(String[] args) throws Exception{
        /*try {
            SQLHandler sqlh = new SQLHandler("root", "root", "asrs", 3306, "localhost");
            new TourScreen(sqlh);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        ArduConnect connection = new ArduConnect();
        if(connection.initialize()){
            connection.sendData("g");
        }
        connection.close();

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException ie){
        }

    }

}
