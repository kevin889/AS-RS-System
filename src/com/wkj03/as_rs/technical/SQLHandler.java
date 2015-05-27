package com.wkj03.as_rs.technical;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


/**
 * Created by kevin889 on 16-04-15.
 */
public class SQLHandler {

    public static final String driver = "com.mysql.jdbc.Driver";
    private Statement statement;
    private Connection connection;
    private String username, password, host, database;
    private int port;

    /**
     * De database connectie wordt aangemaakt
     * @param username
     * @param password
     * @param database
     * @param port
     * @param host
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public SQLHandler(String username, String password, String database, int port, String host)  throws SQLException, ClassNotFoundException {
        this.password = password;
        this.username = username;
        this.database = database;
        this.port = port;
        this.host = host;

        Class.forName(driver);
        connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);
        //connection = DriverManager.getConnection("jdbc:mysql://localhost/asrs", username, password);
        statement = connection.createStatement();
    }

    /**
     * Krijg de connectie
     * @return
     */
    public Connection getConnection(){
        return connection;
    }

    /**
     * Krijg het statement
     * @return
     */
    public Statement getStatement(){
        return statement;
    }

    /**
     * Vraag een query aan, en krijg de results uit de database
     * @param columns
     * @param table
     * @param where
     * @return
     * @throws SQLException
     */
    public ResultSet getData(String columns, String table, String where) throws SQLException{
        String sql = "SELECT " + columns + " FROM " + table + (where==null ? ";":" WHERE "+where+";");
        try{
            return getStatement().executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
