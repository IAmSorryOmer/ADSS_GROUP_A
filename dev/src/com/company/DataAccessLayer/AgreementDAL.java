package com.company.DataAccessLayer;

import com.company.Entities.Agreement;
import com.company.Entities.CatalogItem;
import com.company.Entities.Pair;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AgreementDAL {
    private static HashMap<String, Agreement> agreementHashMap = new HashMap<>();

    public static void insertItem(CatalogItem catalogItem, double discount, int minAmount){
        String sql = "INSERT INTO AgreementItems(providerId, itemId, minAmount, discount) VALUES(?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, catalogItem.getProviderID());
            preparedStatement.setString(2, catalogItem.getCatalogNum());
            preparedStatement.setInt(3, minAmount);
            preparedStatement.setDouble(4, discount);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editItem(CatalogItem catalogItem, double discount, int minAmount){
        String sql = "update AgreementItems set minAmount = ?, discount = ? where providerId = ? and itemId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, minAmount);
            preparedStatement.setDouble(2, discount);
            preparedStatement.setString(3, catalogItem.getProviderID());
            preparedStatement.setString(4, catalogItem.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void removeItem(CatalogItem catalogItem){
        String sql = "delete from AgreementItems where providerId = ? and itemId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, catalogItem.getProviderID());
            preparedStatement.setString(2, catalogItem.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}