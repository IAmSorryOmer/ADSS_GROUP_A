package BL;

import DAL.DEmployee;
import DAL.Mapper;
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
    public String getDate(int day_num){
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

    public String  addDelivery(Mapper mapper, String date, String hour, String tid, String driverName, String source, int weightBeforeGo, List<String> numberedFiles,
                               HashMap<String,String> adresses, HashMap<String,HashMap<String,Integer>> products,String returnHour){

        if (morningOrEvening(hour).equals("morning"))
        {
            Day day = schedule.getDayByDate(date);
            if( day!= null)
            {
                if (schedule.getDayByDate(date).is_Assigned_To_Role("driver","morning")) {
                    if (chooseLower(hour,returnHour).equals(returnHour)) // return date is next day
                    {
                        if (schedule.returnIfDayIsLastInTheWeek(day.getDate()))
                            return "the return hour must be in the same week";
                        else
                        {
                            Day nextDay = schedule.getNextDay(date);
                            if (nextDay.is_Assigned_To_Role("storage",morningOrEvening(returnHour)))
                            {
                                return deliveryController.addDelivery(mapper, date, hour, tid, driverName, source, weightBeforeGo, numberedFiles, adresses, products, store_num, returnHour);
                            }
                            else
                            {
                                return "there must be an employee with role 'storage' at the shift of the return hour";
                            }
                        }

                    }
                    else
                    {
                        if (day.is_Assigned_To_Role("storage",morningOrEvening(returnHour)))
                        {
                            return deliveryController.addDelivery(mapper, date, hour, tid, driverName, source, weightBeforeGo, numberedFiles, adresses, products, store_num, returnHour);
                        }
                        else
                        {
                            return "there must be an employee with role 'storage' at the shift of the return hour";
                        }
                    }
                }
                else
                    return "there is no assigned driver for the shift of the delivery";
            }
            else
            {
                return "no day with that date in this week";
            }
        }

        else
        {
            Day day = schedule.getDayByDate(date);
            if( day!= null)
            {
                if (schedule.getDayByDate(date).is_Assigned_To_Role("driver","evening")) {
                    if (chooseLower(hour,returnHour).equals(returnHour)) // return date is next day
                    {
                        if (schedule.returnIfDayIsLastInTheWeek(day.getDate()))
                            return "the return hour must be in the same week";
                        else
                        {
                            Day nextDay = schedule.getNextDay(date);
                            if (nextDay.is_Assigned_To_Role("storage",morningOrEvening(returnHour)))
                            {
                                return deliveryController.addDelivery(mapper, date, hour, tid, driverName, source, weightBeforeGo, numberedFiles, adresses, products, store_num, returnHour);
                            }
                            else
                            {
                                return "there must be an employee with role 'storage' at the shift of the return hour";
                            }
                        }

                    }
                    else
                    {
                        if (day.is_Assigned_To_Role("storage",morningOrEvening(returnHour)))
                        {
                            return deliveryController.addDelivery(mapper, date, hour, tid, driverName, source, weightBeforeGo, numberedFiles, adresses, products, store_num, returnHour);
                        }
                        else
                        {
                            return "there must be an employee with role 'storage' at the shift of the return hour";
                        }
                    }
                }
                else
                    return "there is no assigned driver for the shift of the delivery";
            }
            else
            {
                return "no day with that date in this week";
            }
        }

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


    public void passDay(Mapper mapper) {

    }
}

