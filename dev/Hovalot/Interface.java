public class Interface {
    private static DeliveryController controller=new DeliveryController();

    public static String viewDeliveries (){

     return controller.getInstance().toString();
    }
    public static boolean addDelivery(String date, String hour,String tid,String driverName,String source,int weightBeforeGo, String[] numberedFiles,String[] adresses, String[][] products ){
        return controller.getInstance().toString();
    }
}
