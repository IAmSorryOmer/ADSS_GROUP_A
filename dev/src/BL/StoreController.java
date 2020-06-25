package BL;

import DAL.DDestination;
import DAL.DEmployee_Details;
import DAL.Mapper;
import Entities.SingleProviderOrder;
import IL.Callback;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StoreController {
    static List<Store> stores;
    static public int storeNumbers = 2;
    static private Mapper mapper;
    static private String sundayDate;

    public static LocalDate current_date;
    static int Day_In_Week = 1;

    public StoreController()
    {
        stores = new LinkedList<>();
        mapper = new Mapper();
        List<DDestination> dDestinations = mapper.loadDestinations();
        for (DDestination dDestination : dDestinations) {
            DestController.getInstance().addDestination(dDestination.getAddress(),dDestination.getContact(),dDestination.getPhone(),dDestination.getArea());
        }
    }





    public static String addDriverEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date,String license)
    {
        Store store = getStore(store_num);
        if (store != null)
        {
            License license1 = Validation.checkLicense(license);
            if (license1 != License.NoLicense)
            {
                String result;
                result = addEmployee(jobs, name, id, bankAccount, store_num, salary, employee_conditions, start_date);
                if (result.equals("success"))
                {
                    addDriver(store_num,id,name,license1,license);
                }
                return result;

            }
            else {
                return "license type doesn't exits";
            }
        }
        else
        {
            return "store doesn't exists";
        }
    }
    public static void addDriver(int store_num,int id,String name, License license,String LicenseName){
        Store store = getStore(store_num);
        store.addDriver(name,license,id);
        mapper.saveDriver(LicenseName,name,store_num,id);

    }
    public static String addEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date) {

        String result;
        Store store = getStore(store_num);
        if (store != null) {
            if(!store.hasLoaded())
               store.Load(mapper);
            result = Validation.checkAddingNewEmployee(jobs);
            if (result == "success") {
                store.getEmployees().add(new Employee(jobs, name, id, bankAccount, store_num, salary, employee_conditions, start_date));
                //
                //
                //
                mapper.saveEmployee(jobs,name,id,bankAccount,store_num,salary,employee_conditions,start_date,0);


            }
        }
        else {
            result = "store doesn't exists";
        }
        return  result;

    }


    public static  String updateEmployee(int store_num , String name, int id, String bankAccount, int salary, String employee_conditions) {
        Store store = getStore(store_num);
        String result;
        if (store != null) {
            if(!store.hasLoaded())
                store.Load(mapper);

            Employee employee = store.getEmployeeById(id);
            if (employee != null) {
//                result = Validation.checkAddingNewEmployee(jobs);
//                if (result == "success") {
                    employee.updateEmployee(name, bankAccount, employee.getStore_num(), salary, employee_conditions);

                    int has = 0;
                    if (employee.getHasAssignedShifts())
                        has = 1;

                    mapper.updateEmployee(name,id,bankAccount,salary,employee_conditions);
                    return "success";

            }
            else {
                return "there is no employee with this id";
            }
        }
        else {
            result = "store doesn't exists";
        }
        return  result;

    }

    public static  String addToShift(int store_num, int employeeId, int dayNum, String dayPart, String role) {
        String result;
        Store store = getStore(store_num);

        if (store != null) {
            if(!store.hasLoaded())
                store.Load(mapper);

            Employee employee = store.getEmployeeById(employeeId);
            if (employee != null) {
                result = Validation.checkCabaleShifts(employee.getCapable_shifts(), dayNum, dayPart, employee.getCapable_jobs(), role);
                if (result.equals("success")) {
                    result = store.addEmployeeToShift(employeeId, dayNum, dayPart, role);
                    if (result.equals("success"))
                    {
                        int dayPartInt=0;
                        if (dayPart.equals("morning"))
                                dayPartInt = 0;
                        if (dayPart.equals("evening"))
                            dayPartInt = 1;
                        if(role.equals("shift manager")) {
                            LocalDate date = store.getSchedule().getDays()[dayNum -1].getDate();
                            mapper.saveShiftManger(date.toString(),role,dayPartInt,dayNum-1,employeeId,0,store_num);

                        }
                        else
                            mapper.saveAssignments_To_Shifts(store.getDate(dayNum).toString(),role,dayPartInt,dayNum-1,employeeId,0,store_num);

                    }

                }
            }
            else {
                result = "employee id doesn't exists";
            }
        }
        else {
            result = "store doesn't exists";
        }
        return  result;
    }

    public static String addRoleToShift(int store_num, int dayNum, String dayPart, String role, int amount)
    {
        String result;
        Store store = getStore(store_num);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);

            result = store.addRoleToShift(dayNum,dayPart,role,amount);
            if (result.equals("success"))
            {
                //
                //
                //DALLLLL
            //    mapper.save
                LocalDate date = store.getSchedule().getDays()[dayNum -1].getDate();
                mapper.saveRoleInShift(role,date.toString(),dayPart,store_num,amount);
            }
        }
        else {
            result="Store doesn't exist!";
        }
        return result;
    }

    /*
    public List<List<Employee>> getAllEmployees()
    {
        List<List<Employee>> employees = new LinkedList<>();
        for (Store s: stores) {

            if(s!=null&&!s.isHasLoaded()){
                s.Load();
            }
            employees.add(s.getEmployees());
        }
        return employees;
    } */


    //LOADS ALL THE STORES -------
    public static String getEmployeesDetails()
    {
        String str = "";
        List<DEmployee_Details> dEmployee_details = mapper.loadEmployeesDetails();

        for (DEmployee_Details employee_details : dEmployee_details)
        {
                if (employee_details.getId() != 0) {
                    str += "Store Number- " + employee_details.getStore_num() + " Employee Name: " + employee_details.getName() +
                            " Employee ID: " + employee_details.getId() + "\n";
                }
        }

        return str;
    }

    public static String getShiftsDetails(int store_num)
    {
        Store store = getStore(store_num);
        String str ="";

        if (store != null)
        {
            if (!store.hasLoaded())
                store.Load(mapper);

            for (int j =0; j < 7; j++)
            {
                Day day = store.getSchedule().getDays()[j];
                str += "day "  + (j+1) + " ----- \n";
                str += "morning: \n";
                for (String role : day.getMorning().keySet()) {
                    str += role + ": ";

                    for (int i = 0; i < day.getMorning().get(role).length; i ++)
                    {
                        if (day.getMorning().get(role)[i] != 0)
                            str += day.getMorning().get(role)[i] + ", ";
                    }
                    str += "\n";

                }
                str += "\n";
                str += "evening: \n";
                for (String role : day.getEvening().keySet()) {
                    str += role + ": ";

                    for (int i = 0; i < day.getEvening().get(role).length; i ++)
                    {
                        if (day.getEvening().get(role)[i] != 0)
                            str += day.getEvening().get(role)[i] + ", ";
                    }
                    str+= "\n";
                }

            }
        }
        else {
            str = "store doesn't exists";
        }
        return str;
    }

    public static String getCapableShiftsByEmployees(int store_num)
    {
        Store store = getStore(store_num);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);

            String val = "";
            for (Employee employee : getStore(store_num).getEmployees()) {
                String roles = "";
                for (int i = 0; i < employee.getCapable_jobs().length; i++)
                    roles += employee.getCapable_jobs()[i] + ", ";
                val += "Employee: " + employee.getName() + ", ID: " + employee.getID() + ", Roles: " + roles + " Available for: \n";
                for (int i = 0; i < 7; i++) {
                    if (employee.getCapable_shifts()[i].contains("morningevening")) {
                        val += "day " + (i + 1) + " morning,evening\n";
                    } else {
                        if (employee.getCapable_shifts()[i].contains("morning")) {
                            val += "day " + (i + 1) + " morning\n";
                        } else {
                            if (employee.getCapable_shifts()[i].contains("evening"))
                                val += "day " + (i + 1) + " evening\n";
                        }
                    }
                }
            }
            return val;
        }
        return "store doesn't exists";
    }
    /*public Employee getEmployeeById(int id)
    {

        for (Store store : stores)
        {

            if(store!=null&&!store.isHasLoaded()){
                store.Load();
            }

            for (Employee e : store.getEmployees()) {
                if (e.getID() == id)
                    return e;
            }
        }
        return null;
    } */

    public Boolean isEmployeeExists(int id)
    {
    /*    for (List<Employee> employees : getAllEmployees())
        {
            if (!employees.isEmpty()) {
                for (Employee employee : employees)
                {
                    if (employee.getID() == id)
                        return true;
                }
            }
        }*/
        return  false;
    }



    public static String removeEmployee(int id)
    {

        List<String> roles = mapper.loadRolesByID(id);
        if (roles.contains("shift manager"))
        {
            mapper.updateShiftManagerID(id);
        }

            mapper.removeEmployee(id);
            return "success";

    }


    public static String addDelivery(int weightBeforeGo, String tid,String orderId,int store_num)
    {
        SingleProviderOrder singleProviderOrder = SingleProviderOrderController.getOrderById(orderId);
        if(singleProviderOrder == null || singleProviderOrder.getStoreId() != store_num){
            throw new IllegalArgumentException("there is no order with id " + orderId + " for store number " + store_num);
        }
        Store store = getStore(store_num);
        if (store != null) {
           String result =  store.addDelivery(mapper,tid,weightBeforeGo,singleProviderOrder);
            return result;
        }
        else
        {
            return "store doesn't exists";
        }

    }

    public static Store getStore(int store_num)
    {

        for (int i = 0; i < storeNumbers; i++) {

            if (stores.get(i).getStore_num() == store_num) {
                return stores.get(i);
            }
        }
        return null;
    }

    public static void saveCapableShifts(Employee employee)
    {
        for (int i=0; i< 7; i++) {
            if (employee.getCapable_shifts()[i].contains("morning"))
             mapper.saveCapableShifts(i,"morning",employee.getID());
            if (employee.getCapable_shifts()[i].contains("evening"))
                mapper.saveCapableShifts(i,"evening",employee.getID());
        }
        mapper.setAssignedCapableShifts(employee.getID());

    }

    public static Store returnStoreIfEmployeeExists(int storeNum, int employeeId)
    {
        Store store = getStore(storeNum);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);
            Employee employee = store.getEmployeeById(employeeId);
            if (employee != null)
                return store;
            else
                return null;
        }
        else
        {
            return null;
        }
    }



    public static String getEmployeeDetails(int store_num, int id) {
        Store store = getStore(store_num);
        if (store != null) {
            if(!store.hasLoaded())
                store.Load(mapper);

            for (Employee employee : store.getEmployees()) {
                if (employee.getID() == id)
                    return employee.toString();
            }

        }
        return "there is no employee with this id";

    }

//    public void setStores(List<Store> stores) {
//        this.stores = stores;
//    }
//
//    public void setStoreNumbers(int storeNumbers) {
//        this.storeNumbers = storeNumbers;
//    }



    public static String addTruck(int store_num,String id, int weight, int maxWeight, String model){
        Store store = getStore(store_num);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);

            store.addTruck(id,weight,maxWeight,model);
            mapper.saveTruck(id,weight,maxWeight,model,store_num);
            return "success";
        }
        return "store doesn't exists";
    }


    public static void printAllTrucks(int id){
        Store store = getStore(id);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);
        }

        System.out.println( "store doesn't exists");
    }
    public static String viewDeliveries (int store_num){
        Store store = getStore(store_num);
        if (store != null) {
            if (!store.hasLoaded())
                store.Load(mapper);
            return store.viewDeliveries();
        }
        return "store doesn't exists";
    }


    public static void initializeStores(int month, int day,int storeNumber) {
        current_date = LocalDate.of(2020, month, day);
        mapper.saveCurrent_Date(current_date.toString());
        for (int i =1; i <= storeNumber; i++)
        {
            Store store = new Store(i,mapper);
            mapper.saveStore(i);
            stores.add(store);
            store.initializeDays(month,day,i,mapper);
            store.setHasLoaded(true);
        }

    }
    public static boolean  LoadStores()
    {

        List<DAL.DStore> Dstores = mapper.LoadStores();
        for (DAL.DStore dStore : Dstores)
        {
            Store store = new Store(dStore.getStoreNum());
            store.setHasLoaded(true);
            stores.add(store);
            store.Load(mapper);
        }
        if (stores.size() ==0)
            return false;
        return true;

    }

    public static void addDestination(String address, String contact, String phone, String area) {
        DestController.getInstance().addDestination(address,contact,phone,area);
        mapper.saveDestination(address,contact,phone,area);
    }

    public static String getSundayDate() {
        return "sunday is : " +mapper.getSundayDate();
    }


    //------------------------------------- ass 3
    public static void passDay() {
        //removes all the orders that were set for yesterday but there were no drivers and no drivers were added
        mapper.removeNoDeliveryOrders(current_date.toString());
        current_date=current_date.plusDays(1);
        Day_In_Week=((Day_In_Week)%7)+1;
        for (Store store :  stores)
        {
            store.passDay(mapper,current_date);
        }
        //Checks every order and for each order set for today sets a delivery date
       ;
    }
    public static void acceptDelivery(String orderId, String truckId, int storeId, int weight){
        Store store = getStore(storeId);
        if (!store.hasLoaded())
            store.Load(mapper);
        store.acceptDelivery(mapper, orderId, truckId, storeId, weight);
    }
    public static void validateSpecialRole(int employeeId, String specialRole) throws Exception {
        mapper.isSpecialRole(employeeId,specialRole);
    }
}

