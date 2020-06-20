package com.company.DataAccessLayer;

import com.company.Entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DiscountDAL {
    public static HashMap<String, Discount> mapper = new HashMap<>();

    public static void insertDiscount(Discount discount){
        String sql = "INSERT INTO Discount(id, percentage, fromDate, toDate, retail) VALUES (?,?,?,?,true);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, discount.getId());
            preparedStatement.setDouble(2, discount.getPercentage());
            preparedStatement.setString(3, discount.getFromDate().toString());
            preparedStatement.setString(4, discount.getToDate().toString());
            preparedStatement.executeUpdate();
            mapper.put(discount.getId(), discount);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editItem(Discount discount){
        String sql = "update Discount set percentage = ?, fromDate = ?, toDate = ? where id = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, discount.getPercentage());
            preparedStatement.setString(2, discount.getFromDate().toString());
            preparedStatement.setString(3, discount.getToDate().toString());
            preparedStatement.setString(4, discount.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertDiscountRelation(Discount discount, Discountable discountable){
        String sql = "INSERT INTO DiscountsToDiscountables(DiscountId, DiscountableId, OnProduct) VALUES (?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, discount.getId());
            preparedStatement.setString(2, discountable.getId());
            preparedStatement.setInt(3, discountable instanceof ProductDetails ? 1 : 0);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Discount> getDisccountableDiscounts(Discountable discountable, boolean isProductDetails){
        String sql = "select * from DiscountsToDiscountables join Discount on Discount.id = DiscountsToDiscountables.DiscountId where (OnProduct = 1 and ? = true and DiscountableId = ?) or (OnProduct = 0 and ? = false and DiscountableId = ?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, isProductDetails);
            preparedStatement.setString(2, discountable.getId());
            preparedStatement.setBoolean(3, isProductDetails);
            preparedStatement.setString(4, discountable.getId());
            List<Discount> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Discount getDiscountById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "select * from Discount where id = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<Discount> resultList = resultSetToCategory(preparedStatement.executeQuery());
                if(resultList.size() == 0)
                    return null;
                return resultList.get(0);
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private static List<Discount> resultSetToCategory(ResultSet resultSet) throws SQLException{
        List<Discount> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String discountId = resultSet.getString("id");
            double percentage = resultSet.getDouble("percentage");
            LocalDate fromDate = LocalDate.parse(resultSet.getString("fromDate"));
            LocalDate toDate = LocalDate.parse(resultSet.getString("toDate"));
            if(mapper.containsKey(discountId)){
                toReturn.add(mapper.get(discountId));
            }
            else {
                Discount discount = new Discount(discountId, percentage, fromDate, toDate);
                mapper.put(discountId, discount);
                toReturn.add(discount);
            }
        }
        return toReturn;
    }

}
