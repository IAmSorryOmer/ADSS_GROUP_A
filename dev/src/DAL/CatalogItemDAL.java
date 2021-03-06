package DAL;

import Entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CatalogItemDAL {
    private static HashMap<String, CatalogItem> mapper = new HashMap<>();

    public static void insertItem(CatalogItem catalogItem){
        String sql = "INSERT INTO CatalogItem(CatalogNum, ProviderId, Price, ProductDetailsId) values (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, catalogItem.getCatalogNum());
            preparedStatement.setString(2, catalogItem.getProviderID());
            preparedStatement.setDouble(3, catalogItem.getPrice());
            preparedStatement.setString(4, catalogItem.getProductDetails().getId());
            preparedStatement.executeUpdate();
            mapper.put(catalogItem.getCatalogNum(), catalogItem);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public static void editItem(CatalogItem catalogItem){
        String sql = "update CatalogItem set Price = ? where CatalogNum = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, catalogItem.getPrice());
            preparedStatement.setString(2, catalogItem.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static CatalogItem getCatalogItemById(String catalogNum){
        if(mapper.containsKey(catalogNum)){
            return mapper.get(catalogNum);
        }
        else {
            String sql = "SELECT * FROM CatalogItem where CatalogNum = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, catalogNum);
                List<CatalogItem> toReturn = resultSetToItems(preparedStatement.executeQuery());
                if (toReturn.size() == 0)
                    return null;
                return toReturn.get(0);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public static CatalogItem getProviderCatalogItemByProductDetail(String providerId, String productDetailsId){
        String sql = "SELECT * FROM CatalogItem where ProviderId = ? and ProductDetailsId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, providerId);
            preparedStatement.setString(2, productDetailsId);
            List<CatalogItem> toReturn = resultSetToItems(preparedStatement.executeQuery());
            if (toReturn.size() == 0)
                return null;
            return toReturn.get(0);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void loadProviderItems(CommunicationDetails communicationDetails){
        String sql = "SELECT * FROM CatalogItem where ProviderId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, communicationDetails.getProvider().getProviderID());
            List<CatalogItem> toReturn = resultSetToItems(preparedStatement.executeQuery());
            communicationDetails.setCatalogItems(toReturn);
            communicationDetails.setUpdated(true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static List<CatalogItem> resultSetToItems(ResultSet resultSet) throws SQLException{
        List<CatalogItem> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String providerId = resultSet.getString("ProviderId");
            String catalogNum = resultSet.getString("CatalogNum");
            double price = resultSet.getDouble("Price");
            String productDetailsId = resultSet.getString("ProductDetailsId");
            ProductDetails productDetails = ProductDetailsDAL.getProductDetailsById(productDetailsId);
            if(mapper.containsKey(catalogNum)){
                toReturn.add(mapper.get(catalogNum));
            }
            else {
                CatalogItem catalogItem = new CatalogItem(providerId, catalogNum, price, productDetails);
                mapper.put(catalogNum, catalogItem);
                toReturn.add(catalogItem);
            }
        }
        return toReturn;
    }

}
