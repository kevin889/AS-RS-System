package com.kevin889.as_rs.technical;

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

    public Connection getConnection(){
        return connection;
    }

    public Statement getStatement(){
        return statement;
    }

    public Object getDatabaseOption(DatabaseOption option){
        switch (option){
            case DATABASE_NAME:return database;
            case HOST: return host;
            case PASSWORD: return password;
            case PORT: return port;
            case USERNAME: return username;
            default: return null;
        }
    }

    public enum DatabaseOption{ USERNAME, PASSWORD, HOST, DATABASE_NAME, PORT }

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
