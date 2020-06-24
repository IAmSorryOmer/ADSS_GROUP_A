package BL;

import DAL.DEmployee;
import DAL.Mapper;
import Entities.Pair;
import Entities.SingleProviderOrder;
import IL.Callback;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Store {

    private List<Employee> employees;
    private WeekSchedule schedule;
    private int store_num;
    private  DeliveryController deliveryController;
    private boolean hasLoaded;


    public Store(int month,int day,int store_num, Mapper mapper)
    {
        this.store_num = store_num;
        this.employees = new LinkedList<>();
        this.schedule = new WeekSchedule();
      //  schedule.initializeDays(month,day,store_num,mapper);
        deliveryController = new DeliveryController();
        hasLoaded= false;
    }
    public void initializeDays(int month,int day,int store_num,Mapper mapper)
    {
        schedule.initializeDays(month,day,store_num,mapper);
    }
    public Store(int store_num)
    {
        this.store_num = store_num;
        this.employees = new LinkedList<>();
        this.schedule = new WeekSchedule();
        deliveryController = new DeliveryController();
        hasLoaded= true;
    }

    public void Load(DAL.Mapper mapper)
    {
        List<DAL.DEmployee> DEmployees = mapper.LoadEmployees(store_num);
        for (DEmployee dEmployee : DEmployees)
        {
            employees.add(new Employee(dEmployee));
        }

        schedule.load(store_num,mapper);

        deliveryController.Load(store_num,mapper);

        hasLoaded = true;

    }
    public LocalDate getDate(int day_num){
        return schedule.getDays()[day_num -1].getDate();
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

    public void setHasLoaded(boolean b)
    {
        hasLoaded = b;
    }


    public String chooseLower(String t, String k){
        if(t.charAt(0)>k.charAt(0)){
            return k;
        }
        if(t.charAt(0)<k.charAt(0)){
            return t;
        }
        if(t.charAt(1)>k.charAt(1)){
            return k;
        }
        if(t.charAt(1)<k.charAt(1)){
            return t;
        }
        if(t.charAt(3)>k.charAt(3)){
            return k;
        }
        if(t.charAt(3)<k.charAt(3)){
            return t;
        }
        if(t.charAt(4)>k.charAt(4)){
            return k;
        }
        if(t.charAt(4)<k.charAt(4)){
            return t;
        }
        return k;
    }

    public String  addDelivery(Mapper mapper, String date,  int weightBeforeGo, SingleProviderOrder order){

                               return deliveryController.addDelivery(mapper, schedule.getDayByDate(date).getDate().toString(),weightBeforeGo,order,store_num);

    }
    public void printAllTrucks(){
        deliveryController.printAllTrucks();
    }
    public static String morningOrEvening(String x){
        if(x.charAt(0)=='1'&&x.charAt(1)>'1'){
            return "evening";
        }
        if(x.charAt(0)=='2'){
            return "evening";
        }
        return "morning";
    }

    public String viewDeliveries (){
        return deliveryController.toString();
    }

    public void addDriver(String name, License license,int id){
        deliveryController.addDriver(license,name,id);
    }
    public  void addTruck(String id, int weight, int maxWeight, String model){
        deliveryController.addTruck(id,weight,maxWeight,model);
    }
    public void addDestination(String address, String contact, String phone, String area){
        DestController.getInstance().addDestination(address,contact,phone,area);
    }

    public boolean hasLoaded() {
        return  hasLoaded;
    }

    public Employee getEmployeeById(int id)
    {
        for (Employee employee : employees)
        {
            if (employee.getID() == id)
                return employee;
        }
        return null;
    }

    //=----------------------------------------------ass3


    public void passDay(Mapper mapper,LocalDate curr) {
        SingleProviderOrderController.handleAutomaticOrders(store_num);
        //TODO ask aviv what the fuck??
        //checkOrders(mapper,curr);
        schedule.passDay(mapper,curr);
    }

    public Pair<Integer[],LocalDate> findDateForOrder(){
        return  schedule.findDateForOrder();
    }

    public void checkOrders(Mapper mappe, LocalDate curr){
        List<SingleProviderOrder> orders=SingleProviderOrderController.getAllAutomaticOrdersToSupply(curr);

        for(int i=0; i<orders.size();++i){
            SingleProviderOrder order=orders.get(i);
            if(order.getStoreId()==store_num){

                Pair<Integer[], LocalDate> deliveryInfo=schedule.findDateForOrder();
                if(deliveryInfo==null){
                    List<String> alertHumanResource=new LinkedList<>();
                    alertHumanResource.add("One of the orders(id:"+order.getOrderID()+") didn't manage to register because there were no drivers/storage workers working in the same shift");

                }
                else{
                    String hour="";
                    String returnhour="";
                    if(deliveryInfo.first[0]==0){
                        hour="6:00";
                        returnhour="7:00";

                    }
                    else{
                        hour="22:00";
                        returnhour="23:00";



                    }
                    order.setDeliveryDate(deliveryInfo.second);
                    order.setDriverId(deliveryInfo.first[1]);
                    order.setShift(deliveryInfo.first[0]);




                    ;

                }
            }

        }



    }
}

