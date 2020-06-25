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
        String sql = "INSERT INTO SingleProviderOrder(OrderId, ProviderId, OrderDate, DeliveryDate, OrderDays, StoreId, DriverId, Shift, isShipped, hasArrived) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getOrderID());
            preparedStatement.setString(2, singleProviderOrder.getProvider().getProviderID());
            preparedStatement.setString(3, singleProviderOrder.getOrderDate() == null ? null : singleProviderOrder.getOrderDate().toString());
            preparedStatement.setString(4, singleProviderOrder.getDeliveryDate() == null ? null : singleProviderOrder.getDeliveryDate().toString());
            preparedStatement.setInt(5, singleProviderOrder.getOrderDays());
            preparedStatement.setInt(6, singleProviderOrder.getStoreId());
            preparedStatement.setInt(7, singleProviderOrder.getDriverId());
            preparedStatement.setInt(8, singleProviderOrder.getShift());
            preparedStatement.setInt(9, singleProviderOrder.isShipped() ? 1 : 0);
            preparedStatement.setInt(10, singleProviderOrder.hasArrived() ? 1 : 0);
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
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void editOrder(SingleProviderOrder singleProviderOrder){
        String sql = "update SingleProviderOrder set DeliveryDate = ?, DriverId = ?, Shift = ?, isShipped = ?, hasArrived = ? where OrderId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, singleProviderOrder.getDeliveryDate() == null ? null : singleProviderOrder.getDeliveryDate().toString());
            preparedStatement.setInt(2, singleProviderOrder.getDriverId());
            preparedStatement.setInt(3, singleProviderOrder.getShift());
            preparedStatement.setInt(4, singleProviderOrder.isShipped() ? 1 : 0);
            preparedStatement.setInt(5, singleProviderOrder.hasArrived() ? 1 : 0);
            preparedStatement.setString(6, singleProviderOrder.getOrderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
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
            throw new IllegalArgumentException(e.getMessage());
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
            throw new IllegalArgumentException(e.getMessage());
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
            throw new IllegalArgumentException(e.getMessage());
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
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }


    public static List<SingleProviderOrder> getOrdersOfStore(int storeId){
        String sql = "select * from SingleProviderOrder where SingleProviderOrder.StoreId =  ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getAutomaticOrdersOfStore(int storeId){
        //here only because i want to get only the ones who have a matching record in the automatic orders table
        String sql = "select * from SingleProviderOrder where OrderDays != 0 and StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getAutomaticOrdersOfStoreAndDay(int storeId, int day){
        String sql = "select * from SingleProviderOrder where OrderDays != 0 and StoreId = ? and (OrderDays & ?) != 0;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            preparedStatement.setInt(2, 1 << (day - 1));
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getNotScheduledOrders(int storeId){
        String sql = "select * from SingleProviderOrder where OrderDays = 0 and DeliveryDate is null and StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getNotShippedOrders(int storeId, LocalDate date){
        String sql = "select * from SingleProviderOrder where OrderDays = 0 and DeliveryDate = ? and StoreId = ? and IsShipped != 1;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, date.toString());
            preparedStatement.setInt(2, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getNotHandledOrders(int storeId){
        String sql = "select * from SingleProviderOrder where OrderDays = 0 and StoreId = ? and IsShipped = 1 and HasArrived != 1;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getAllProviderTransportedDeliveries(int storeId){
        String sql = "select SingleProviderOrder.* from SingleProviderOrder join Provider P on SingleProviderOrder.ProviderId = P.ProviderId " +
                "where OrderDays = 0 and StoreId = ? and IsShipped = 0 and P.NeedsTransport = 1;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getStoreOrdersOfProvider(int storeId, String providerId){
        String sql = "select * from SingleProviderOrder where SingleProviderOrder.ProviderId =  ? and SingleProviderOrder.StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, providerId);
            preparedStatement.setInt(2, storeId);
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<SingleProviderOrder> getOrdersToSupply(LocalDate localDate){
        String sql = "select * from SingleProviderOrder left join Provider P on SingleProviderOrder.ProviderId = P.ProviderId where SingleProviderOrder.OrderDays = 0 and SingleProviderOrder.OrderDate = ? and P.NeedsTransport = 1;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, localDate.toString());
            List<SingleProviderOrder> resultList = resultSetToOrders(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    private static List<SingleProviderOrder> resultSetToOrders(ResultSet resultSet) throws SQLException{
        List<SingleProviderOrder> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String orderId = resultSet.getString("OrderId");
            int storeId = resultSet.getInt("StoreId");
            int driverId = resultSet.getInt("DriverId");
            int shift = resultSet.getInt("Shift");
            String providerId = resultSet.getString("ProviderId");
            String orderDateStr = resultSet.getString("OrderDate");
            LocalDate orderDate = orderDateStr == null ? null : LocalDate.parse(orderDateStr);
            String deliveryDateStr = resultSet.getString("DeliveryDate");
            LocalDate deliveryDate = deliveryDateStr == null ? null : LocalDate.parse(deliveryDateStr);
            boolean isShipped = resultSet.getBoolean("IsShipped");
            boolean hasArrived = resultSet.getBoolean("HasArrived");
            int orderDays = resultSet.getInt("OrderDays");
            if(mapper.containsKey(orderId)){
                toReturn.add(mapper.get(orderId));
            }
            else{

                SingleProviderOrder singleProviderOrder = new SingleProviderOrder(ProviderDAL.getProviderById(providerId), storeId, driverId, shift, orderId, orderDate, deliveryDate, orderDays,isShipped, hasArrived);
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
                CatalogItem catalogItem = CatalogItemDAL.getCatalogItemById(catalogNum);
                singleProviderOrder.getOrderItems().put(catalogItem, quantity);
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public static void removeOrder(SingleProviderOrder order){
        if(mapper.containsKey(order.getOrderID())){
            mapper.remove(order.getOrderID());
        }
        String sql = "delete from SingleProviderOrder where OrderId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, order.getOrderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
