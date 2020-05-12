package DAL;

import java.util.List;
//package net.sqlitetutorial;

import java.sql.*;






public class Mapper {

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/zura.db";

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            String sql = "SELECT Zura FROM Mirai";

            try (Connection conn2 = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("zura"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Mapper ()
    {

    }

    public  List<DStore> LoadStores() {

    }

    public  List<DStore> LoadEmployees(int store_num) {
    }

    public  DDay[] loadDays(int sore_num) {

    }

    public  List<DRole_ID> loadRole_ID(String date, int store_num, int i) {
    }

    public  List<DDelivery> loadDeliveries(int store_num) {
    }

    public  List<DNumber_Adress> loadNumberAdress(int deliverId, String number) {
    }

    public  List<DNumberedFile> loadNumberedFiles(int id) {
    }

    public  List<DDestination> loadDestinations(int id) {
    }

    public  List<DTruck> getTrucks() {
    }

    public  List<DDriver> getDrivers(int store_num) {
    }
}
