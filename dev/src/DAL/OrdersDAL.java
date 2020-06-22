package DAL;

import Entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrdersDAL {
    public static HashMap<String, SingleProviderOrder> mapper = new HashMap<>();

    public static void insertOrder(SingleProviderOrder singleProviderOrder){
        String sql = "INSERT INTO SingleProviderOrder(OrderId, ProviderId, Date, OrderDays) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getOrderID());
            preparedStatement.setString(2, singleProviderOrder.getProvider().getProviderID());
            preparedStatement.setString(3, singleProviderOrder.getOrderDate() == null ? null : singleProviderOrder.getOrderDate().toString());
            preparedStatement.setInt(4, singleProviderOrder.getOrderDays());
            preparedStatement.executeUpdate();
            mapper.put(singleProviderOrder.getOrderID(), singleProviderOrder);
            for(Map.Entry<CatalogItem, Integer> entry: singleProviderOrder.getOrderItems().entrySet()){
                String newSql = "insert into CatalogToOrders(CatalogNumber, OrderId, Quantity) values (?,?,?);";
                PreparedStatement newPs = DBHandler.getConnection().prepareStatement(newSql);
                newPs.setString(1, entry.getKey().getCatalogNum());
                newPs.setString(2, singleProviderOrder.getOrderID());
                newPs.setInt(3, entry.getValue());
                newPs.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertItemToOrder(SingleProviderOrder order, CatalogItem item, int quantity){
        String sql = "INSERT INTO CatalogToOrders(CatalogNumber, OrderId, Quantity) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.getCatalogNum());
            preparedStatement.setString(2, order.getOrderID());
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editItemOnOrder(SingleProviderOrder order, CatalogItem item, int newQuantity){
        String sql = "update CatalogToOrders set Quantity = ? where OrderId = ? and CatalogNumber = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setString(2, order.getOrderID());
            preparedStatement.setString(3, item.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void removeItemFromOrder(SingleProviderOrder order, CatalogItem item){
        String sql = "delete from CatalogToOrders where OrderId = ? and CatalogNumber = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, order.getOrderID());
            preparedStatement.setString(2, item.getCatalogNum());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static SingleProviderOrder getOrderById(String orderId){
        if(mapper.containsKey(orderId)){
            return mapper.get(orderId);
        }
        else{
            String sql = "select * from SingleProviderOrder where SingleProviderOrder.OrderId = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, orderId);
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
        String sql = "select * from SingleProviderOrder;";
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
        String sql = "select * from SingleProviderOrder where SingleProviderOrder.ProviderId =  ?;";
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
    public static List<SingleProviderOrder> getOrdersToSupply(LocalDate localDate){
        String sql = "select * from SingleProviderOrder left join Provider P on SingleProviderOrder.ProviderId = P.ProviderId where SingleProviderOrder.OrderDays = 0 and SingleProviderOrder.Date = ? and P.NeedsTransport = 1;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, localDate.toString());
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
        String sql = "select * from SingleProviderOrder where OrderDays != 0;";
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
            String dateStr = resultSet.getString("Date");
            LocalDate date = dateStr == null ? null : LocalDate.parse(dateStr);
            int orderDays = resultSet.getInt("OrderDays");
            if(mapper.containsKey(orderId)){
                toReturn.add(mapper.get(orderId));
            }
            else{
                SingleProviderOrder singleProviderOrder = new SingleProviderOrder(ProviderDAL.getProviderById(providerId), orderId, date, orderDays);
                mapper.put(orderId, singleProviderOrder);
                toReturn.add(singleProviderOrder);
                addItemsToOrder(singleProviderOrder);
            }
        }
        return toReturn;
    }
    private static void addItemsToOrder(SingleProviderOrder singleProviderOrder){
        String sql = "select * from CatalogToOrders where OrderId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getOrderID());
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
