package DAL;

import Entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAL {
    public static HashMap<String, Category> mapper = new HashMap<>();
    private static boolean updated = false;

    public static List<Category> loadMainCategories(){
        String sql = "select * from Category where ParentCategory is null";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            return resultSetToCategory(preparedStatement.executeQuery());
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void insertCategory(Category category){
        String sql = "INSERT INTO Category(Id, Name, ParentCategory) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, category.getID());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getParent() != null ? category.getParent().getID() : null);
            preparedStatement.executeUpdate();
            mapper.put(category.getID(), category);
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public static Category getCategoryById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "SELECT * FROM Category WHERE Id = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<Category> toReturn = resultSetToCategory(preparedStatement.executeQuery());
                if (toReturn.size() == 0)
                    return null;
                return toReturn.get(0);
            }
            catch (SQLException e){
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public static void loadCategorySubs(Category category){
        String sql = "SELECT * FROM Category WHERE ParentCategory = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, category.getID());
            category.setSubCategories(resultSetToCategory(preparedStatement.executeQuery()));
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public static List<Category> loadAll(){
        String sql = "SELECT * FROM Category;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            List<Category> toReturn = updated? new LinkedList<>(mapper.values()) : resultSetToCategory(preparedStatement.executeQuery());
            updated = true;
            return toReturn;
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static List<Category> resultSetToCategory(ResultSet resultSet) throws SQLException{
        List<Category> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String catId = resultSet.getString("Id");
            String name = resultSet.getString("Name");
            String categoryParentId = resultSet.getString("ParentCategory");
            if(mapper.containsKey(catId)){
                toReturn.add(mapper.get(catId));
            }
            else {
                Category parent = categoryParentId == null ? null : getCategoryById(categoryParentId);
                Category category = new Category(catId, name, parent);
                mapper.put(catId, category);
                toReturn.add(category);
            }
        }
        return toReturn;
    }

}
