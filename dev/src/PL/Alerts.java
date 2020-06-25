package PL;
import BL.StoreController;
import Entities.Pair;
import IL.ManagerController;
import IL.OrdersInterface;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static PL.Main.*;

public class Alerts {

    public static void logisticManagerAlertMenu(int storeId){
        System.out.println("Orders which should have arrived today and waitings for approval:");
        printNumberedList(OrdersInterface.getNotShippedOrders(storeId, StoreController.current_date));
    }
    public static void humanResourceAlert(int storeId){
        System.out.println("Orders which doesnt have delivery date due to fails at assignning workers:");
        printNumberedList(OrdersInterface.getNotScheduledOrders(storeId));
    }
    public static void stockKeeperAlert(int storeId){
        System.out.println("Orders which arrived and need to be stored:");
        printNumberedList(OrdersInterface.getNotHandledOrders(storeId));
    }

    public static void accceptDelivery(int storeId){
        System.out.println("please insert the id of the order to accept(insert @print to print all orders to accept):");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getNotShippedOrders(storeId, StoreController.current_date));
            System.out.println("now insert the id:");
            orderId = reader.nextLine();
        }
        System.out.println("please insert the id of the truck that delivered the order(insert @print to print all trucks):");
        String truckId = reader.nextLine();
        if(truckId.equals("@print")){
            ManagerController.printAllTrucks(storeId);
            System.out.println("now insert the id:");
            truckId = reader.nextLine();
        }
        System.out.println("please insert the weight of the delivery:");
        int weight = Integer.parseInt(reader.nextLine());
        ManagerController.acceptDelivery(orderId, truckId, storeId, weight);
        System.out.println("delivery accepted successfully");
    }

    public static void rescheduleDelivery(int storeId){
        System.out.println("please insert the id of the order to reschedule(insert @print to print all orders that needing reschedule):");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getNotScheduledOrders(storeId));
            System.out.println("now insert the id:");
            orderId = reader.nextLine();
        }
        OrdersInterface.rescheduleOrder(orderId, storeId);
    }

    public static void acceptSupply(int storeId){
        System.out.println("insert the id of the order that you want to accept her supply(insert @print for print the relevant orders):");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getNotHandledOrders(storeId));
            System.out.println("now insert the id:");
            orderId = reader.nextLine();
        }
        System.out.println("the products in this order is:");
        System.out.println(OrdersInterface.stringifyOrderItemsToHandle(storeId, orderId));
        System.out.println("now start to insert the ids of the items that you want to modify in the order(due to damage or missings).\n" +
                "insert @stop when you done and you want to add the order to stock:");
        List<Pair<String, Integer>> toChange = new LinkedList<>();
        String itemId = "a";
        while (true){
            System.out.println("please insert item id(or @stop):");
            itemId = reader.nextLine();
            if(itemId.equals("@stop"))
                break;
            System.out.println("now insert the new amount:");
            int newAmount = Integer.parseInt(reader.nextLine());
            toChange.add(new Pair<>(itemId, newAmount));
        }
        OrdersInterface.modifyItemsBeforeAccept(orderId, storeId, toChange);
        OrdersInterface.acceptOrder(orderId, storeId);
        System.out.println("order accepted successfully");
    }
}
