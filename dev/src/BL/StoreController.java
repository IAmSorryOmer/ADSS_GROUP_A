package BL;

import DAL.Mapper;
import IL.Callback;
import IL.Validation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StoreController {
    List<Store> stores;
    private int storeNumbers = 9;
    private Mapper mapper = new Mapper();

    public StoreController()
    {
        stores = new LinkedList<>();
    }

    public  void  LoadStores()
    {
        List<DAL.DStore> Dstores = mapper.LoadStores();
        for (int i =0; i < Dstores.size(); i++)
        {
            Store store = new Store(Dstores.get(i).getStoreNum);
        }
    }

    public String addEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date) {

        String result;
        Store store = getStore(store_num);
        if (store != null) {
            result = Validation.checkAddingNewEmployee(jobs);
            if (result == "success") {
                store.getEmployees().add(new Employee(jobs, name, id, bankAccount, store_num, salary, employee_conditions, start_date));
               // DAL.AddEMployee();
            }
        }
        else {
            result = "store doesn't exists";
        }
        return  result;

    }

    public String getEmployeeDetails(int store_num, int id) {

        for (List<Employee> employees : getAllEmployees())
        {
            if (!employees.isEmpty()) {
                for (Employee employee : employees)
                {
                    if (employee.getID() == id)
                        return employee.toString();
                }

            }
        }
        return "there is no employee with this id";

    }

    public String updateEmployee(int store_num ,String[] jobs, String name, int id, String bankAccount, int salary, String employee_conditions, String start_date) {

        String result;
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
        return  result;

    }

    public String addToShift(int employeeId, int dayNum, String dayPart, String role) {
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
    }

    public Store getStore(int store_num)
    {
        for (int i = 0; i < storeNumbers; i++) {

            if (stores.get(i).getStore_num() == store_num) {
                return stores.get(i);
            }
        }
        return null;
    }
    public String addRoleToShift(int store_num, int dayNum, String dayPart, String role, int amount)
    {
        String result;
        Store store = getStore(store_num);
        if (store != null) {
            result = store.addRoleToShift(dayNum,dayPart,role,amount);
        }
        else {
            result="Store doesn't exist!";
        }
        return result;
    }

    public List<List<Employee>> getAllEmployees()
    {
        List<List<Employee>> employees = new LinkedList<>();
        for (Store s: stores) {
            employees.add(s.getEmployees());
        }
        return employees;
    }
    public String getEmployeesNameID()
    {
        String str = "";
        for (Store store : stores)
        {
            if (!store.getEmployees().isEmpty()) {
                str += "Store Number- " + store.getStore_num() + "\n";
                for (Employee employee : store.getEmployees())
                {
                    str += "Employee Name: " + employee.getName() + " Employee ID: " + employee.getID() + "\n";
                }

            }
        }

        return str;
    }
    public String getShiftsDetails(int store_num)
    {
        String str ="";
        Store store = getStore(store_num);
        if (store != null)
        {
            for (int j =0; j < 7; j++)
            {
                Day day = store.getSchedule().getDays()[j];
                str += "day "  + (j+1) + " - \n";
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
    public String getCapableShiftsByEmployees(int store_num)
    {
        String val = "";
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
        return val;
    }
    public Employee getEmployeeById(int id)
    {


        for (Store store : stores)
        {
            for (Employee e : store.getEmployees()) {
                if (e.getID() == id)
                    return e;
            }
        }
        return null;
    }

    public Boolean isEmployeeExists(int id)
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
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public void setStoreNumbers(int storeNumbers) {
        this.storeNumbers = storeNumbers;
    }

    public String removeEmployee(int id)
    {
        Employee employee = getEmployeeById(id);
        if (employee != null)
        {
            getStore(employee.getStore_num()).getEmployees().remove(employee);
            return "success";
        }
        return "employee doesn't exists";

    }


    public void addDelivery(String date, String hour, String tid, String driverName, String source, int weightBeforeGo, List<String> numberedFiles, HashMap<String,String> adresses, HashMap<String,HashMap<String,Integer>> products)
    {
        Callback c = stores.get(1).addDelivery(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
        c.run(date,hour,tid,driverName,source,weightBeforeGo,numberedFiles,adresses,products);
    }
}
