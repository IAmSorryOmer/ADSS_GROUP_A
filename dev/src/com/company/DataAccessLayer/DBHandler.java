package com.company.DataAccessLayer;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DBHandler {
    public static Connection connection = null;
    public static void connect(){
        try{
            String url = "jdbc:sqlite:/home/assaf/Documents/GitHub/ADSS_GROUP_A/dev/src/com/company/DB/SuperLi";
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);
            connection = DriverManager.getConnection(url, sqLiteConfig.toProperties());
            System.out.println("opened database successfully");
        }
        catch (Exception e){

        }
    }

    public static ResultSet executeCommand(String command) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(command);
    }


    public static Connection getConnection(){
        return connection;
    }
}
