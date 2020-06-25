package DAL;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DBHandler {
    public static Connection connection = null;
    public static void connect(){
        try{
            String url = "jdbc:sqlite:SuperLiUnified.db";
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);
            connection = DriverManager.getConnection(url, sqLiteConfig.toProperties());
            System.out.println("opened database successfully");
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
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
