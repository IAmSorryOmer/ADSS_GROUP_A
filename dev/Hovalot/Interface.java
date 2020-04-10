import java.util.HashMap;
import java.util.List;

public class Interface {


    public static String viewDeliveries (){

        return DeliveryController.getInstance().toString();
    }
    public static void addDelivery(String date, String hour, String tid, String driverName, String source, int weightBeforeGo, List<String> numberedFiles, HashMap<String,String> adresses, HashMap<String,HashMap<String,Integer>> products ){
        Callback toCall=DeliveryController.getInstance().addDelivery(date,hour,tid,driverName,source,weightBeforeGo,adresses,numberedFiles,products);
        toCall.run(date,hour,tid,driverName,source,weightBeforeGo,adresses,numberedFiles,products);
    }
    public static void addDestination(String destname,String number, String contact, String area){
        DestController.getInstance().addDestination(new Destination(destname,contact,number,area));

    }
    public static void addTruck(String id, int weight, int maxweight, String model){
        TruckController.getInstance().addTruck(new Truck(id,weight,maxweight,model));
    }
    public static void addDriver(String name,License ls){
        DriverController.getInstance().addDriver(new Driver(ls,name));
    }
}