package com.company.DataAccessLayer;

import com.company.Entities.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrdersDAL {
    public static HashMap<Pair<String, String>, SingleProviderOrder> mapper = new HashMap<>();

    public static void insertOrder(SingleProviderOrder singleProviderOrder){
        String sql = "INSERT INTO SingleProviderOrder(OrderId, ProviderId, StoreId, Date) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getOrderID());
            preparedStatement.setString(2, singleProviderOrder.getProvider().getProviderID());
            preparedStatement.setInt(3, singleProviderOrder.getStoreID());
            preparedStatement.setString(4, singleProviderOrder.getOrderDate() == null ? null : singleProviderOrder.getOrderDate().toString());
            preparedStatement.executeUpdate();
            mapper.put(new Pair<>(singleProviderOrder.getProvider().getProviderID(), singleProviderOrder.getOrderID()), singleProviderOrder);
            for(Map.Entry<CatalogItem, Integer> entry: singleProviderOrder.getOrderItems().entrySet()){
                String newSql = "insert into CatalogToOrders(CatalogNumber, ProviderId, OrderId, Quantity) values (?,?,?,?);";
                PreparedStatement newPs = DBHandler.getConnection().prepareStatement(newSql);
                newPs.setString(1, entry.getKey().getCatalogNum());
                newPs.setString(2, entry.getKey().getProviderID());
                newPs.setString(3, singleProviderOrder.getOrderID());
                newPs.setInt(4, entry.getValue());
                newPs.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertAutomaticOrder(AutomaticOrder automaticOrder){
        String sql = "INSERT INTO AutomaticOrder(OrderId, StoreId OrderDays, ProviderId) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, automaticOrder.getOrderID());
            preparedStatement.setInt(2, automaticOrder.getStoreID());
            preparedStatement.setInt(3, automaticOrder.getOrderDays());
            preparedStatement.setString(4, automaticOrder.getProvider().getProviderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertItemToOrder(SingleProviderOrder order, CatalogItem item, int quantity){
        String sql = "INSERT INTO CatalogToOrders(CatalogNumber, ProviderId, OrderId, Quantity) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.getCatalogNum());
            preparedStatement.setString(2, order.getProvider().getProviderID());
            preparedStatement.setString(3, order.getOrderID());
            preparedStatement.setInt(4, quantity);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void editItemOnOrder(SingleProviderOrder order, CatalogItem item, int newQuantity){
        String sql = "update CatalogToOrders set Quantity = ? where ProviderId = ? and OrderId = ? and CatalogNumber = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setString(2, order.getProvider().getProviderID());
            preparedStatement.setString(3, order.getOrderID());
            preparedStatement.setString(4, item.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void removeItemFromOrder(SingleProviderOrder order, CatalogItem item){
        String sql = "delete from CatalogToOrders where ProviderId = ? and OrderId = ? and CatalogNumber = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, order.getProvider().getProviderID());
            preparedStatement.setString(2, order.getOrderID());
            preparedStatement.setString(3, item.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static SingleProviderOrder getOrderById(String orderId, String providerId){
        Pair<String, String> key = new Pair<>(providerId, orderId);
        if(mapper.containsKey(key)){
            return mapper.get(key);
        }
        else{
            String sql = "select * from SingleProviderOrder left join AutomaticOrder AO on SingleProviderOrder.OrderId = AO.OrderId and SingleProviderOrder.ProviderId = AO.ProviderId " +
                    "where SingleProviderOrder.ProviderId = ? and SingleProviderOrder.OrderId = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, providerId);
                preparedStatement.setString(2, orderId);
                List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
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


    public static List<SingleProviderOrder> loadAll(){
        String sql = "select * from SingleProviderOrder left join AutomaticOrder AO on SingleProviderOrder.OrderId = AO.OrderId and SingleProviderOrder.ProviderId = AO.ProviderId;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<SingleProviderOrder> getOrdersOfProvider(String providerId){
        String sql = "select * from SingleProviderOrder left join AutomaticOrder AO on SingleProviderOrder.OrderId = AO.OrderId and SingleProviderOrder.ProviderId = AO.ProviderId " +
                "where SingleProviderOrder.ProviderId =  ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, providerId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<SingleProviderOrder> getAutomaticOrders(){
        //here only because i want to get only the ones who have a matching record in the automatic orders table
        String sql = "select * from SingleProviderOrder join AutomaticOrder AO on SingleProviderOrder.OrderId = AO.OrderId and SingleProviderOrder.ProviderId = AO.ProviderId;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    private static List<SingleProviderOrder> resultSetToOrders(ResultSet resultSet) throws SQLException{
        List<SingleProviderOrder> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String orderId = resultSet.getString("OrderId");
            String providerId = resultSet.getString("ProviderId");
            //TODO-add StoreId to this ppl thing | remove it conpletely and do something not related to ppl;
            String dateStr = resultSet.getString("Date");
            LocalDate date = dateStr == null ? null : LocalDate.parse(dateStr);
            int orderDays = resultSet.getInt("OrderDays");
            SingleProviderOrder toEdit;
            Pair<String, String> key = new Pair<>(providerId,orderId);
            if(mapper.containsKey(key)){
                toEdit = mapper.get(key);
                toReturn.add(toEdit);
            }
            else {
                if(orderDays != 0){
                    toEdit = new AutomaticOrder(ProviderDAL.getProviderById(providerId), orderId, orderDays);
                }
                else {
                    toEdit = new SingleProviderOrder(ProviderDAL.getProviderById(providerId), orderId, date);
                }
                mapper.put(key, toEdit);
                toReturn.add(toEdit);
                addItemsToOrder(toEdit);
            }
        }
        return toReturn;
    }
    private static void addItemsToOrder(SingleProviderOrder singleProviderOrder){
        String sql = "select * from CatalogToOrders where ProviderId = ? and OrderId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getProvider().getProviderID());
            preparedStatement.setString(2, singleProviderOrder.getOrderID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String catalogNum = resultSet.getString("CatalogNumber");
                int quantity = resultSet.getInt("Quantity");
                CatalogItem catalogItem = CatalogItemDAL.getCatalogItemByIdAndProvider(singleProviderOrder.getProvider().getProviderID(), catalogNum);
                singleProviderOrder.getOrderItems().put(catalogItem, quantity);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
