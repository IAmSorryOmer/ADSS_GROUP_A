package DAL;

import Entities.Product;
import Entities.ProductDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductDAL {
    public static HashMap<String, Product> mapper = new HashMap<>();

    public static void insertProduct(Product product){
        String sql = "INSERT INTO Product(Id, Location, IsInStorage, IsDamaged, ExpirationDate, type, StoreId) values (?,?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getLocation());
            preparedStatement.setInt(3, product.isInStorage() ? 1 : 0);
            preparedStatement.setInt(4, product.isDamaged() ? 1 : 0);
            preparedStatement.setString(5, product.getExpirationDate().toString());
            preparedStatement.setString(6, product.getType().getId());
            preparedStatement.setInt(7, product.getStoreId());
            preparedStatement.executeUpdate();
            mapper.put(product.getId(), product);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void editProduct(Product product){
        String sql = "update Product set Location = ?, IsInStorage = ?, IsDamaged = ?, ExpirationDate = ? where Id = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, product.getLocation());
            preparedStatement.setInt(2, product.isInStorage() ? 1 : 0);
            preparedStatement.setInt(3, product.isDamaged() ? 1 : 0);
            preparedStatement.setString(4, product.getExpirationDate().toString());
            preparedStatement.setString(5, product.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Product getProductById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "select * from Product where Id = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<Product> resultList = resultSetToCategory(preparedStatement.executeQuery());
                if(resultList.size() == 0)
                    return null;
                return resultList.get(0);
            }
            catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }
    public static List<Product> getStoreProductsByType(String typeId, int storeId){
        String sql = "select * from Product where type = ? and StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, typeId);
            preparedStatement.setInt(2, storeId);
            List<Product> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public static List<Product> getStoreDamagedProducts(int storeId){
        String sql = "select * from Product where IsDamaged = 1 and StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<Product> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<Product> loadStoreAll(int storeId){
        String sql = "select * from Product where StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<Product> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static List<Product> resultSetToCategory(ResultSet resultSet) throws SQLException{
        List<Product> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String id = resultSet.getString("Id");
            int storeId = resultSet.getInt("StoreId");
            String location = resultSet.getString("Location");
            boolean inStorage = resultSet.getInt("IsInStorage") == 1;
            boolean isDamaged = resultSet.getInt("IsDamaged") == 1;
            LocalDate expireDate = LocalDate.parse(resultSet.getString("ExpirationDate"));
            String productDetailsId = resultSet.getString("type");
            if(mapper.containsKey(id)){
                toReturn.add(mapper.get(id));
            }
            else {
                ProductDetails typeObj = ProductDetailsDAL.getProductDetailsById(productDetailsId);
                Product product = new Product(id, storeId, location, inStorage, expireDate, isDamaged, typeObj);
                mapper.put(product.getId(), product);
                toReturn.add(product);
            }
        }
        return toReturn;
    }

}
