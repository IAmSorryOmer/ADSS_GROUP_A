package IL;

import BL.*;
import Entities.SingleProviderOrder;


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
        StoreController.initializeStores(month, day,storeNumber);
    }
    public boolean loadStores()
    {
       return StoreController.LoadStores();
    }
    public void  setActiveUserManager(Manager manager)
    {
        this.activeUserManager = manager;
    }


    public String addEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date) {

        return StoreController.addEmployee(jobs,name,id, bankAccount,store_num,salary,employee_conditions,start_date);

    }
    public String addDriverEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date,String license){
        return StoreController.addDriverEmployee(jobs,name,id,bankAccount,store_num,salary,employee_conditions,start_date,license);
    }

    public String getEmployeeDetails(int store_num, int id) {

       return StoreController.getEmployeeDetails(store_num,id);

    }
    public static Store connect(int employeeId, int storeId)
    {
        return  StoreController.returnStoreIfEmployeeExists(storeId,employeeId);
    }
    public static void saveCapableShifts(Employee employee)
    {
        StoreController.saveCapableShifts(employee);
    }

    public String updateEmployee(int store_num, String name, int id, String bankAccount, int salary, String employee_conditions) {

        return StoreController.updateEmployee(store_num,name,id,bankAccount,salary,employee_conditions);
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

        return StoreController.addToShift(store_num,employeeId,dayNum,dayPart,role);

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
      return StoreController.addRoleToShift(store_num,dayNum,dayPart,role,amount);
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
       return StoreController.getEmployeesDetails();
    }
    public String getShiftsDetails(int store_num)
    {
        return StoreController.getShiftsDetails(store_num);

    }
    public static void printAllTrucks(int id){
        StoreController.printAllTrucks(id);
    }

    public String getCapableShiftsByEmployees(int store_num)
    {
        return StoreController.getCapableShiftsByEmployees(store_num);
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
       return StoreController.removeEmployee(id);

    }





    //---------------------------------------------------------------

    public String addDelivery(String date,  int weightBeforeGo, SingleProviderOrder order,int store_num)
    {

        return StoreController.addDelivery(date,  weightBeforeGo,  order,store_num);

    }

    public String viewDeliveries (int store_num){
        return StoreController.viewDeliveries(store_num);
    }

    public String addTruck(int store_num,String id, int weight, int maxWeight, String model){
        return StoreController.addTruck(store_num,id,weight,maxWeight,model);
    }
    public void addDestination(String address, String contact, String phone, String area){
        StoreController.addDestination(address,contact,phone,area);

    }

    public String getSundayDate() {
        return  StoreController.getSundayDate();
    }




    //AGREEMENT INTERFACE
    public static void addItemToAgreement(String providerId, String catalogItemId, int minAmount, double discount){
        AgreementController.addItem(providerId, catalogItemId, minAmount, discount);
    }

    public static void editItemAgreement(String providerId, String catalogItemId, Integer minAmount, Double discount){
        AgreementController.editItem(providerId, catalogItemId, minAmount, discount);
    }

    public static void removeItemFromAgreement(String providerId, String catalogItemId){
        AgreementController.removeItemByIds(providerId, catalogItemId);
    }
    public static String stringifyAgreementItems(String providerId){
        return AgreementController.stringifyAgreementItems(providerId);
    }




    //CATALOG ITEM INTERFACE

    //CATEGORY INTERFACE

    //DISCOUNT INTERFACE


    //PRODUCT DETAILS INTERFACE


    //PROVIDER INTERFACE


    //REPORT INTERFACE



}
