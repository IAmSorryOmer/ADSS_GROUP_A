package DAL;

import Entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgreementDAL {

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

    public static void loadProviderAgreement(Provider provider){
        String sql = "select * from AgreementItems where providerId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, provider.getProviderID());
            resultSetToAgreement(preparedStatement.executeQuery());
            if(!provider.getCommunicationDetails().isAgreement()) {
                provider.getCommunicationDetails().setAgreement(new Agreement(provider.getProviderID()));
            }
            provider.getCommunicationDetails().getAgreement().setUpdated(true);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void resultSetToAgreement(ResultSet resultSet) throws SQLException{
        while(resultSet.next()){
            String providerId = resultSet.getString("providerId");
            String catalogNum = resultSet.getString("itemId");
            int minAmount = resultSet.getInt("minAmount");
            double discount = resultSet.getDouble("discount");
            CatalogItem catalogItem = CatalogItemDAL.getCatalogItemByIdAndProvider(providerId, catalogNum);
            Provider provider = ProviderDAL.getProviderById(providerId);
            if(!provider.getCommunicationDetails().isAgreement()) {
                Agreement agreement = new Agreement(providerId);
                provider.getCommunicationDetails().setAgreement(agreement);
            }
            provider.getCommunicationDetails().getAgreement().getAgreementItems().put(catalogItem, new Pair<>(minAmount, discount));
        }
    }
}