package IL;

import BL.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;




public class ManagerController {

    final int storeNumber = 2;

    private static StoreController storeController;
    private Manager activeUserManager;

    private static ManagerController instance = null;




    public static ManagerController getInstance()
    {
        if (instance == null)
        {
            instance = new ManagerController();
        }
        return  instance;
    }

    private ManagerController()
    {
        storeController = new StoreController();
    }
    public void initializeStores(int month, int day)
    {
        storeController.initializeStores(month, day,storeNumber);
    }
    public boolean loadStores()
    {
       return storeController.LoadStores();
    }
    public void  setActiveUserManager(Manager manager)
    {
        this.activeUserManager = manager;
    }


    public String addEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date) {

        return storeController.addEmployee(jobs,name,id, bankAccount,store_num,salary,employee_conditions,start_date);

    }
    public String addDriverEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date,String license){
        return storeController.addDriverEmployee(jobs,name,id,bankAccount,store_num,salary,employee_conditions,start_date,license);
    }

    public String getEmployeeDetails(int store_num, int id) {

       return storeController.getEmployeeDetails(store_num,id);

    }
    public static Store connect(int employeeId, int storeId)
    {
        return  storeController.returnStoreIfEmployeeExists(storeId,employeeId);
    }
    public static void saveCapableShifts(Employee employee)
    {
        storeController.saveCapableShifts(employee);
    }

    public String updateEmployee(int store_num, String name, int id, String bankAccount, int salary, String employee_conditions) {

        return storeController.updateEmployee(store_num,name,id,bankAccount,salary,employee_conditions);
      /*  String result;
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            result = Validation.checkAddingNewEmployee(jobs);
            if (result == "success") {
                employee.updateEmployee(jobs, name, bankAccount, employee.getStore_num(), salary, employee_conditions, start_date);
                return "success";
            }
        }
        else {
            return "there is no employee with this id";
        }
        return  result; */

    }

    public String addToShift(int store_num, int employeeId, int dayNum, String dayPart, String role) {

        return storeController.addToShift(store_num,employeeId,dayNum,dayPart,role);

        /*
            String result;

            Employee employee = getEmployeeById(employeeId);
            if (employee != null) {
                result = Validation.checkCabaleShifts(employee.getCapable_shifts(), dayNum, dayPart, employee.getCapable_jobs(), role);
                if (result == "success") {
                    result = getStore(employee.getStore_num()).addEmployeeToShift(employeeId, dayNum, dayPart, role);
                }
            }
            else
            {
                result = "employee id doesn't exists";
            }
        return  result;

         */
    }

   /* public Store getStore(int store_num)
    {
        for (int i = 0; i < storeNumbers; i++) {

            if (stores.get(i).getStore_num() == store_num) {
                return stores.get(i);
            }
        }
        return null;
    } */
    public String addRoleToShift(int store_num, int dayNum, String dayPart, String role, int amount)
    {
      return storeController.addRoleToShift(store_num,dayNum,dayPart,role,amount);
    }

   /* public List<List<Employee>> getAllEmployees()
    {
        List<List<Employee>> employees = new LinkedList<>();
        for (Store s: stores) {
            employees.add(s.getEmployees());
        }
        return employees;
    } */

    public String getEmployeesDetails()
    {
       return storeController.getEmployeesDetails();
    }
    public String getShiftsDetails(int store_num)
    {
        return storeController.getShiftsDetails(store_num);

    }

    public String getCapableShiftsByEmployees(int store_num)
    {
        return storeController.getCapableShiftsByEmployees(store_num);
       /* String val = "";
        for (Employee employee : getStore(store_num).getEmployees())
        {
            String roles = "";
            for (int i = 0; i < employee.getCapable_jobs().length; i++)
                roles += employee.getCapable_jobs()[i] + ", ";
            val += "Employee: " + employee.getName() + ", ID: " + employee.getID() + ", Roles: " + roles + " Available for: \n";
            for (int i = 0; i < 7; i++)
            {
                if (employee.getCapable_shifts()[i].contains("morningevening"))
                {
                    val += "day " + (i+1) + " morning,evening\n";
                }
                else {
                    if (employee.getCapable_shifts()[i].contains("morning"))
                    {
                        val += "day " + (i+1) + " morning\n";
                    }
                    else {
                        if (employee.getCapable_shifts()[i].contains("evening"))
                            val += "day " + (i+1) + " evening\n";
                    }
                }
            }
        }
        return val; */
    }
    /*public Employee getEmployeeById(int id)
    {


        for (Store store : stores)
        {
            for (Employee e : store.getEmployees()) {
                if (e.getID() == id)
                    return e;
            }
        }
        return null;
    }*/

   /* public Boolean isEmployeeExists(int id)
    {
        for (List<Employee> employees : getAllEmployees())
        {
            if (!employees.isEmpty()) {
                for (Employee employee : employees)
                {
                    if (employee.getID() == id)
                        return true;
                }
            }
        }
        return  false;
    } */





    public String removeEmployee(int id)
    {
       return storeController.removeEmployee(id);

    }





    //---------------------------------------------------------------


//    public static void addDelivery(String date, String hour, String tid, String driverName, String source, int weightBeforeGo, List<String> numberedFiles, HashMap<String,String> adresses, HashMap<String,HashMap<String,Integer>> products ){
//        Callback c=DeliveryController.getInstance().addDelivery(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
//        c.run(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
//    }

    public String addDelivery(int store_num,String date, String hour, String tid, String driverName, String source, int weightBeforeGo,
                              List<String> numberedFiles, HashMap<String,String> adresses,
                              HashMap<String,HashMap<String,Integer>> products,String returnHour)
    {

        return storeController.addDelivery(store_num,date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products,returnHour);

     //   Callback c = stores.get(1).addDelivery(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
   //     c.run(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
    }

    public String viewDeliveries (int store_num){
        return storeController.viewDeliveries(store_num);
    }

    public String addTruck(int store_num,String id, int weight, int maxWeight, String model){
        return storeController.addTruck(store_num,id,weight,maxWeight,model);
    }
    public void addDestination(String address, String contact, String phone, String area){
        storeController.addDestination(address,contact,phone,area);

    }

    public String getSundayDate() {
        return  storeController.getSundayDate();
    }
}
