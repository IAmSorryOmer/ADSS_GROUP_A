package PL;

import BL.StoreController;
import BL.TruckController;
import IL.*;

import java.net.SocketTimeoutException;

import static PL.Main.*;

public class WatchMenues {
    public static void watchTrucks(int storeId){
        String[] options = new String[]{"watch all trucks", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(Main.reader.nextLine());
                switch (option){
                    case 1:
                        ManagerController.printAllTrucks(storeId);
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchDeliveries(int storeId){
        String[] options = new String[]{"watch all deliveries", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(Main.reader.nextLine());
                switch (option){
                    case 1:
                        Main.viewDeliveries(storeId);
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchEmployees(int storeId){
        String[] options = new String[]{"watch all employees", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(Main.reader.nextLine());
                switch (option){
                    case 1:
                        Main.watchEmployeeDetails(storeId);
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchShifts(int storeId){
        String[] options = new String[]{"watch all shifts", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(Main.reader.nextLine());
                switch (option){
                    case 1:
                        Main.watchShiftDetails(storeId);
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchProducts(int storeId){
        String[] options = new String[]{"watch all products of type", "watch all products in stock", "watch all missing products", "watch all damaged products", "watch all products", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(Main.reader.nextLine());
                switch (option){
                    case 1:
                        printAllStoreProductsOfType(storeId);
                        break;
                    case 2:
                        printNumberedList(ProductDetailsInterface.getStoreProductDetailsByStock(storeId));
                        break;
                    case 3:
                        printNumberedList(ProductDetailsInterface.getAllMissingsOfStore(storeId));
                    case 4:
                        printNumberedList(ProductInterface.getAllDamagedOfStore(storeId));
                        break;
                    case 5:
                        System.out.println(ProductInterface.stringifyStoreProducts(storeId));
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 6");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchOrders(int storeId){
        String[] options = new String[]{"print all store orders", "print all store automatic orders", "return to store manager menu"};
        while(true) {
            System.out.println("choose what you want to watch:");
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
                switch(option){
                    case 1:
                        printNumberedList(OrdersInterface.getAllOrdersOfStore(storeId));
                        break;
                    case 2:
                        printNumberedList(OrdersInterface.getAllAutomaticsOrdersOfStore(storeId));
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public static void watchReports(int storeId){
        String[] options = new String[]{"print all reports", "return to store manager menu"};
        while(true){
            System.out.println("choose what you want to watch:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        System.out.println(ReportInterface.stringifyStoreReports(storeId));
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 2");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }

}
