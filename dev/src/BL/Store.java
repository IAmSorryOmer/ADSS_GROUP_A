package BL;

import IL.Callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Store {

    private List<Employee> employees;
    private WeekSchedule schedule;
    private int store_num;
    private  DeliveryController deliveryController;
    private boolean hasLoaded;


    public Store(int store_num)
    {
        this.store_num = store_num;
        this.employees = new LinkedList<>();
        this.schedule = new WeekSchedule();

        deliveryController = new DeliveryController();
        deliveryController.Load(store_num);

        hasLoaded= false;
    }

    public void Load(DAL.Mapper mapper)
    {
        List<DAL.DStore> DEmployees = mapper.LoadEmployees(store_num);
        schedule = new WeekSchedule();
        schedule.load(store_num,mapper);

        deliveryController = new DeliveryController();
        deliveryController.Load(store_num,mapper);

    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public String addEmployeeToShift(int employeeId, int dayNum, String dayPart, String role) {
        return schedule.getDays()[dayNum-1].enterEmployeeToShift(dayPart,employeeId,role);
    }

    public int getStore_num() {
        return store_num;
    }

    public String addRoleToShift(int dayNum, String dayPart, String role, int amount) {
        return schedule.getDays()[dayNum-1].enterJobToShift(dayPart,role,amount);
    }

    public WeekSchedule getSchedule() {
        return schedule;
    }





    public Callback addDelivery(String date, String hour, String tid, String driverName, String source, int weightBeforeGo, List<String> numberedFiles, HashMap<String,String> adresses, HashMap<String,HashMap<String,Integer>> products ){
        Callback c=deliveryController.addDelivery(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
        return  c;
    }

//    public static String viewDeliveries (){
//        return DeliveryController.getInstance().toString();
//    }
//
    public void addDriver(String name, License license){
        deliveryController.addDriver(license,name);
    }
    public  void addTruck(String id, int weight, int maxWeight, String model){
        deliveryController.addTruck(id,weight,maxWeight,model);
    }
    public void addDestination(String address, String contact, String phone, String area){
        DestController.getInstance().addDestination(address,contact,phone,area);
    }
}
