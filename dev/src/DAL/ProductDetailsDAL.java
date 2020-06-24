package DAL;

import Entities.Category;
import Entities.ProductDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductDetailsDAL {
    public static HashMap<String, ProductDetails> mapper = new HashMap<>();

    public static void insertProductDetails(ProductDetails productDetails){
        String sql = "INSERT INTO ProductDetails(Id, Name, Manufacturer, RetailPrice, MinQuantity, Category, daysToExpiration) VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, productDetails.getId());
            preparedStatement.setString(2, productDetails.getName());
            preparedStatement.setString(3, productDetails.getManufacturer());
            preparedStatement.setDouble(4, productDetails.getRetailPrice());
            preparedStatement.setInt(5, productDetails.getMinimumQuantity());
            preparedStatement.setString(6, productDetails.getCategory().getID());
            preparedStatement.setInt(7, productDetails.getDaysToExpiration());
            preparedStatement.executeUpdate();
            mapper.put(productDetails.getId(), productDetails);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void editProductDetails(ProductDetails productDetails){
        String sql = "update ProductDetails set Name = ?, Manufacturer = ?, RetailPrice = ?, MinQuantity = ?, daysToExpiration = ? where Id = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, productDetails.getName());
            preparedStatement.setString(2, productDetails.getManufacturer());
            preparedStatement.setDouble(3, productDetails.getRetailPrice());
            preparedStatement.setInt(4, productDetails.getMinimumQuantity());
            preparedStatement.setInt(5, productDetails.getDaysToExpiration());
            preparedStatement.setString(6, productDetails.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static ProductDetails getProductDetailsById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "select * from ProductDetails where Id = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<ProductDetails> resultList = resultSetToCategory(preparedStatement.executeQuery());
                if(resultList.size() == 0)
                    return null;
                return resultList.get(0);
            }
            catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public static List<ProductDetails> getStoreMissingProducts(int storeId){
        String sql = "select ProductDetails.* from ProductDetails " +
                "left join Product P on ProductDetails.Id = P.type and P.StoreId = ? and P.IsDamaged = 0 " +
                "group by ProductDetails.Id having count(P.Id) < ProductDetails.MinQuantity;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<ProductDetails> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<ProductDetails> getProductDetailsByName(String name){
        String sql = "select * from ProductDetails where Name = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            List<ProductDetails> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public static List<ProductDetails> getStoreProductDetailsInStock(int storeId){
        String sql = "select ProductDetails.* from ProductDetails " +
                "left join Product P on ProductDetails.Id = P.type and P.StoreId = ? and P.IsDamaged = 0 " +
                "group by ProductDetails.Id having count(P.Id) > 0";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<ProductDetails> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static int getStoreProductDetailsQuantity(int storeId, String productDetailsId){
        String sql = "select count() as quantity from Product where StoreId = ? and type = ? and IsDamaged = 0;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            preparedStatement.setString(2, productDetailsId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("quantity");
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<ProductDetails> loadAll(){
        String sql = "select * from ProductDetails;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            List<ProductDetails> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static List<ProductDetails> resultSetToCategory(ResultSet resultSet) throws SQLException{
        List<ProductDetails> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String id = resultSet.getString("Id");
            String name = resultSet.getString("Name");
            String manufacturer = resultSet.getString("Manufacturer");
            double price = resultSet.getDouble("RetailPrice");
            int minQuantity = resultSet.getInt("MinQuantity");
            String categoryId = resultSet.getString("Category");
            int dayToExpire = resultSet.getInt("daysToExpiration");
            if(mapper.containsKey(id)){
                toReturn.add(mapper.get(id));
            }
            else {
                Category category = CategoryDAL.getCategoryById(categoryId);
                ProductDetails productDetails = new ProductDetails(id, name, manufacturer, price, dayToExpire, category, minQuantity);
                mapper.put(productDetails.getId(), productDetails);
                toReturn.add(productDetails);
            }
        }
        return toReturn;
    }

}
